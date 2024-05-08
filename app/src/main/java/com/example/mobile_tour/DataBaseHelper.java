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
import android.widget.Toast;

import com.example.mobile_tour.ui.ClickedTravelData;
import com.example.mobile_tour.ui.home.Landmark;
import com.example.mobile_tour.ui.home.TravelCategory;
import com.example.mobile_tour.ui.home.TravelLocation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "landmarks3.db";
    private static final int DATABASE_VERSION = 10;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Очистка таблицы landmarks
        //db.delete("landmarks", null, null);
        db.delete("clickedLandmarks", null, null);

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
                "coordX REAL," +
                "coordY REAL," +
                "costable INTEGER," +
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




        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "name TEXT," +
                "surname TEXT," +
                "password TEXT," +
                "image TEXT," +
                "routes_count INTEGER," +
                "city TEXT," +
                "contact_info TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 9 && newVersion >= 9) {

        }

    }

    public boolean updateUserData(ContentValues values, String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.update("users", values, "email=?", new String[]{userEmail});
        db.close();

        return rowsAffected > 0;
    }


    public boolean updateSurnameByEmail(String email, String newSurname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("surname", newSurname);
        int rowsAffected = db.update("users", values, "email=?", new String[]{email});
        db.close();
        return rowsAffected > 0;
    }

    @SuppressLint("RestrictedApi")
    public void displayAllUserData() {

        SQLiteDatabase db = this.getReadableDatabase();
        onCreate(db);
        String tableName = "users";

        // Получаем метаданные таблицы
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0", null);
        String[] columnNames = cursor.getColumnNames(); // Получаем названия столбцов
        int columnCount = columnNames.length; // Получаем количество столбцов

        // Выводим названия столбцов
        for (String columnName : columnNames) {
            Log.d(TAG, "Column Name: " + columnName);
        }

        // Закрываем курсор, так как он больше не нужен
        cursor.close();

        // Получаем данные о пользователях
        cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Извлекаем данные о каждом пользователе на основе метаданных
                for (int i = 0; i < columnCount; i++) {
                    String columnName = columnNames[i];
                    @SuppressLint("Range") String columnValue = cursor.getString(cursor.getColumnIndex(columnName));
                    Log.d(TAG, columnName + ": " + columnValue);
                }
                Log.d(TAG, "-------------------------------");
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "No data found in the users table");
        }

        // Закрываем курсор и базу данных
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }


    public void removeDuplicateUsers() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Создаем временную таблицу для хранения наименьших id для каждого дубликата
        db.execSQL("CREATE TEMP TABLE TempUsers AS " +
                "SELECT MIN(id) AS min_id, email, name, password " +
                "FROM users " +
                "GROUP BY email, name, password");

        // Удаляем из основной таблицы users все записи, кроме тех, у которых id соответствуют минимальным id в TempUsers
        db.execSQL("DELETE FROM users WHERE id NOT IN (SELECT min_id FROM TempUsers)");

        // Удаляем временную таблицу
        db.execSQL("DROP TABLE IF EXISTS TempUsers");

        db.close();
    }


    public String[] getUserDataByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        String[] userData = new String[9]; // Инициализируем массив на 8 элементов

        if (cursor != null && cursor.moveToFirst()) {
            // Если есть данные в результате запроса, заполняем массив соответствующим образом
            for (int i = 0; i < 8; i++) {
                userData[i] = cursor.getString(i);
            }
            cursor.close();
        } else {
            // Если данных нет, заполняем массив пустыми значениями
            for (int i = 0; i < 8; i++) {
                userData[i] = "";
            }
        }

        db.close();
        return userData;
    }


    public void insertUser(String email, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put("email", email);
        values.put("name", name);
        values.put("surname","");
        values.put("password", password);
        values.putNull("image");
        values.put("routes_count", 0);
        values.putNull("city");
        values.putNull("contact_info");
        db.insert("users", null, values);
        db.close();
    }

    // Метод для получения данных о пользователе по электронной почте
    public boolean isUserExist(String email, String enteredPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean userExists = (cursor.getCount() > 0);

        if (userExists) {
            cursor.moveToFirst();
            @SuppressLint("Range") String passwordFromDB = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            db.close();
            return enteredPassword.equals(passwordFromDB);
        } else {
            cursor.close();
            db.close();
            return false;
        }
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
            values.put("coordX", location.coordX);
            values.put("coordY", location.coordY);
            values.put("costable", location.costable);
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
            @SuppressLint("Range") float coordX = cursor.getFloat(cursor.getColumnIndex("coordX"));
            @SuppressLint("Range") float coordY = cursor.getFloat(cursor.getColumnIndex("coordY"));
            @SuppressLint("Range") int costable = cursor.getInt(cursor.getColumnIndex("costable"));

            // Добавьте другие поля, если нужно

            // Создаем объект Landmark и добавляем его в список
            Landmark landmark = new Landmark(id, title, image, category , coordX , coordY, costable);
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

    @SuppressLint("RestrictedApi")
    public void displayUserCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT count(*) FROM users";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            Log.d(TAG, "Количество элементов в таблице users: " + count);
        }
        cursor.close();
    }





}
