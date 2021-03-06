package com.kwolarz.hw1;

import android.os.Parcel;
import android.os.Parcelable;

class Contact implements Parcelable {

    public int imageID;
    public String firstName;
    public String lastName;
    public String birthDate;
    public int phoneNumber;

    Contact(int imageID, String firstName, String lastName, String birthDate, int phoneNumber) {
        this.imageID = imageID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    protected Contact(Parcel in) {
        imageID = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        birthDate = in.readString();
        phoneNumber = in.readInt();
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {

        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageID);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(birthDate);
        dest.writeInt(phoneNumber);
    }
}
