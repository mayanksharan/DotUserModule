package com.example.mayank.dotusermodule;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by mayank on 17/06/16.
 */
public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ElementHolder>{

private ArrayList<String> data;

public AdapterCart(ArrayList<String> data) {
        this.data = data;
        }

public static class ElementHolder extends RecyclerView.ViewHolder {

    TextView tv;
    ImageView iv;
    public ElementHolder(View v) {
        super(v);
        tv = (TextView) v.findViewById(R.id.cart_text);
        iv = (ImageView) v.findViewById(R.id.cart_img);
    }
}

    public AdapterCart.ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cart, parent, false);
        ElementHolder eh = new ElementHolder(v);
        return eh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, final int position) {

        h.tv.setText(data.get(position));
        Log.e("ds",data.get(position));

        h.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://crackling-fire-4425.firebaseio.com");
                databaseReference.child("cart").child(data.get(position)).removeValue();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }
}
