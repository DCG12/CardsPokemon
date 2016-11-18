package com.example.user.magicgatherin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import nl.littlerobots.cupboard.tools.provider.UriHelper;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

import com.example.user.magicgatherin.databinding.FragmentDetailBinding;
import com.example.user.magicgatherin.databinding.FragmentMainBinding;


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
        FragmentMainBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);
        View view = binding.getRoot();

        items = new ArrayList<>();
        adapter = new CardsAdapter(
                getContext(),
                R.layout.lv_cards_row,
                items
        );

        binding.lvCards.setAdapter(adapter);
        binding.lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Card card = (Card) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), DetailActivity.class);

                intent.putExtra("card", card);

                startActivity(intent);
            }
        });

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
    private class RefreshDataTask extends AsyncTask<Void, Object, Void> {
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

        UriHelper helper = UriHelper.with(CardContentProvider.AUTHORITY);
        Uri cardUri = helper.getUri(Card.class);
        cupboard().withContext(getContext()).put(cardUri, Card.class, result);

            return null;
        }
    }
}
