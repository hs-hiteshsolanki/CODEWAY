package com.codeway_task.quote_of_the_day;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoriteQuotesActivity extends AppCompatActivity {
    private ListView favoriteQuotesListView;
    private DatabaseHelper databaseHelper;
    private FavoriteQuotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);

        favoriteQuotesListView = findViewById(R.id.favoriteQuotesListView);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve favorite quotes from the database
        ArrayList<Quote> favoriteQuotesList = (ArrayList<Quote>) databaseHelper.getAllFavoriteQuotes();

        // Set up the adapter for the ListView
        adapter = new FavoriteQuotesAdapter(this, favoriteQuotesList, databaseHelper);
        favoriteQuotesListView.setAdapter(adapter);
    }
}