package com.kwolarz.hw1;

import android.content.Context;
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


    public ContactsListAdapter (Context context, List<Contact> data) {
        this.mInflater = LayoutInflater.from(context);
        this.contactsList = data;
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
                contactsList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() { return contactsList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView contactIV;
        TextView firstNameTV;
        Button removeContactButton;

        ViewHolder (View itemView) {
            super(itemView);

            contactIV = itemView.findViewById(R.id.contactRowItemIV);
            firstNameTV = itemView.findViewById(R.id.contactRowItemTV);
            removeContactButton = itemView.findViewById(R.id.removeRowItemButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(onContactClickListener != null)
                onContactClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    String getItem(int id) { return contactsList.get(id).firstName; }

    void setOnContactClickListener(ItemClickInterface itemClickListener) {
        this.onContactClickListener = itemClickListener;
    }

    public interface ItemClickInterface {
        void onItemClick(View view, int position);
    }
}
