package com.kwolarz.hw1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactRVFragment extends Fragment implements ContactsListAdapter.ItemClickInterface {

    private static final int REQUEST_CALL = 1;

    private String INSTANCEKEY = "savedInstanceKey";
    private String CONTACTKEY = "newContactKey";
    private String DETAILKEY = "detailContactKey";

    private int contactPositon;

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
            Contact sampleContact = new Contact(R.drawable.avatar_1,"", "", "", 0);
            contactsList.add(sampleContact);
            contactsList = savedInstanceState.getParcelableArrayList(INSTANCEKEY);
        }

        tryAddNewContact(contactsList);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));
        adapterRV = new ContactsListAdapter(getActivity(), contactsList);
        adapterRV.setOnContactClickListener(this);
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

    private void makePhoneCall(int position) {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            final String phone = Integer.toString(adapterRV.getItem(position).phoneNumber);
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall(contactPositon);
            }
        }

    }

    @Override
    public void onItemClick(View view, int position) {

        //Toast.makeText(getActivity(), "You clicked " + adapterRV.getItem(position).firstName + " on row " + position, Toast.LENGTH_SHORT).show();

        Bundle detailContactBundle = new Bundle();
        detailContactBundle.putParcelable(DETAILKEY, (Contact) adapterRV.getItem(position));

        NavHostFragment.findNavController(ContactRVFragment.this)
                .navigate(R.id.action_ContactRVFragment_to_DetailContactFragment, detailContactBundle);
    }

    @Override
    public void onLongItemClick(View view, final int position) {
        new AlertDialog.Builder(getActivity())
                .setMessage("Zadzwonic do " + adapterRV.getItem(position).firstName + "?")

                .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        contactPositon = position;
                        makePhoneCall(contactPositon);
                    }
                })

                .setNegativeButton(R.string.nie, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
