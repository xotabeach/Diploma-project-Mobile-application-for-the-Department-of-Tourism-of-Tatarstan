package com.example.mobile_tour;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mobile_tour.ui.home.TravelLocation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "landmarks.db";
    private static final int DATABASE_VERSION = 3;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Очистка таблицы landmarks
        db.delete("landmarks", null, null);

        // Или, если вы предпочитаете использовать execSQL
        // db.execSQL("DELETE FROM landmarks");

        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table for landmarks
        db.execSQL("CREATE TABLE IF NOT EXISTS landmarks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "image INTEGER," +
                "title TEXT," +
                "category TEXT," +
                "foundation_date TEXT," +
                "description TEXT," +
                "location TEXT," +
                "starRating REAL);");


        // Table for clicked landmarks
        db.execSQL("CREATE TABLE IF NOT EXISTS clickedLandmarks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "landmark_id INTEGER," +
                "title TEXT," +
                "image INTEGER," +
                "category TEXT," +
                "FOREIGN KEY (landmark_id) REFERENCES landmarks(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            // Добавьте столбец starRating к таблице landmarks, если версия меньше 2

            db.execSQL("ALTER TABLE landmarks ADD COLUMN location TEXT;");
        }

        onCreate(db);
    }

    public void insertTravelLocations(List<TravelLocation> travelLocations) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (TravelLocation location : travelLocations) {
            ContentValues values = new ContentValues();
            values.put("image", location.imageUrl);
            values.put("title", location.title);
            values.put("category", location.category);
            values.put("foundation_date", location.year);
            values.put("description", location.description);
            values.put("location", location.location);
            values.put("starRating", location.starRating);
            db.insert("landmarks", null, values);
        }

        db.close();
    }


    public void displayRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM landmarks";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int rowCount = cursor.getInt(0);
            System.out.println("Количество элементов в базе данных: " + rowCount);
        }

        cursor.close();
        db.close();
    }
    public void displayAllData() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM landmarks";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
            // Дополнительные поля, которые вы хотите вывести...

            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("ImageURL: "+ image);
            // Выводите другие поля по аналогии...
        }

        cursor.close();
        db.close();
    }

    @SuppressLint("Range")
    public List<TravelLocation> getAllTravelLocations() {
        List<TravelLocation> travelLocations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM landmarks";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            TravelLocation location = new TravelLocation();
            location.imageUrl = cursor.getInt(cursor.getColumnIndex("image"));
            location.title = cursor.getString(cursor.getColumnIndex("title"));
            location.category = cursor.getString(cursor.getColumnIndex("category"));
            location.year = cursor.getString(cursor.getColumnIndex("foundation_date"));
            location.description = cursor.getString(cursor.getColumnIndex("description"));
            location.location = cursor.getString(cursor.getColumnIndex("location"));

            float starRating = cursor.getFloat(cursor.getColumnIndex("starRating"));
            location.starRating = starRating;

            travelLocations.add(location);
        }

        cursor.close();
        db.close();

        return travelLocations;
    }

}
