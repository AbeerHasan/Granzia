package com.example.granziaegypt.Controlers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.granziaegypt.Adapter.Category_RecycleViewAdapter;
import com.example.granziaegypt.Adapter.Product_RecycleViewAdapter;
import com.example.granziaegypt.Models.Category;
import com.example.granziaegypt.Models.Product;
import com.example.granziaegypt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends MainActivity implements Product_RecycleViewAdapter.FragmentActionListener {
    DrawerLayout drawer;
    ViewFlipper viewFlipper;

    private RecyclerView recyclerView_categ;
    private DatabaseReference categoriesRef;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<Product> productArrayList;
    Category_RecycleViewAdapter myAdapter_categ;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        UiInit();

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.blood_presure);
        images.add(R.drawable.woman_scale);
        images.add(R.drawable.blood_suger);
        images.add(R.drawable.breastfeeding_blog_iwp_in_blog);
        images.add(R.drawable.kitchen_scales);
        images.add(R.drawable.thermometer);
        images.add(R.drawable.massagers);
        for(int image:images) {
            startViewFlipper(image);
        }
        setData();
    }

    private void startViewFlipper(int image){
        viewFlipper = findViewById(R.id.categoriesFlipper);
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
    private void setData() {
        context = this;
        categoriesRef = FirebaseDatabase.getInstance().getReference().child("Database").child("Categories");
        recyclerView_categ = findViewById(R.id.categRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);//-- to set the no of items pear line
        recyclerView_categ.setLayoutManager(layoutManager);

        categoriesRef.addValueEventListener(new ValueEventListener() {
            Category c;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryArrayList = new ArrayList<Category>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    c = dataSnapshot1.getValue(Category.class);
                    c.setId(dataSnapshot1.getKey());
                    productArrayList =new ArrayList<Product>();
                    for (DataSnapshot dataSnapshot2 :dataSnapshot1.child("Products").getChildren()){
                        Product p = dataSnapshot2.getValue(Product.class);
                        p.setId(dataSnapshot2.getKey());
                        Log.v("product",p.getName());
                        productArrayList.add(p);
                    }
                    c.setProducts(productArrayList);
                    categoryArrayList.add(c);
                }
                myAdapter_categ = new Category_RecycleViewAdapter(context,categoryArrayList);// categoryArrayList);
                recyclerView_categ.setAdapter(myAdapter_categ);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "*** Error in database ***", Toast.LENGTH_SHORT).show();
                throw databaseError.toException();
            }
        });
    }
    @Override
    public void onSellectedItem(Bundle bundle) {
        Intent intent = new Intent(this,ProductDetails.class);
        intent.putExtra("product info",bundle);
        startActivity(intent);
    }
            /*images.add("https://firebasestorage.googleapis.com/v0/b/granziaegypt.appspot.com/o/%D9%85%D9%88%D8%A7%D8%B2%D9%8A%D9%86%20%D8%A7%D9%84%D9%85%D8%B7%D8%A8%D8%AE%2FTop-10-Best-Digital-Kitchen-Scales-in-2018-Reviews.jpg?alt=media&token=8c87f2fb-a6ad-4bad-ae57-6295d29e1b83");
        images.add("https://firebasestorage.googleapis.com/v0/b/granziaegypt.appspot.com/o/massagers%2Fmassagers.jpg?alt=media&token=40a540b1-cb4a-4a3d-8129-913598bb0a8f");
        images.add("https://firebasestorage.googleapis.com/v0/b/granziaegypt.appspot.com/o/%D8%A3%D8%AC%D9%87%D8%B2%D8%A9%20%D8%A7%D9%84%D8%B3%D9%83%D8%B1%2FTips-to-Monitor-Your-Blood-Sugar-Successfully.jpg?alt=media&token=7ecbe249-509a-467b-864c-db116be94301");
        images.add("https://firebasestorage.googleapis.com/v0/b/granziaegypt.appspot.com/o/%D8%A3%D8%AC%D9%87%D8%B2%D8%A9%20%D8%A7%D9%84%D8%B6%D8%BA%D8%B7%2F%D9%83%D9%8A%D9%81%D9%8A%D8%A9-%D9%82%D9%8A%D8%A7%D8%B3-%D8%B6%D8%BA%D8%B7-%D8%A7%D9%84%D8%AF%D9%85-%D8%A8%D8%A7%D9%84%D8%AC%D9%87%D8%A7%D8%B2-%D8%A7%D9%84%D8%B2%D8%A6%D8%A8%D9%82%D9%8A.jpg?alt=media&token=46af104a-fdf6-48ee-9e26-7870a7d37082");
        images.add("https://firebasestorage.googleapis.com/v0/b/granziaegypt.appspot.com/o/%D8%A3%D8%AC%D9%87%D8%B2%D8%A9%20%D9%82%D9%8A%D8%A7%D8%B3%20%D8%A7%D9%84%D8%AD%D8%B1%D8%A7%D8%B1%D8%A9%2F%D8%B9%D9%84%D8%A7%D8%AC-%D8%A7%D8%B1%D8%AA%D9%81%D8%A7%D8%B9-%D8%A7%D9%84%D8%AD%D8%B1%D8%A7%D8%B1%D8%A9-%D8%B9%D9%86%D8%AF-%D8%A7%D9%84%D8%A3%D8%B7%D9%81%D8%A7%D9%84-%D9%85%D8%B9-%D8%A8%D8%B1%D9%88%D8%AF-%D8%A7%D9%84%D8%A3%D8%B7%D8%B1%D8%A7%D9%81.jpg?alt=media&token=eb815326-addb-4efa-b822-fdc6b4ee3a4f");*/

}
