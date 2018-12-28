package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // adding a reference to origin text view
    private TextView originTV;
    private TextView descriptionTV;
    private TextView also_knownTV;
    private TextView ingredientsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // reference origin to text view
        originTV = (TextView) findViewById(R.id.origin_tv);

        descriptionTV = (TextView) findViewById(R.id.description_tv);

        also_knownTV = (TextView) findViewById(R.id.also_known_tv);

        ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // TODO (1): get the UI here
        if (!sandwich.getPlaceOfOrigin().equals(""))
            originTV.setText(sandwich.getPlaceOfOrigin());
        else
            originTV.setText("-");


        for (String s : sandwich.getAlsoKnownAs())
            also_knownTV.append(s);
        if (also_knownTV.getText().equals(""))
            also_knownTV.setText("-");

        if (!sandwich.getDescription().equals(""))
            descriptionTV.setText(sandwich.getDescription());
        else
            descriptionTV.setText("");


        for (String s : sandwich.getIngredients())
            ingredientsTV.append(s);
        if (ingredientsTV.getText().equals(""))
            ingredientsTV.setText("-");
    }
}
