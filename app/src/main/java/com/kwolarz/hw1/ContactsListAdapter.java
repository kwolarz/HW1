package com.kwolarz.hw1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    private List<Contact> contactsList;
    private LayoutInflater mInflater;
    private ItemClickInterface onContactClickListener;
    private Context context;

    public ContactsListAdapter (Context context, List<Contact> data) {
        this.mInflater = LayoutInflater.from(context);
        this.contactsList = data;
        this.context = context;
    }

    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View rowItem = mInflater.inflate(R.layout.contact_row_item, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder (ContactsListAdapter.ViewHolder holder, final int position) {
        String contactFirstName = contactsList.get(position).firstName;
        holder.firstNameTV.setText(contactFirstName);

        holder.contactIV.setImageResource(contactsList.get(position).imageID);

        holder.removeContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Usuń kontakt")
                        .setMessage("Czy napewno chcesz usunąć kontakt?")

                        .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                contactsList.remove(position);
                                notifyDataSetChanged();
                            }
                        })

                        .setNegativeButton(R.string.nie, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() { return contactsList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView contactIV;
        TextView firstNameTV;
        Button removeContactButton;

        ViewHolder (View itemView) {
            super(itemView);

            contactIV = itemView.findViewById(R.id.contactRowItemIV);
            firstNameTV = itemView.findViewById(R.id.contactRowItemTV);
            removeContactButton = itemView.findViewById(R.id.removeRowItemButton);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(onContactClickListener != null)
                onContactClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if(onContactClickListener != null)
                onContactClickListener.onLongItemClick(view, getAdapterPosition());
            return true;
        }
    }

    Contact getItem(int id) { return contactsList.get(id); }

    void setOnContactClickListener(ItemClickInterface itemClickListener) {
        this.onContactClickListener = itemClickListener;
    }


    public interface ItemClickInterface {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }
}
