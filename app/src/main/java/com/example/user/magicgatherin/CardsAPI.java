package com.example.user.magicgatherin;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class CardsAPI {

    private static String BASE_URL = "http://api.magicthegathering.io/v1/cards";
    private static Integer LIMIT = 50;
    private static final int PAGES = 10;

    /*static ArrayList<Card> getAllCards(){
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .build();
        String url = builtUri.toString();

        return doCall(url);
    }*/

    static ArrayList<Card> getCardsRarity(String rareza) {
        String url = getUrlRareza("rarity", rareza);
        Log.d("URL", url);


        return doCall(url);
    }


    static ArrayList<Card> getCardsColor(String color) {

        String url = getUrlColor("colors", color);
        Log.d("URL", url);

        return doCall(url);
    }

    private static String getUrlColor(String colors, String color) {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("colors", color)
                .appendQueryParameter("limit", LIMIT.toString())
                .build();
        return builtUri.toString();
    }

    private static String getUrlRareza(String rarity, String rareza) {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("rarity", rareza)
                .appendQueryParameter("limit", LIMIT.toString())
                .build();
        return builtUri.toString();
    }
    @Nullable
        private static ArrayList doCall(String url) {
        try {
            String JsonResponse = HttpUtils.get(url);
            ArrayList<Card> carta = new ArrayList<>();

            JSONObject data = new JSONObject(JsonResponse);
            JSONArray jsonCartas = data.getJSONArray("cards");

            for (int i = 0; i <jsonCartas.length() ; i++) {
                Card card = new Card();
                JSONObject object = jsonCartas.getJSONObject(i);
                card.setName(object.getString("name"));
                card.setColors(object.getString("colors"));
                card.setType(object.getString("type"));
                card.setPosterUrl(object.getString("imageUrl"));
                card.setRarity(object.getString("rarity"));
                if(object.has("text")){
                    card.setText(object.getString("text"));}
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
}
