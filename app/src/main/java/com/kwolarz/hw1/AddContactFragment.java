package com.kwolarz.hw1;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

public class AddContactFragment extends Fragment {

    private String CONTACTKEY = "newContactKey";

    private EditText editText;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.firstName);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fN = editText.getText().toString();

                Contact contact = new Contact(fN, "", "", 1);
                contactsList.add(contact);

                Bundle newContactBundle = new Bundle();
                newContactBundle.putParcelableArrayList(CONTACTKEY, (ArrayList<? extends Parcelable>) contactsList);

                NavHostFragment.findNavController(AddContactFragment.this)
                        .navigate(R.id.action_AddContactFragment_to_ContactRVFragment, newContactBundle);
            }
        });
    }
}
