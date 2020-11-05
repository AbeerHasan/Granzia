package com.example.granzia.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.granzia.Models.Contact;
import com.example.granzia.MyApplication;
import com.example.granzia.R;
import java.util.ArrayList;

public class ContactUs_RecyclerViewAdapter extends RecyclerView.Adapter<ContactUs_RecyclerViewAdapter.MyViewHolder>{


    Context context;
    ArrayList<Contact> contacts ;
    public ContactUs_RecyclerViewAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactUs_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, viewGroup,false),context,contacts);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactUs_RecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.cardView.setTag(i);
        myViewHolder.Name.setText(contacts.get(i).getName());
        myViewHolder.Number.setText(contacts.get(i).getNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    //---------------------- MyViewHolder Class -----------------
    //---------------------------------------
    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView Name;
        private TextView Number;
        private ArrayList<Contact> contacts;
        private Button bottun;
        private Context context;

        public MyViewHolder(@NonNull View itemView, final Context con, ArrayList<Contact> contacts) {
            super(itemView);

            this.context = con;
            this.contacts = contacts;
            cardView = itemView.findViewById(R.id.contact_CardView);
            Name = (TextView) itemView.findViewById(R.id.contactName);
            Number = (TextView) itemView.findViewById(R.id.contactPhone);
            bottun = (Button) itemView.findViewById(R.id.callButt);
            bottun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyApplication myApplication = (MyApplication) context.getApplicationContext();
                    if(myApplication.callPermission){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+Number.getText().toString()));
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context,"Permission Denied",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}