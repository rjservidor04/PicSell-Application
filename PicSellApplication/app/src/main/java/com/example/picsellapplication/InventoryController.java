package com.example.picsellapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InventoryController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryController extends Fragment {

    InventoryModel inventoryModel;
    Button btnNewItem;
    RecyclerView recyclerView;
    InventoryTableAdapter adapter;
    TextView tvTest;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InventoryController() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InventoryView.
     */
    // TODO: Rename and change types and number of parameters
    public static InventoryController newInstance(String param1, String param2) {
        InventoryController fragment = new InventoryController();
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
        // Inflate the layout for this fragment
        View layoutView = inflater.inflate(R.layout.inventory_view, container, false);

        btnNewItem = (Button) layoutView.findViewById(R.id.btnNewItem);
        recyclerView = (RecyclerView) layoutView.findViewById(R.id.inventory_recycler_view);
        tvTest = (TextView) layoutView.findViewById(R.id.tvTest_InventoryView);
        inventoryModel = new InventoryModel(getActivity());

        setRecyclerView();

        btnNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddInventoryItemController.class);
                Context context = getActivity();
                Bundle b = new Bundle();
                startActivity(intent);
            }
        });
        return layoutView;
    }
    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new InventoryTableAdapter(getActivity(), getInventoryList());

        recyclerView.setAdapter(adapter);
    }


    private List<InventoryModel> getInventoryList(){
        return inventoryModel.getInventoryItems();
    }
}