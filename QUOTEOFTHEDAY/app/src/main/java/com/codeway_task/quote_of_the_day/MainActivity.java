package com.codeway_task.quote_of_the_day;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.codeway_task.quote_of_the_day.Apiclient.Quotes;
import com.codeway_task.quote_of_the_day.Apiclient.RetrofitClient;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView quoteTextView;
    private DatabaseHelper databaseHelper;
    private Button  favQe;
    ImageButton shareButton , favoriteButton;

    private Quote currentQuote;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteTextView = findViewById(R.id.quoteTextView);
        favoriteButton = findViewById(R.id.favoriteButton);
        favQe =findViewById(R.id.favQ);
        databaseHelper = new DatabaseHelper(this);

        // Display a random quote on app launch
        //displayRandomQuote();
        fetchQuote();

        // Share button click listener
        shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote();
            }
        });
        // Favorite button click listener
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleFavorite();
            }
        });
        favQe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,FavoriteQuotesActivity.class);
                startActivity(i);
            }
        });
    }


    private void fetchQuote() {
        Call<List<Quote>> call = RetrofitClient.getInstance().getApi().getQuotes();
        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Quote> quotes = response.body();
                    if (!quotes.isEmpty()) {
                        int randomIndex = new Random().nextInt(quotes.size());
                        Quote responseItem = quotes.get(randomIndex); // Assuming you're only interested in the first quote
                        String quoteText = responseItem.getText();
                        String quoteAuthor = responseItem.getAuthor();
                        String formattedQuote = "\"" + quoteText + "\"\n- " + quoteAuthor;
                        quoteTextView.setText(formattedQuote);
                        currentQuote = responseItem;
                    } else {
                        quoteTextView.setText("No quotes found.");
                    }
                } else {
                    quoteTextView.setText("Failed to fetch quotes.");
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                quoteTextView.setText("Network error: " + t.getMessage());
            }
        });

    }


//    private void displayRandomQuote() {
//        Quote quote = databaseHelper.getRandomQuote();
//        if (quote != null) {
//            currentQuote = quote;
//            String quoteText = "\"" + quote.getText() + "\"\n- " + quote.getAuthor();
//            quoteTextView.setText(quoteText);
//        } else {
//            quoteTextView.setText("Failed to load quote.");
//        }
//    }

    private void shareQuote() {
        String quote = quoteTextView.getText().toString();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, quote);
        startActivity(Intent.createChooser(shareIntent, "Share Quote"));
    }

    private void toggleFavorite() {
        if (currentQuote != null) {
            boolean isFavorite = databaseHelper.isQuoteFavorite(currentQuote);
            currentQuote.setFavorite(!isFavorite); // Toggle favorite status
            if (isFavorite) {
                databaseHelper.deleteFavorite(currentQuote);
            } else {
                databaseHelper.addFavorite(currentQuote);
            }
            updateFavoriteButtonState();
        }
    }

    private void updateFavoriteButtonState() {
        if (currentQuote != null) {
            boolean isFavorite = databaseHelper.isQuoteFavorite(currentQuote);
            if (isFavorite) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_shadow_24dp);
            } else {
                favoriteButton.setImageResource(R.drawable.ic_favorite_red_24dp);
            }
        }
    }
}