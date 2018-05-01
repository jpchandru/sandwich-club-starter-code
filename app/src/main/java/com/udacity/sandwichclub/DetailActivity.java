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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);


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
        TextView also_know_tv = findViewById(R.id.also_known_tv);
        TextView origin_tv = findViewById(R.id.origin_tv);
        TextView description_tv = findViewById(R.id.description_tv);
        TextView ingredients_tv = findViewById(R.id.ingredients_tv);
        StringBuffer buffer = null;

        if (null != sandwich.getPlaceOfOrigin() && !sandwich.getPlaceOfOrigin().isEmpty()) {
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        } else {
            origin_tv.setText(R.string.error_poc_orgin);
        }

        if (null != sandwich.getAlsoKnownAs() && sandwich.getAlsoKnownAs().size() > 0) {
            buffer = new StringBuffer();
            int c = 1;
            for(String alsoKnownAs: sandwich.getAlsoKnownAs()){
                buffer.append(alsoKnownAs);
                if(c != sandwich.getAlsoKnownAs().size())
                    buffer.append(", ");
                else
                    buffer.append(".");
                c++;
            }
            also_know_tv.setText(buffer.toString());
        } else {
            also_know_tv.setText(R.string.error_also_known_as);
        }

        if (null != sandwich.getDescription() && !sandwich.getDescription().isEmpty()) {
            description_tv.setText(sandwich.getDescription());
        } else {
            description_tv.setText(R.string.error_description);
        }


         if (null != sandwich.getIngredients() && sandwich.getIngredients().size() > 0) {
            buffer = new StringBuffer();
            int c = 1;
            for(String ingredients: sandwich.getIngredients()){
                buffer.append(ingredients);
                if(c != sandwich.getIngredients().size())
                    buffer.append(", ");
                else{
                    buffer.append(".");
                }
                c++;
            }
            ingredients_tv.setText(buffer.toString());
        } else {
            ingredients_tv.setText(R.string.error_ingredients);
        }

        if (null == sandwich.getImage() || sandwich.getImage().isEmpty()) {
            Toast.makeText(this, R.string.error_image, Toast.LENGTH_SHORT).show();
        }
    }
}
