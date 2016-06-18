package com.example.mayank.dotusermodule;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ActivityCart extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Context mContext;
    ArrayList<String> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = this;

        mDataset = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_view2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new AdapterCart(mDataset);
        mRecyclerView.setAdapter(mAdapter);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://crackling-fire-4425.firebaseio.com");

        databaseReference.child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setDataset(dataSnapshot);
                RecyclerView.Adapter mAdapter = new AdapterCart(mDataset);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setDataset(DataSnapshot ds)
    {
        mDataset = new ArrayList<>();
        Iterator<DataSnapshot> dsi  = ds.getChildren().iterator();

        while(dsi.hasNext())
        {
            Log.e("DATA CHANGE","CHANGE");
            mDataset.add(dsi.next().getKey());
        }
    }
}
