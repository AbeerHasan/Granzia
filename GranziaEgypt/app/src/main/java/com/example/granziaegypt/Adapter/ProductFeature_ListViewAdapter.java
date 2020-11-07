package com.example.granziaegypt.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.granziaegypt.R;

import java.util.ArrayList;

public class ProductFeature_ListViewAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> Feature;

    public ProductFeature_ListViewAdapter(Context C, ArrayList<String> feature){
        super(C, R.layout.item_product_features,R.id.featureText,feature);
        this.context = C;
        this.Feature = feature;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater =(LayoutInflater)getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = layoutInflater.inflate(R.layout.item_product_features,parent,false);
        TextView feature =item.findViewById(R.id.featureText);
        feature.setText(Feature.get(position));

        return item;
    }
}
