package com.example.granziaegypt.Adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.granziaegypt.Models.Category;
import com.example.granziaegypt.R;

import java.util.ArrayList;

public class Category_RecycleViewAdapter extends RecyclerView.Adapter<Category_RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Category> Categories =new ArrayList<Category>();

    public Category_RecycleViewAdapter(Context con , ArrayList<Category> categories){
        context = con;
        Categories = categories;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false)
                    , context, Categories);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.cardView.setTag(i);
        myViewHolder.Name.setText(Categories.get(i).getName());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);//-- to set the no of items pear line
        myViewHolder.recyclerView.setLayoutManager(layoutManager);
        Product_RecycleViewAdapter myAdapter_prod = new Product_RecycleViewAdapter(context,Categories.get(i).getProducts());
        myViewHolder.recyclerView.setAdapter(myAdapter_prod);
    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    //---------------------------------------
    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView Name;
        ArrayList<Category> categories ;
        RecyclerView recyclerView;
        Context con;


        public MyViewHolder(@NonNull View itemView, Context con, ArrayList<Category> arrayList) {
            super(itemView);

            this.con = con;
            this.categories = arrayList;
            cardView = itemView.findViewById(R.id.category_CardView);
            Name = (TextView) itemView.findViewById(R.id.categ_Name);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.prodRecyclerView);
        }
    }

}
