package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        if(json != null){
            try{
                JSONObject jsonObject = new JSONObject(json);
                JSONObject name = new JSONObject(jsonObject.getString(NAME));
                String mainName = name.getString(MAIN_NAME);
                List<String> alsoKnownAsList= new ArrayList<String>();
                JSONArray alsoKnownAsArray = name.getJSONArray(ALSO_KNOWN_AS);
                for(int i = 0; i < alsoKnownAsArray.length(); i++ ){
                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
                }
                String placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
                String description = jsonObject.getString(DESCRIPTION);
                String image = jsonObject.getString(IMAGE);
                JSONArray ingredientsArray = jsonObject.getJSONArray(INGREDIENTS);
                List<String> ingredientsList = new ArrayList<String>();
                for(int i = 0; i<ingredientsArray.length(); i++){
                    ingredientsList.add(ingredientsArray.getString(i));
                }
                Sandwich sandwich = new Sandwich(mainName,alsoKnownAsList,placeOfOrigin,description,image,ingredientsList);
                return sandwich;
            }catch(JSONException jse){
                jse.printStackTrace();
            }
        }
        return null;
    }
}
