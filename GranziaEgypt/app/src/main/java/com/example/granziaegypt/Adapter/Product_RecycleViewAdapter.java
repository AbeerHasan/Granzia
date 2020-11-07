package com.example.granziaegypt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.granziaegypt.Controlers.ProductDetails;
import com.example.granziaegypt.R;
import com.example.granziaegypt.Models.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Product_RecycleViewAdapter extends  RecyclerView.Adapter<Product_RecycleViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Product> Products =new ArrayList<Product>();
    FragmentActionListener fragmentActionListener;

    public Product_RecycleViewAdapter(Context con , ArrayList<Product> products){
        context = con;
        Products = products;
        fragmentActionListener = (FragmentActionListener) con;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, viewGroup,false)
                , context, Products);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.Name.setText(Products.get(i).getName());
        Picasso.get().load(Products.get(i).getPicture()).into(myViewHolder.Pic);
    }

    @Override
    public int getItemCount() {
        return Products.size();
    }

    //---------------------------------------
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private CardView cardView = new CardView(context);
        private TextView Name;
        private ImageView Pic;
        ArrayList<Product> products = new ArrayList<Product>();
        Context con;


        public MyViewHolder(@NonNull View itemView , Context con , ArrayList<Product> arrayList) {
            super(itemView);

             itemView.setOnClickListener(this);
             this.con = con;
             this.products = arrayList;
             cardView = itemView.findViewById(R.id.product_CardView);
             Name = (TextView) itemView.findViewById(R.id.prod_Name);
             Pic = (ImageView) itemView.findViewById(R.id.prod_ImageView);

        }
        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            Product product = this.products.get(position);
            Toast.makeText(con, product.getName(), Toast.LENGTH_SHORT).show();
            Bundle bundle = new Bundle();
            bundle.putString("product id",product.getId());
            bundle.putString("product name",product.getName());
            bundle.putString("product price",product.getPrice());
            bundle.putString("product pic",product.getPicture());
            bundle.putStringArrayList("product description",product.getDescription());
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("product info",bundle);
            context.startActivity(intent);

            //fragmentActionListener.onSellectedItem(bundle);
        }
    }
    public interface FragmentActionListener {
        String KEY_SELECTED_PRODUCT = "KEY_SELECTED_PRODUCT";
        void onSellectedItem(Bundle bundle);

    }
}
