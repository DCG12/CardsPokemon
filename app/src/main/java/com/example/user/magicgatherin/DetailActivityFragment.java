package com.example.user.magicgatherin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivityFragment extends Fragment {

    private View view;
    private ImageView ivPosterImage;
    private TextView lvCardNAme;
    private TextView lvCardType;
    private TextView lvCardColor;
    private TextView lvCardRarity;
    private TextView lvCardText;

    public DetailActivityFragment() {
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);

                        Intent i = getActivity().getIntent();

                        if (i != null) {
                        Card card = (Card) i.getSerializableExtra("card");

                                if (card != null) {
                                updateUi(card);
                            }
                    }

                        return view;
            }

                private void updateUi(Card card) {
                Log.d("CARD", card.toString());

                    ivPosterImage = (ImageView) view.findViewById(R.id.ivPosterImage);
                    lvCardNAme = (TextView) view.findViewById(R.id.lvCardName);
                    lvCardType = (TextView) view.findViewById(R.id.lvCardType);
                    lvCardColor = (TextView) view.findViewById(R.id.lvCardColor);
                    lvCardRarity = (TextView) view.findViewById(R.id.lvCardRarity);
                    lvCardText = (TextView) view.findViewById(R.id.lvCardText);

                    lvCardNAme.setText(card.getName());
                    lvCardColor.setText(card.getColors());
                    lvCardType.setText(
                            Html.fromHtml("<b>tipo:</b> " + card.getType() + ""));
                    lvCardRarity.setText(card.getRarity());
                    lvCardText.setText(Html.fromHtml("<b>Texto:</b> " + card.getText()));
                    Glide.with(getContext()).load(card.getPosterUrl()).into(ivPosterImage);
    }
}


