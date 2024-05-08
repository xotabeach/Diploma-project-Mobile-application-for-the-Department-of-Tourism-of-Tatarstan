package com.example.mobile_tour.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

public class Landmark implements Parcelable {
    private Float coordX;
    private Float coordY;
    private int id;
    private String title;
    private int image;
    private String category;

    private int costable;

    public Landmark(int id, String title, int image, String category , float coordX, float coordY , int costable) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.category = category;
        this.coordX = coordX;
        this.coordY = coordY;
        this.costable = costable;
    }

    protected Landmark(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readInt();
        category = in.readString();
        coordX = in.readFloat();
        coordY = in.readFloat();
        costable = in.readInt();
    }

    public static final Creator<Landmark> CREATOR = new Creator<Landmark>() {
        @Override
        public Landmark createFromParcel(Parcel in) {
            return new Landmark(in);
        }

        @Override
        public Landmark[] newArray(int size) {
            return new Landmark[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(image);
        dest.writeString(category);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public float getCoordX() { return  coordX;}

    public float getCoordY() { return  coordY;}

    public int getCostable() { return  costable;}
}
