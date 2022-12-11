package com.example.picsellapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutView extends Fragment {

    private SalesModel model;
    private EditText etItem, etQuantity;
    private Button btnSubmit, btnAdd;
    private View.OnClickListener clicked;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckoutView() {
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
    public static CheckoutView newInstance(String param1, String param2) {
        CheckoutView fragment = new CheckoutView();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_checkout_view, container, false);
        model = new SalesModel(getActivity());

        etItem = (EditText) layoutView.findViewById(R.id.etItem);
        etQuantity = (EditText) layoutView.findViewById(R.id.etQuantity);
        btnSubmit = (Button) layoutView.findViewById(R.id.btnSubmit);

        clicked = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = etItem.getText().toString();
                String qty = etQuantity.getText().toString();
                String msg = "";

                if(qty.equals("") || itemName.equals(""))
                    msg = "Please fill all fields";
                else{
                    if(view.getId() == R.id.btnSubmit){
                        try{
                            int quantity = Integer.parseInt(etQuantity.getText().toString());
                            msg = addItemToSales(itemName, quantity);
                        }
                        catch(Exception e){
                            msg = e.toString();
                        }
                    }
                }
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        };
        btnSubmit.setOnClickListener(clicked);
//        btnAdd.setOnClickListener(clicked);

        return layoutView;
    }

    public String addItemToSales(String itemName, int quantity){
        SalesController sc = new SalesController();
        Calendar today = Calendar.getInstance();

        // converting timeInMilliseconds to days
        int dateInDays = sc.getFormattedDate(today);

        model.setItemName(itemName);
        model.setQuantity(quantity);
        model.setDateSold(dateInDays);

        String result = model.addItemToSales(model);
        return result;
    }
}