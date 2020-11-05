package com.example.granzia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.example.granzia.Controlers.Categories;
import com.example.granzia.Fragments.*;
import com.example.granzia.Models.*;
import com.example.granzia.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Category_RecycleViewAdapter extends RecyclerView.Adapter<Category_RecycleViewAdapter.MyViewHolder> {

    boolean Flag;
    Context context;
    ArrayList<Category> Categories =new ArrayList<Category>();
    ArrayList<Product> Products =new ArrayList<Product>();
    FragmentActionListener fragmentActionListener;

    public <T> Category_RecycleViewAdapter(Context con , ArrayList<T> cat_prod , boolean flag){
        context = con;
        Flag = flag;
        if(flag) {
            Categories = (ArrayList<Category>) cat_prod;
            fragmentActionListener = (FragmentActionListener) context;
        }else {
            Products = (ArrayList<Product>) cat_prod;
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(Flag) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_product, viewGroup, false)
                    , context, Categories ,true);
        }else {
            return
    new MyViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_category_product, viewGroup,false)
                    , context, Products ,false);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.cardView.setTag(i);
        if(Flag) {
            myViewHolder.Name.setText(Categories.get(i).getName());
            Picasso.get().load(Categories.get(i).getPicture()).into(myViewHolder.Pic);
        }else {
            myViewHolder.Name.setText(Products.get(i).getName());
            Picasso.get().load(Products.get(i).getPicture()).into(myViewHolder.Pic);
        }

    }

    @Override
    public int getItemCount() {
        if(Flag) {
            return Categories.size();
        }else {
            return Products.size();
        }
    }

    //---------------------------------------
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView cardView;
        private TextView Name;
        private ImageView Pic;
        ArrayList<Category> categories = new ArrayList<Category>();
        ArrayList<Product> products = new ArrayList<Product>();
        Context con;


        public <T> MyViewHolder(@NonNull View itemView ,Context con ,ArrayList<T> arrayList , boolean f) {
            super(itemView);

            itemView.setOnClickListener(this);
            if(f) {
                this.categories = (ArrayList<Category>) arrayList;
            }else this.products = (ArrayList<Product>) arrayList;

            this.con = con;
            cardView = itemView.findViewById(R.id.category_product_CardView);
            Name = (TextView) itemView.findViewById(R.id.categ_prod_Name);
            Pic = (ImageView) itemView.findViewById(R.id.categ_prod_ImageView);

        }
        @Override
        public void onClick(View v)
        {
            if(Flag) {
                int position = getAdapterPosition();
                Category category = this.categories.get(position);
                Toast.makeText(con, category.getName(), Toast.LENGTH_SHORT).show();
                fragmentActionListener.onCategorySelected(this.categories.get(position).getId());
            }
        }
    }
    public interface FragmentActionListener {
        String KEY_SELECTED_CATEGORY = "KEY_SELECTED_CATEGORY";
        void onCategorySelected(String id);
    }
}
