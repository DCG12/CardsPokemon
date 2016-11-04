package com.example.user.magicgatherin;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CardsAPI {

    private final String BASE_URL = "http://api.magicthegathering.io/v1/cards";

    ArrayList<Card> getAllCards(){
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .build();
        String url = builtUri.toString();

        try {
            String JsonResponse = HttpUtils.get(url);
            ArrayList<Card> carta = new ArrayList<>();

            JSONObject data = new JSONObject(JsonResponse);
            JSONArray jsonCartas = data.getJSONArray("cards");

            for (int i = 0; i <jsonCartas.length() ; i++) {
                Card card = new Card();
                JSONObject object = jsonCartas.getJSONObject(i);
                card.setName(object.getString("name"));
                card.setType(object.getString("type"));
                carta.add(card);
            }

            return carta;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    String getCardsTypes(String pais) {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath("name")
                .appendPath("type")
                .build();
        String url = builtUri.toString();

        try {
            String JsonResponse = HttpUtils.get(url);
            return JsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
