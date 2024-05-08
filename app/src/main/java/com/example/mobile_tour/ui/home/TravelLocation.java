package com.example.mobile_tour.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

public class TravelLocation implements Parcelable {
    public String title;
    public String year;
    public String category;
    public String description;
    public String location;
    public Integer imageUrl;
    public Float starRating;
    public Float coordX;
    public Float coordY;
    public int costable;

    public TravelLocation() {

    }

    protected TravelLocation(Parcel in) {
        title = in.readString();
        year = in.readString();
        category = in.readString();
        description = in.readString();
        location = in.readString();
        coordX = in.readFloat();
        coordY = in.readFloat();
        if (in.readByte() == 0) {
            imageUrl = null;
        } else {
            imageUrl = in.readInt();
        }
        if (in.readByte() == 0) {
            starRating = null;
        } else {
            starRating = in.readFloat();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(location);
        dest.writeFloat(coordX);
        dest.writeFloat(coordY);
        if (imageUrl == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageUrl);
        }
        if (starRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(starRating);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TravelLocation> CREATOR = new Creator<TravelLocation>() {
        @Override
        public TravelLocation createFromParcel(Parcel in) {
            return new TravelLocation(in);
        }

        @Override
        public TravelLocation[] newArray(int size) {
            return new TravelLocation[size];
        }
    };
}