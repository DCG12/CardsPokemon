package com.example.user.magicgatherin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.app.ProgressDialog;
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

import com.example.user.magicgatherin.databinding.FragmentDetailBinding;
import com.example.user.magicgatherin.databinding.FragmentMainBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private CardsCursorAdapter adapter;
    private ProgressDialog dialog;

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

        adapter = new CardsCursorAdapter(getContext(), Card.class);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");

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

        getLoaderManager().initLoader(0, null, this);

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

            }


    private void refresh() {
        RefreshDataTask task = new RefreshDataTask();
        task.execute();

    }

    @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return DataManager.getCursorLoader(getContext());
            }

                @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                adapter.swapCursor(data);
            }

                @Override
        public void onLoaderReset(Loader<Cursor> loader) {
                adapter.swapCursor(null);
            }


    private class RefreshDataTask extends AsyncTask<Void, Object, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.show();
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

            dialog.dismiss();
        }
    }
}
