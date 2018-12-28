package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // TODO (2) Parse the JSON --
        Sandwich sandwishSelected = new Sandwich();

        JSONObject sandwishJSON = null;

        try {
            // Crete the new JSON object from the JSON text received from resource!
            sandwishJSON = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // make sure we have an object pared
        if (sandwishJSON == null)
        {
            return null;
        }

        // get name and its inner mainName string and alsoKnownAs array of strings
        String mainName="";
        List<String> alsoKnownAs = new ArrayList<String>();
        try {
            // get the name json object
           JSONObject nameJSON = sandwishJSON.getJSONObject("name");
            mainName = nameJSON.getString("mainName");
            JSONArray alsoKnownAsJSON = nameJSON.getJSONArray("alsoKnownAs");

            // if there are other names known as =, loop them into a list
            if (alsoKnownAsJSON!=null &&  alsoKnownAsJSON.length()>0)
            {
                alsoKnownAs = new ArrayList<String>(alsoKnownAsJSON.length());
                for (int i = 0; i < alsoKnownAsJSON.length() - 1; i++) {
                    alsoKnownAs.add(alsoKnownAsJSON.getString(i) + ", ");
                }
                // no need to comma at the end
                alsoKnownAs.add(alsoKnownAsJSON.getString(alsoKnownAsJSON.length() - 1));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sandwishSelected.setMainName(mainName);
        sandwishSelected.setAlsoKnownAs(alsoKnownAs);
        //

        // try parsing the place of origin
        try {
            sandwishSelected.setPlaceOfOrigin(sandwishJSON.getString("placeOfOrigin"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // same paring description
        try {
            sandwishSelected.setDescription(sandwishJSON.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // same paring image
        try {
            sandwishSelected.setImage(sandwishJSON.getString("image"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // paring the array of ingredients
        List<String> ingredients = new ArrayList<String>();
        try {
            JSONArray ingredientsJSON = sandwishJSON.getJSONArray("ingredients");

            // if there are other names known as =, loop them into a list
            if (ingredientsJSON!=null &&  ingredientsJSON.length()>0)
            {
                ingredients = new ArrayList<String>(ingredientsJSON.length());
                for (int i = 0; i < ingredientsJSON.length() - 1; i++) {
                    ingredients.add(ingredientsJSON.getString(i) + ", ");
                }
                ingredients.add(ingredientsJSON.getString(ingredientsJSON.length() - 1));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sandwishSelected.setIngredients(ingredients);

        return sandwishSelected;
    }
}
