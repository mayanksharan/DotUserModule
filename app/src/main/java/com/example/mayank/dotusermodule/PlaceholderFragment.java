package com.example.mayank.dotusermodule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by mayank on 18/06/16.
 */

public class PlaceholderFragment extends Fragment {

    private static Context mContext;
    private ArrayList<Data> mDataset;
    static int secnum;
    private static int lengthf = 0, qualityf = 0,fcf = 0;
    boolean grid = true;
    ProgressDialog dialog;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
        mContext = getContext();
        mDataset = new ArrayList<>();
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        secnum = sectionNumber;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.item_recycler_view);
        final RecyclerView.Adapter[] mAdapter = new RecyclerView.Adapter[1];
        if(!grid) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(mLayoutManager);
            if(mDataset.size()>0) {
                mAdapter[0] = new AdapterItemsSingle(mDataset, mContext);
                Log.d("Adapter","Linear Data"+mDataset.size());
            }
            else{
                Log.d("Adapter","Linear Loading"+mDataset.size());
                mAdapter[0] = new AdapterLoading();
            }
            mRecyclerView.setAdapter(mAdapter[0]);
        }
        else
        {
            if(mDataset.size()>0) {
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter[0] = new AdapterItemsSingle(mDataset, mContext);
                Log.d("Adapter","Grid Data"+mDataset.size());
            }
            else{
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter[0] = new AdapterLoading();
                Log.d("Adapter","Grid Loading"+mDataset.size());
            }
            mRecyclerView.setAdapter(mAdapter[0]);
        }



        final ImageView iv2 = (ImageView) rootView.findViewById(R.id.switch_config);
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grid)
                {
                    iv2.setImageResource(R.drawable.application_black);
                    grid = false;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    if(mDataset.size()>0) {
                        mAdapter[0] = new AdapterItemsSingle(mDataset, mContext);
                        Log.d("Adapter","Linear Data Click"+mDataset.size());
                    }
                    else
                    {
                        Log.d("Adapter","Linear Loading Click"+mDataset.size());
                        mAdapter[0] = new AdapterLoading();
                    }
                    mRecyclerView.setAdapter(mAdapter[0]);
                }
                else
                {
                    iv2.setImageResource(R.drawable.border_all_black);
                    grid = true;
                    if(mDataset.size()>0) {
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter[0] = new AdapterItems(mDataset, mContext);
                        Log.d("Adapter","Grid Data Click"+mDataset.size());
                    }
                    else {
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                        mRecyclerView.setLayoutManager(mLayoutManager);

                        mAdapter[0] = new AdapterLoading();
                        Log.d("Adapter","Grid Loading Click"+mDataset.size());
                    }
                    mRecyclerView.setAdapter(mAdapter[0]);
                }
            }
        });

        ImageView iv = (ImageView) rootView.findViewById(R.id.filter_button);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(),ActivityFilter.class);
                startActivity(intent);
            }
        });


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://crackling-fire-4425.firebaseio.com");

        databaseReference.child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int temp = MainActivity.fil;
                lengthf = temp % 10;
                temp /= 10;
                qualityf = temp % 10;
                temp /= 10;
                fcf = temp;
                setDataset(dataSnapshot);
                Log.d("LLL", mDataset.size() + "");
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
                mRecyclerView.setLayoutManager(mLayoutManager);
                RecyclerView.Adapter mAdapter = new AdapterItems(mDataset,mContext);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("KHJKH","kjdhkhfdjk");

            }

        });


        return rootView;
    }


    private void setDataset (DataSnapshot ds)
    {
        mDataset = new ArrayList<>();

        for (int i = 0; i < ds.getChildrenCount(); i++) {
            String a = ds.child("hair" + i).child("name").getValue(String.class);
            String b = ds.child("hair" + i).child("gender").getValue(String.class);
            String c = ds.child("hair" + i).child("hairLength").getValue(String.class);
            String d = ds.child("hair" + i).child("hairQuality").getValue(String.class);
            String e = ds.child("hair" + i).child("faceCut").getValue(String.class);
            String f = ds.child("hair" + i).child("image").getValue(String.class);

            Data temp = new Data(a, b, c, d, e, f);
            if(filter(temp)) {
                mDataset.add(temp);
            }
        }

    }

    private boolean filter(Data t)
    {
        Log.d("FIL Vals",lengthf+" "+qualityf+" "+fcf);
        if(lengthf == 1 && !t.length.equals("S")) return false;
        if(lengthf == 2 && !t.length.equals("M")) return false;
        if(lengthf == 3 && !t.length.equals("L")) return false;
        if(qualityf == 1 && !t.quality.equals("Soft")) return false;
        if(qualityf == 2 && !t.quality.equals("Hard")) return false;
        if(fcf == 1 && !t.faceCut.equals("Oblong")) return false;
        if(fcf == 2 && !t.faceCut.equals("Diamond")) return false;
        if(fcf == 3 && !t.faceCut.equals("Heart")) return false;
        return true;
    }

}