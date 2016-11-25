package com.example.user.magicgatherin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alexvasilkov.events.Events;


import com.bumptech.glide.Glide;

import com.example.user.magicgatherin.databinding.FragmentDetailBinding;

public class DetailActivityFragment extends Fragment {


    private FragmentDetailBinding binding;

    public DetailActivityFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        Events.register(this);
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_detail, container, false);
        View view = binding.getRoot();

                        Intent i = getActivity().getIntent();

                        if (i != null) {
                        Card card = (Card) i.getSerializableExtra("card");

                                if (card != null) {
                                updateUi(card);
                            }
                    }

                        return view;
            }

    @Events.Subscribe("card-selected")
        private void onMovieSelected(Card card) {
                updateUi(card);
            }

                private void updateUi(Card card) {
                Log.d("CARD", card.toString());

                    binding.lvCardName.setText(card.getName());
                    binding.lvCardType.setText(
                            Html.fromHtml("<b>tipo:</b> " + card.getType() + ""));
                    binding.lvCardRarity.setText(card.getRarity());
                    binding.lvCardText.setText(Html.fromHtml("<b>Text:</b> " + card.getText()));
                    Glide.with(getContext()).load(card.getPosterUrl()).into(binding.ivPosterImage);
    }
}


