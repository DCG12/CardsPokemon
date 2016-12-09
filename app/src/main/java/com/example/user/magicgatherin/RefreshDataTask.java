package com.example.user.magicgatherin;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import com.alexvasilkov.events.Events;
import java.util.ArrayList;

class RefreshDataTask extends AsyncTask<Void, Void, Void> {

    private Context context;


    RefreshDataTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Events.post("start-downloading-data");
    }

    @Override
    protected Void doInBackground(Void... voids) {



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String rarity = preferences.getString("rarity", "Rare");
        String color = preferences.getString("colors", "White");
        String tipusConsulta = preferences.getString("tipus_consulta", "rareza");

        ArrayList<Card> result;
        if (tipusConsulta.equals("rareza")) {
            result = CardsAPI.getCardsRarity(rarity);
        } else {
            result = CardsAPI.getCardsColor(color);
        }

        Log.d("DEBUG", result != null ? result.toString() : null);

        DataManager.deleteCards(getContext());
        DataManager.saveCards(result, getContext());

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Dialog dialog = null;
        dialog.dismiss();
    }

    public Context getContext() {
        return context;
    }
}


