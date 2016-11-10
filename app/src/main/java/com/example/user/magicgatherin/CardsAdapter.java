package com.example.user.magicgatherin;

 import android.content.Context;
 import android.widget.ArrayAdapter;
 import java.util.List;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.TextView;


public class CardsAdapter extends ArrayAdapter<Card> {

    public CardsAdapter(Context context, int resource, List<Card> objects) {
                super(context, resource, objects);
            }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

                        // Obtenim l'objecte en la possició corresponent
                                Card card = getItem(position);
                Log.w("XXXX", card.toString());

                        // Mirem a veure si la View s'està reusant, si no es així "inflem" la View
                                // https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView#row-view-recycling
                                        if (convertView == null) {
                        LayoutInflater inflater = LayoutInflater.from(getContext());
                        convertView = inflater.inflate(R.layout.lv_cards_row, parent, false);
                    }

                        // Unim el codi en les Views del Layout
                                TextView lvCardName = (TextView) convertView.findViewById(R.id.lvCardName);
                TextView lvCardType = (TextView) convertView.findViewById(R.id.lvCardType);
                ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);

                        // Fiquem les dades dels objectes (provinents del JSON) en el layout
                                lvCardName.setText(card.getName());
                lvCardType.setText("Tipo: " + card.getType() + "%");

                        // Retornem la View replena per a mostrarla
                                return convertView;
}
