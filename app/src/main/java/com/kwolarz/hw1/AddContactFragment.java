package com.kwolarz.hw1;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AddContactFragment extends Fragment {

    private String CONTACTKEY = "newContactKey";

    private EditText firstNameET;
    private EditText lastNameET;
    private EditText birthDateET;
    private EditText phoneNumberET;
    private Button button;
    private List contactsList = new ArrayList<Contact>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_contact_fragment, container, false);

        tryAddNewContact(contactsList);

        return view;
    }

    private void tryAddNewContact(List<Contact> contactsList) {
        if (getArguments() == null || !getArguments().containsKey(CONTACTKEY))
            return;

        this.contactsList = getArguments().getParcelableArrayList(CONTACTKEY);
    }

    private int setRandomAvatar() {
        int[] res = {
                R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3, R.drawable.avatar_4, R.drawable.avatar_5, R.drawable.avatar_6,
                R.drawable.avatar_7, R.drawable.avatar_8, R.drawable.avatar_9, R.drawable.avatar_10, R.drawable.avatar_11, R.drawable.avatar_12,
                R.drawable.avatar_13, R.drawable.avatar_14, R.drawable.avatar_15, R.drawable.avatar_16
        };
        Random rand = new Random();
        int random = rand.nextInt(res.length);

        return res[random];
    }

    private void setEditTexts(View view) {

        firstNameET = view.findViewById(R.id.firstName);
        lastNameET = view.findViewById(R.id.lastName);
        birthDateET = view.findViewById(R.id.birthDate);
        phoneNumberET = view.findViewById(R.id.phoneNumber);
    }

    private boolean checkNameInput(String name){
        boolean valid = name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");

        return valid;
    }

    private boolean checkBirthDateInput(String birth) {
        if (birth.trim().equals("")) {
            return true;
        } else {

            SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(birth);
            } catch (ParseException e) {
                return false;
            }

            return true;
        }
    }

    private boolean checkPhoneNumber(String phone) {
        boolean valid = phone.matches("\\d{9}");

        return valid;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setEditTexts(view);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fN = firstNameET.getText().toString();
                String lN = lastNameET.getText().toString();
                String bD = birthDateET.getText().toString();
                String pN = phoneNumberET.getText().toString();

                if (checkNameInput(fN) && checkNameInput(lN) && checkBirthDateInput(bD) && checkPhoneNumber(pN)) {
                    Contact contact = new Contact(setRandomAvatar(), fN, lN, bD, Integer.parseInt(pN));
                    contactsList.add(contact);

                    Bundle newContactBundle = new Bundle();
                    newContactBundle.putParcelableArrayList(CONTACTKEY, (ArrayList<? extends Parcelable>) contactsList);

                    NavHostFragment.findNavController(AddContactFragment.this)
                            .navigate(R.id.action_AddContactFragment_to_ContactRVFragment, newContactBundle);
                } else {
                    if(!checkNameInput(fN))
                        Toast.makeText(getActivity(), "Imię jest w złym formacie", Toast.LENGTH_SHORT).show();

                    if(!checkNameInput(lN))
                        Toast.makeText(getActivity(), "Nazwisko jest w złym formacie", Toast.LENGTH_SHORT).show();

                    if(!checkBirthDateInput(bD))
                        Toast.makeText(getActivity(), "Data jest w złym formacie", Toast.LENGTH_SHORT).show();

                    if(!checkPhoneNumber(pN))
                        Toast.makeText(getActivity(), "Numer jest w złym formacie", Toast.LENGTH_SHORT).show();

                }
                
            }
        });
    }
}
