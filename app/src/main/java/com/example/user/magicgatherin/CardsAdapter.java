package com.example.user.magicgatherin;

 import android.content.Context;
 import android.widget.ArrayAdapter;
 import java.util.List;

public class CardsAdapter extends ArrayAdapter<Card> {

    public CardsAdapter(Context context, int resource, List<Card> objects) {
                super(context, resource, objects);
            }
}
