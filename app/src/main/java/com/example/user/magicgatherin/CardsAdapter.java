package com.example.user.magicgatherin;

 import android.content.Context;
 import android.databinding.DataBindingUtil;
 import android.widget.ArrayAdapter;
 import java.util.List;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;


 import com.bumptech.glide.Glide;

 import com.example.user.magicgatherin.databinding.LvCardsRowBinding;


public class CardsAdapter extends ArrayAdapter<Card> {

    public CardsAdapter(Context context, int resource, List<Card> objects) {
                super(context, resource, objects);
            }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        // Obtenim l'objecte en la possició corresponent
        Card card = getItem(position);
        Log.w("XXXX", card.toString());

        LvCardsRowBinding binding = null;

        // Mirem a veure si la View s'està reusant, si no es així "inflem" la View
        // https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView#row-view-recycling
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            binding = DataBindingUtil.inflate(inflater, R.layout.lv_cards_row, parent, false);
                    } else {
                        binding = DataBindingUtil.getBinding(convertView);
        }

        // Unim el codi en les Views del Layout


        // Fiquem les dades dels objectes (provinents del JSON) en el layout


        binding.lvCardName.setText(card.getName());
        binding.lvCardType.setText("tipo: " + card.getType());
        Glide.with(getContext()).load(card.getPosterUrl()).into(binding.ivPosterImage);

        // Retornem la View replena per a mostrarla
        return binding.getRoot();
    }
}
