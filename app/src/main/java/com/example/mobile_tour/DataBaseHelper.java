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

import com.example.mobile_tour.ui.ClickedTravelData;
import com.example.mobile_tour.ui.home.Landmark;
import com.example.mobile_tour.ui.home.TravelCategory;
import com.example.mobile_tour.ui.home.TravelLocation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "landmarks.db";
    private static final int DATABASE_VERSION = 6;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Очистка таблицы landmarks
        db.delete("landmarks", null, null);
        db.delete("clickedLandmarks", null, null);
        db.delete("categories",null, null);
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

        db.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "image INTEGER," +
                "FOREIGN KEY (name) REFERENCES landmarks(category));");
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

    public void insertCategories(List<TravelCategory> travelCategories) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            // Очищаем существующие данные в таблице
            db.execSQL("DELETE FROM categories");

            // Вставляем новые данные
            for (TravelCategory category : travelCategories) {
                ContentValues values = new ContentValues();
                values.put("name", category.title);
                values.put("image", category.imageResId);

                db.insert("categories", null, values);
            }
        } finally {
            // Всегда закрываем базу данных после выполнения операций
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }


    public void insertClickedTravelData(ClickedTravelData clickedTravelData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", clickedTravelData.getTitle());
        values.put("image", clickedTravelData.getImageUrl());
        values.put("category", clickedTravelData.getCategory());

        db.insert("clickedLandmarks", null, values);
        db.close();
    }

    public void dropClickedLandmarksTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS clickedLandmarks");
        onCreate(db);
        db.close();
    }


    public void deleteClickedDataByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Поиск записи в базе данных по заголовку (title)
        String whereClause = "title = ?";
        String[] whereArgs = {title};

        // Удаление записи из таблицы clickedLandmarks, если она существует
        db.delete("clickedLandmarks", whereClause, whereArgs);

        db.close();
    }


    public boolean isLocationDataExists(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM clickedLandmarks WHERE title = ?", new String[]{title});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
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

    public void displayCategoryData() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM categories";
        Cursor cursor = db.rawQuery(query, null);

        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Image: " + image);
            }
        } finally {
            cursor.close();
            db.close();
        }
    }


    public List<ClickedTravelData> getAllClickedLandmarks() {
        List<ClickedTravelData> clickedLandscapes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM clickedLandmarks", null);

        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));

                // Дополнительные поля, если есть...

                ClickedTravelData clickedTravelData = new ClickedTravelData(title, image, category);
                clickedLandscapes.add(clickedTravelData);
            }
        } finally {
            cursor.close();
        }

        return clickedLandscapes;
    }

    public List<TravelCategory> getAllCategories() {
        List<TravelCategory> categoriesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM categories";
        Cursor cursor = db.rawQuery(query, null);

        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));

                // Создаем объект TravelCategory и добавляем его в список
                TravelCategory category = new TravelCategory();
                category.setTitle(name);
                category.setImageResId(image);
                categoriesList.add(category);
            }
        } finally {
            cursor.close();
            db.close();
        }

        return categoriesList;
    }


    public List<Landmark> getLandmarksByCategory(String category) {
        List<Landmark> landmarks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Выполняем запрос
        String query = "SELECT * FROM landmarks WHERE category = ?";
        Cursor cursor = db.rawQuery(query, new String[]{category});

        // Обрабатываем результат запроса
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
            // Добавьте другие поля, если нужно

            // Создаем объект Landmark и добавляем его в список
            Landmark landmark = new Landmark(id, title, image, category);
            landmarks.add(landmark);
        }

        cursor.close();
        db.close();

        return landmarks;
    }


    public void displayClickedData() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM clickedLandmarks";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") int image = cursor.getInt(cursor.getColumnIndex("image"));
            @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
            // Дополнительные поля, которые вы хотите вывести...

            System.out.println("ID: " + id);
            System.out.println("Title: " + title);
            System.out.println("ImageURL: " + image);
            System.out.println("Category: " + category);
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
