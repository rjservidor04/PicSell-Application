package com.example.picsellapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileController#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileController extends Fragment {

    UserModel userModel;
    SharedPreferences account;

    TextView storename, username, password;
    Button editProfile, changePassword;
    TextView logOut;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileController() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileView.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileController newInstance(String param1, String param2) {
        ProfileController fragment = new ProfileController();
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
        View layoutView = inflater.inflate(R.layout.fragment_profile_view, container, false);

        storename = (TextView) layoutView.findViewById(R.id.tvStoreName);
        username = (TextView) layoutView.findViewById(R.id.tvUsername);
        password = (TextView) layoutView.findViewById(R.id.tvPassword);
        editProfile = (Button) layoutView.findViewById(R.id.btnChangeProfileDetails);
        changePassword = (Button) layoutView.findViewById(R.id.btnChangePasswordDetails);
        logOut = (TextView) layoutView.findViewById(R.id.tvLogout);

        userModel = new UserModel(getActivity());

        account = getActivity().getSharedPreferences("MYPREFS", Activity.MODE_PRIVATE);
        String uName = account.getString("username", "N/A");

        userModel = userModel.getUser(uName);

        String storeName = userModel.getStoreName();
        String userName = userModel.getUsername();
        String passWord = userModel.getPassword();

        storename.setText(storeName);
        username.setText(userName);
        password.setText(passWord);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChangeProfileDetailsController.class);
                i.putExtra("storename", storeName);
                i.putExtra("username", userName);
                startActivity(i);

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChangePasswordController.class);
                startActivity(i);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dbHandler.closeDB();
                Intent i = new Intent(getActivity(), LoginController.class);
                startActivity(i);

            }
        });

        return layoutView;
    }
}