package com.codeway_task.quote_of_the_day;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteQuotesAdapter extends ArrayAdapter<Quote> {

    private ArrayList<Quote> favoriteQuotesList;
    private Context context;
    DatabaseHelper databaseHelper;

    public FavoriteQuotesAdapter(Context context, ArrayList<Quote> favoriteQuotesList, DatabaseHelper databaseHelper) {
        super(context, R.layout.favorite_quote_item, favoriteQuotesList);
        this.favoriteQuotesList = favoriteQuotesList;
        this.context = context;
        this.databaseHelper=databaseHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.favorite_quote_item, parent, false);
            holder = new ViewHolder();
            holder.quoteTextView = convertView.findViewById(R.id.favoriteQuoteTextView);
            holder.authorTextView = convertView.findViewById(R.id.favoriteAuthorTextView);
            holder.favoriteButton = convertView.findViewById(R.id.favoriteButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Quote quote = favoriteQuotesList.get(position);
        holder.quoteTextView.setText(quote.getText());
        holder.authorTextView.setText(quote.getAuthor());

        // Set favorite button icon based on whether the quote is already a favorite or not
        if (databaseHelper.isQuoteFavorite(quote)) {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_red_24dp);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_favorite_shadow_24dp);
        }

        // Set click listener for the favorite button
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite(quote);
                notifyDataSetChanged(); // Update the ListView
            }
        });

        return convertView;
    }
    // Method to toggle favorite status of a quote
    private void toggleFavorite(Quote quote) {
        if (databaseHelper.isQuoteFavorite(quote)) {
            databaseHelper.deleteFavorite(quote); // Remove from favorites
        } else {
            databaseHelper.addFavorite(quote); // Add to favorites
        }
    }

    static class ViewHolder {
        TextView quoteTextView;
        TextView authorTextView;
        ImageButton favoriteButton;
    }
}
