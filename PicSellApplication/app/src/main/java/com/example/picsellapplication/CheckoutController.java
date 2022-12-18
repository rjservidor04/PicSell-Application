package com.example.picsellapplication;

import static android.app.Activity.RESULT_OK;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.Calendar;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class CheckoutController extends Fragment {

    private SalesModel salesModel;
    private InventoryModel inventoryModel;
    private EditText etItem, etQuantity;
    private ImageView imageView;
    private Button btnCheckout, btnTakePicture;
    private Bitmap imageBitmap;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckoutController() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckoutView.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckoutController newInstance(String param1, String param2) {
        CheckoutController fragment = new CheckoutController();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test";
            String description = "testtest";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("test", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.checkout_view, container, false);
        inventoryModel = new InventoryModel(getActivity());
        salesModel = new SalesModel(getActivity());

        imageView = (ImageView) layoutView.findViewById(R.id.imgViewPicture);
        etItem = (EditText) layoutView.findViewById(R.id.etItem);
        etQuantity = (EditText) layoutView.findViewById(R.id.etQuantity);
        btnCheckout = (Button) layoutView.findViewById(R.id.btnCheckout);
        btnTakePicture = (Button) layoutView.findViewById(R.id.btnTakePicture);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = etItem.getText().toString();
                String qty = etQuantity.getText().toString();
                String msg = "";

                if(qty.isEmpty() || itemName.isEmpty())  Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();

                else {
                    try{
                        int quantity = Integer.parseInt(etQuantity.getText().toString());
                        if(quantity == 0)
                            msg = "Invalid value in quantity field";
                        else
                            msg = addItemToSales(itemName, quantity);

                        if(inventoryModel.getStockQuantityFromItemName(itemName) <= inventoryModel.getMinimumStockQuantityFromItemName(itemName)) {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "test");
                            builder.setContentTitle("Item Almost Out!");
                            builder.setContentText("The following item " + itemName + " has almost ran out! Try restocking in Inventory.");
                            builder.setSmallIcon(R.drawable.picsell_logo);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
                            managerCompat.notify(1, builder.build());
                        }
                    }
                    catch(Exception e){
                        msg = e.toString();
                    }

                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                }
            }
        });

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        return layoutView;
    }

    public String addItemToSales(String itemName, int quantity){
        SalesController sc = new SalesController();
        Calendar today = Calendar.getInstance();

        // converting timeInMilliseconds to days
        int dateInDays = sc.getFormattedDate(today);

        salesModel.setItemName(itemName);
        salesModel.setQuantity(quantity);
        salesModel.setDateSold(dateInDays);

        String result = salesModel.addItemToSales(salesModel);
        return result;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            detectTextFromImage();
        }
    }

    private void detectTextFromImage() {
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        InputImage image = InputImage.fromBitmap(imageBitmap, 0);

        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                displayTextFromImage(visionText);
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
    }

    private void displayTextFromImage(Text visionText) {
        if(visionText.getTextBlocks().isEmpty()){
            Toast.makeText(getActivity(), "The image has no text found.", Toast.LENGTH_SHORT).show();
        }

        else{
            String extractedText = visionText.getText();
            ArrayList<String> list = inventoryModel.getItemNames();
            if(list.isEmpty()){
                Toast.makeText(getActivity(), "There are no items in inventory. Please add an item.", Toast.LENGTH_SHORT).show();
            }
            else if(FuzzySearch.extractOne(extractedText, list).getScore() < 50)
                Toast.makeText(getActivity(), "The image has no text found.", Toast.LENGTH_SHORT).show();

            else {
                String closestString = FuzzySearch.extractOne(extractedText, list).getString();
                etItem.setText(closestString);
            }
        }
    }
}