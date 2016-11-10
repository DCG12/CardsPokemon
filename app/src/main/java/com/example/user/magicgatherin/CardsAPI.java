package com.example.user.magicgatherin;

import android.net.Uri;
import android.support.annotation.Nullable;

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
                                    card.setRarity(object.getString("rarity"));
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

                ArrayList<Card> getCardsRarity(String rareza) {
                    Uri builtUri = Uri.parse(BASE_URL)
                            .buildUpon()
                            .appendQueryParameter("rarity", rareza)
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
                            card.setRarity(object.getString("rarity"));
                            card.setType(object.getString("type"));
                            card.setPosterUrl(object.getString("imageUrl"));
                            card.setColors(object.getString("colors"));
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


    ArrayList<Card> getCardsColor(String color) {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("colors", color)
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
                card.setColors(object.getString("colors"));
                card.setType(object.getString("type"));
                card.setPosterUrl(object.getString("imageUrl"));
                card.setRarity(object.getString("rarity"));
                //card.setText(object.getString("text"));
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
