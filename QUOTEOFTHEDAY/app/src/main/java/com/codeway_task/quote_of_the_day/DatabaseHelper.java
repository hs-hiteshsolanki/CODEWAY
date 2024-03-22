package com.codeway_task.quote_of_the_day;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuotesDB";
    private static final String TABLE_QUOTES = "quotes";
    private static final String TABLE_FAVORITES = "favorites";
    private static final String KEY_ID = "id";
    private static final String KEY_TEXT = "text";
    private static final String KEY_AUTHOR = "author";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUOTES_TABLE = "CREATE TABLE " + TABLE_QUOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEXT + " TEXT,"
                + KEY_AUTHOR + " TEXT" + ")";
        db.execSQL(CREATE_QUOTES_TABLE);

        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TEXT + " TEXT,"
                + KEY_AUTHOR + " TEXT" + ")";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        onCreate(db);
    }

    public Quote getRandomQuote() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_QUOTES + " ORDER BY RANDOM() LIMIT 1", null);
        if (cursor != null) {
            cursor.moveToFirst();
            Quote quote = new Quote(cursor.getString(1), cursor.getString(2));
            cursor.close();
            return quote;
        }
        return null;
    }
    public boolean isQuoteFavorite(Quote quote) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_FAVORITES + " WHERE "
                + KEY_TEXT + " = ? AND " + KEY_AUTHOR + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{quote.getText(), quote.getAuthor()});
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        return isFavorite;
    }
    public void addFavorite(Quote quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, quote.getText());
        values.put(KEY_AUTHOR, quote.getAuthor());
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    public void deleteFavorite(Quote quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_TEXT + " = ? AND " + KEY_AUTHOR + " = ?",
                new String[]{quote.getText(), quote.getAuthor()});
        db.close();
    }
    // Method to get all favorite quotes from the database
    public List<Quote> getAllFavoriteQuotes() {
        List<Quote> favoriteQuotes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all records from the favorites table
        Cursor cursor = db.query(TABLE_FAVORITES, null, null, null, null, null, null);

        // Loop through the cursor to retrieve each favorite quote
        if (cursor.moveToFirst()) {
            do {
                // Retrieve the text and author of the favorite quote
                @SuppressLint("Range")
                String text = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
                @SuppressLint("Range")
                String author = cursor.getString(cursor.getColumnIndex(KEY_AUTHOR));

                // Create a new Quote object with the retrieved data
                Quote quote = new Quote(text, author);

                // Add the quote to the list of favorite quotes
                favoriteQuotes.add(quote);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        // Return the list of favorite quotes
        return favoriteQuotes;
    }
}