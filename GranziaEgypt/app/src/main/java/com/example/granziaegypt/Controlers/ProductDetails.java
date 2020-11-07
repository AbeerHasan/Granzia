package com.example.granziaegypt.Controlers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.granziaegypt.Adapter.ProductFeature_ListViewAdapter;
import com.example.granziaegypt.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetails extends MainActivity {

    TextView productNameText;
    ImageView productPicImage;
    TextView productPriceText;
    TextView productEnsurenceText;
    ListView keyFeaturesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        UiInit();

        productNameText = findViewById(R.id.productName);
        productPicImage = findViewById(R.id.productPic);
        productPriceText = findViewById(R.id.productPrice);
        productEnsurenceText = findViewById(R.id.productEnsurence);
        keyFeaturesList = findViewById(R.id.productFeatures);

        Bundle bundle = getIntent().getBundleExtra("product info");
        productNameText.setText(bundle.getString("product name"));
        Picasso.get().load(bundle.getString("product pic")).into(productPicImage);
        productPriceText.setText(bundle.getString("product price"));
        productEnsurenceText.setText("2 Years");
        ArrayList<String> Feeatures = bundle.getStringArrayList("product description");
        ProductFeature_ListViewAdapter myAdapter = new ProductFeature_ListViewAdapter(this,Feeatures);
        keyFeaturesList.setAdapter(myAdapter);

    }
}
