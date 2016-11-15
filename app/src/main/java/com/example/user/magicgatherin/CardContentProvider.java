package com.example.user.magicgatherin;

import nl.littlerobots.cupboard.tools.provider.CupboardContentProvider;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class CardContentProvider extends CupboardContentProvider {

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

                static {
                cupboard().register(Card.class);
            }

                public CardContentProvider() {
                super(AUTHORITY, 1);
            }
    }


