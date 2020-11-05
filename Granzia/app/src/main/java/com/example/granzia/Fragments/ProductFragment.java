package com.example.granzia.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.granzia.Adapter.Category_RecycleViewAdapter;
import com.example.granzia.Models.Product;
import com.example.granzia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private DatabaseReference productsRef;
    private ArrayList<Product> productArrayList;
    Category_RecycleViewAdapter myAdapter;
    private Context context;
    String categoryID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_product, container, false);
        initUI();
        return view;
    }
    //________________ Filling the RecyclerView with Data from Firebase _______________\\
    private void initUI() {
        context = getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.prodRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);//-- to set the no of items pear line
        recyclerView.setLayoutManager(layoutManager);
        productArrayList = new ArrayList<Product>();
        Bundle bundle = getArguments();
        categoryID = bundle.getString("KEY_SELECTED_CATEGORY");
        productsRef = FirebaseDatabase.getInstance().getReference()
                .child("Database").child("Categories").
                        child(categoryID).child("Products");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Product p = dataSnapshot1.getValue(Product.class);
                    productArrayList.add(p);
                }
                myAdapter = new Category_RecycleViewAdapter(context, productArrayList,false);

                recyclerView.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"*** Error in database ***", Toast.LENGTH_SHORT).show();
                throw databaseError.toException();
            }
        });
    }
}
