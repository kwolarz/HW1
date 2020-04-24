package com.kwolarz.hw1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailContactFragment extends Fragment {

    private String DETAILKEY = "detailContactKey";

    private TextView detailContactNameTV;
    private ImageView detailContactIV;
    private TextView detailContactBirthDateTV;
    private TextView detailContactPhoneNumberTV;

    Contact detailContact;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail_contact_fragment, container, false);

        if(savedInstanceState == null || !savedInstanceState.containsKey(DETAILKEY)) {

        } else {
            detailContact = getArguments().getParcelable(DETAILKEY);
        }

        detailContact = getArguments().getParcelable(DETAILKEY);

        setView(view);

        return view;
    }

    private void setView(View view) {

        detailContactNameTV = view.findViewById(R.id.nameTV);
        detailContactIV = view.findViewById(R.id.detailContactIV);
        detailContactBirthDateTV = view.findViewById(R.id.detailBirthDayTV);
        detailContactPhoneNumberTV = view.findViewById(R.id.detailPhoneNumberTV);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailContactNameTV.setText(detailContact.firstName + " " + detailContact.lastName);
        detailContactIV.setImageResource(detailContact.imageID);
        detailContactBirthDateTV.setText(detailContact.birthDate);
        detailContactPhoneNumberTV.setText(Integer.toString(detailContact.phoneNumber));
    }
}
