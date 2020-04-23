package com.kwolarz.hw1;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactRVFragment extends Fragment implements ContactsListAdapter.ItemClickInterface {

    private String INSTANCEKEY = "savedInstanceKey";
    private String CONTACTKEY = "newContactKey";

    FloatingActionButton fab;
    ContactsListAdapter adapterRV;
    List<Contact> contactsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contacts_rv_fragment, container, false);

        if(savedInstanceState == null || !savedInstanceState.containsKey(INSTANCEKEY)) {
            contactsList = new ArrayList<>();
        } else {
            contactsList = new ArrayList<Contact>();
            Contact sampleContact = new Contact("", "", "", 0);
            contactsList.add(sampleContact);
            contactsList = savedInstanceState.getParcelableArrayList(INSTANCEKEY);
        }

        tryAddNewContact(contactsList);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));
        adapterRV = new ContactsListAdapter(getActivity(), contactsList);
        recyclerView.setAdapter(adapterRV);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(INSTANCEKEY, (ArrayList<Contact>) contactsList);
    }

    private void tryAddNewContact(List<Contact> contactsList) {
        if (getArguments() == null || !getArguments().containsKey(CONTACTKEY))
            return;

        this.contactsList = getArguments().getParcelableArrayList(CONTACTKEY);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle newContactBundle = new Bundle();

                newContactBundle.putParcelableArrayList(CONTACTKEY, (ArrayList<? extends Parcelable>) contactsList);

                NavHostFragment.findNavController(ContactRVFragment.this)
                        .navigate(R.id.action_ContactRVFragment_to_AddContactFragment, newContactBundle);
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapterRV.getItem(position) + " on row " + position, Toast.LENGTH_SHORT).show();
    }
}
