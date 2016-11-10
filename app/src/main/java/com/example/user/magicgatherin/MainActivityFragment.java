package com.example.user.magicgatherin;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<Card> items;
    private CardsAdapter adapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView lvCards = (ListView) view.findViewById(R.id.lvCards);

        items = new ArrayList<>();
        adapter = new CardsAdapter(
                getContext(),
                R.layout.lv_cards_row,
                items
        );
        lvCards.setAdapter(adapter);

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
                super.onCreateOptionsMenu(menu, inflater);
                inflater.inflate(R.menu.menu_cartes_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
        public void onStart() {
                super.onStart();
                refresh();
            }


    private void refresh() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();

    }
    private class RefreshDataTask extends AsyncTask<Void, Object, ArrayList<Card>> {
        @Override
        protected ArrayList<Card> doInBackground(Void... voids) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String rarity = preferences.getString("rarity", "Rare");
            String color = preferences.getString("colors", "White");
            String tipusConsulta = preferences.getString("tipus_consulta", "rareza");
            CardsAPI api = new CardsAPI();
            ArrayList<Card> result;
            if (tipusConsulta.equals("rareza")) {
                                result = api.getCardsRarity(rarity);
                            } else {
                                result = api.getCardsColor(color);
                            }

            Log.d("DEBUG", result != null ? result.toString() : null);

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Card> carta) {

            super.onPostExecute(carta);

            adapter.clear();
            for (int i = 0; i < carta.size(); i++) {
                adapter.add(carta);
            }
        }
    }
}
