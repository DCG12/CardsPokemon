package com.example.user.magicgatherin;

import android.net.Uri;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class CardsAPI {

    private final String BASE_URL = "https://docs.magicthegathering.io/#get-all-cards";

        ArrayList<Card> getAllCards() {
            Uri builtUri = Uri.parse(BASE_URL)
                    .buildUpon()
                    .appendPath("name")
                    .build();
            String url = builtUri.toString();

            return doCall(url);
            }

    ArrayList<Card> getRarity(String pais) {
                Uri builtUri = Uri.parse(BASE_URL)
                                .buildUpon()
                                .appendPath("name")
                                .appendPath("rarity")
                                .build();
                String url = builtUri.toString();
        return doCall(url);
            }

    @Nullable
    private ArrayList<Card> doCall(String url) {
        try {
            String JsonResponse = HttpUtils.get(url);
            return processJson(JsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
            }

    private ArrayList<Card> processJson(String jsonResponse) {
                ArrayList<Card> cards = new ArrayList<>();
                try {
                        JSONObject data = new JSONObject(jsonResponse);
                        JSONArray jsonMovies = data.getJSONArray("movies");
                        for (int i = 0; i < jsonMovies.length(); i++) {
                                JSONObject jsonMovie = jsonMovies.getJSONObject(i);

                                        Card cartes = new Card();
                                cartes.setName(jsonMovie.getString("name"));
                                cartes.setType(jsonMovie.getString("type"));
                                cartes.setRarity(jsonMovie.getString("rarity"));


                                        cards.add(cartes);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                        return cards;
            }
}
