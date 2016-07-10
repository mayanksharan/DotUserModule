package com.example.mayank.dotusermodule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mayank on 10/07/16.
 */
public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.ElementHolder>{

    private ArrayList<NotiData> data;

    public AdapterNotifications(ArrayList<NotiData> data) {
        this.data = data;
    }

    public static class ElementHolder extends RecyclerView.ViewHolder {

        TextView[] tv = new TextView[4];
        public ElementHolder(View v) {
            super(v);
            tv[0] =(TextView) v.findViewById(R.id.notification_title);
            tv[1] =(TextView) v.findViewById(R.id.notification_body);
            tv[2] =(TextView) v.findViewById(R.id.noti_valid_date);
            tv[3] =(TextView) v.findViewById(R.id.noti_valid_place);
        }
    }

    public AdapterNotifications.ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notification, parent, false);
        ElementHolder eh = new ElementHolder(v);
        return eh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, final int position) {

        h.tv[0].setText(data.get(position).title);
        h.tv[1].setText(data.get(position).body);
        h.tv[2].setText("Valid Till : "+(data.get(position).date%100)+"/"+((data.get(position).date/100)%100)+"/"+(data.get(position).date/10000));
        h.tv[3].setText("Valid At : "+data.get(position).places);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
            return data.size();
    }
}

