package com.example.granzia.Fragments;

import android.content.Context;
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
import com.example.granzia.Models.Category;
import com.example.granzia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    /*/ TODO: Rename parameter arguments, choose names that match
    public CategoryFragment() {
        // Required empty public constructor
    }
*/ View view;
    private RecyclerView recyclerView;
    private DatabaseReference categoriesRef;
    private ArrayList<Category> categoryArrayList;
    Category_RecycleViewAdapter myAdapter;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initUI();
    }

    //________________ Filling the RecyclerView with Data from Firebase _______________\\
    private void initUI() {
        context = getContext();
        //myAdapter = new Category_RecycleViewAdapter();
        recyclerView = (RecyclerView) view.findViewById(R.id.categRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);//-- to set the no of items pear line
        recyclerView.setLayoutManager(layoutManager);
        categoriesRef = FirebaseDatabase.getInstance().getReference().child("Database").child("Categories");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryArrayList = new ArrayList<Category>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Category c = dataSnapshot1.getValue(Category.class);
                    c.setId(dataSnapshot1.getKey());
                    categoryArrayList.add(c);
                }
                myAdapter = new Category_RecycleViewAdapter(context, categoryArrayList, true);
                recyclerView.setAdapter(myAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "*** Error in database ***", Toast.LENGTH_SHORT).show();
                throw databaseError.toException();
            }
        });
    }
}

