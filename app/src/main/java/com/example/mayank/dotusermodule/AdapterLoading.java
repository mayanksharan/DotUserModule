package com.example.mayank.dotusermodule;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mayank on 19/06/16.
 */
public class AdapterLoading extends RecyclerView.Adapter<AdapterLoading.ElementHolder>{

    private ArrayList<String> data;

    public AdapterLoading() {

    }

    public static class ElementHolder extends RecyclerView.ViewHolder {

        public ElementHolder(View v) {
            super(v);
        }
    }

    public AdapterLoading.ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_page, parent, false);
        ElementHolder eh = new ElementHolder(v);
        return eh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, final int position) {
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }
}

