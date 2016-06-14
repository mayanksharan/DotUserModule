package com.example.mayank.dotusermodule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mayank on 12/06/16.
 */
public class AdapterInfo extends RecyclerView.Adapter<AdapterInfo.ElementHolder>{

    private Data data;

    public AdapterInfo(Data data) {
        this.data = data;
    }

    public static class ElementHolder extends RecyclerView.ViewHolder {

        TextView tv;
        public ElementHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.info_text);
      }
    }

    public AdapterInfo.ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info, parent, false);
        ElementHolder eh = new ElementHolder(v);
        return eh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, int position) {

        switch (position)
        {
            case 0 :    h.tv.setText("NAME: " + data.name);
                    break;
            case 1 :    h.tv.setText("LENGTH: " + data.length);
                    break;
            case 2 :    h.tv.setText("QUALITY: " + data.quality);
                    break;
            case 3 :    h.tv.setText("FACECUT: " + data.faceCut);
                    break;
            case 4 :    h.tv.setText(R.string.exp_op1);
                    break;
            case 5 :    h.tv.setText(R.string.exp_op2);
                    break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
            return 6;
    }
}
