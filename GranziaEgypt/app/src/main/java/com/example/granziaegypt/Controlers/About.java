package com.example.granziaegypt.Controlers;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.granziaegypt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class About extends MainActivity {
    DrawerLayout drawer;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        UiInit();
        //setphotos();
      //  startSlideshow();
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.fb_img_1);
        images.add(R.drawable.fb_img_2);
        images.add(R.drawable.fb_img_3);
        images.add(R.drawable.fb_img_4);
        for(int image:images) {
            startViewFlipper(image);
        }
    }

    private void startViewFlipper(int image){
        viewFlipper = findViewById(R.id.eventsPicsFlipper);
        ImageView imageView=new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }
    /*/------------------------- Image presenter Button -----------------------------
    private DatabaseReference photoRef;
    Map<String,String> photos =  new HashMap<String,String>();

    ImageView imageView ;
    TextView aboutText;

    private void setphotos(){
        //photo = (ImageView)findViewById(R.id.imageView);
        imageView = (ImageView)findViewById(R.id.about_ImageView);
        photoRef = FirebaseDatabase.getInstance().getReference().child("Database").child("Photos");
        photoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    photos.put(dataSnapshot1.getKey(),dataSnapshot1.child("url").getValue().toString());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(,"*** Error in database ***", Toast.LENGTH_SHORT).show();
                throw databaseError.toException();
            }
        });
    }
    Timer timer;
    final Handler handler =new Handler();
    int index =1 ;

    private void startSlideshow(){
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(photos.get(index+"")).into(imageView);
                if(index==photos.size()) {
                    index = 1;

                }else index++;
            }
        };
        timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },250,2500);

    }*/
}
