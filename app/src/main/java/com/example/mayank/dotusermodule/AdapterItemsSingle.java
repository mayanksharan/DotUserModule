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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayank on 18/06/16.
 */
public class AdapterItemsSingle extends RecyclerView.Adapter<AdapterItemsSingle.ElementHolder>{

    android.content.Context mContext,context;
    int heightpixels;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;
    int open = 0;

    private ArrayList<Data> mDataset;

    final long ONE_MEGABYTE = 1024 * 1024;

    public AdapterItemsSingle(ArrayList<Data> myDataset, android.content.Context context) {
        this.mContext = context;
        mDataset = myDataset;
        storageReference = storage.getReferenceFromUrl("gs://dotz-2f65f.appspot.com/");
    }

    public static class ElementHolder extends RecyclerView.ViewHolder {

        LinearLayout rl;
        ImageView iv,cb,lb,ib;
        TextView tv;
        public ElementHolder(View v) {
            super(v);
            rl = (LinearLayout) v.findViewById(R.id.cis_rl);
            tv = (TextView) v.findViewById(R.id.cis_name);
            iv = (ImageView) v.findViewById(R.id.cis_img);
            cb = (ImageView) v.findViewById(R.id.cis_cb);
            lb = (ImageView) v.findViewById(R.id.cis_lb);
            ib = (ImageView) v.findViewById(R.id.cis_ib);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(open -1 == position) return 1;
        else return 0;
    }

    public AdapterItemsSingle.ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        if(viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_single, parent, false);

            ElementHolder eh = new ElementHolder(v);
            heightpixels = 6 * MainActivity.getHeight() / 5;
            eh.rl.getLayoutParams().height = heightpixels * 4 / 5;
            return eh;
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_single_detail, parent, false);
            ElementHolder eh = new ElementHolder(v);
            heightpixels = 6 * MainActivity.getHeight() / 5;
            //Done to match size of image do not mess
            eh.rl.getLayoutParams().height = (heightpixels * 4 / 3) - 170;
            return eh;
        }

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, final int position) {

        if(mDataset.size()>0) {
            h.tv.setText(mDataset.get(position).name);
            StorageReference imgRef = storage.getReferenceFromUrl(mDataset.get(position).image);

            final long ONE_MEGABYTE = 1024 * 1024;
            imgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Log.d("kjdh","dhsu");
                    h.iv.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0 , bytes.length));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception exception) {
                    // Handle any errors
                }
            });

        }
        else
        {
            h.tv.setText("TestData"+position);
        }


        h.lb.setOnClickListener(new View.OnClickListener() {
            boolean flag = false;
            @Override
            public void onClick(View v) {
                if(flag) {
                    flag = false;
                    h.lb.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                else {
                    flag = true;
                    h.lb.setImageResource(R.drawable.ic_favorite_red_500_18dp);
                }
            }
        });

        h.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://crackling-fire-4425.firebaseio.com");

                Map<String,Object> map = new HashMap<String, Object>();
                map.put(mDataset.get(position).name,"text");
                databaseReference.child("cart").updateChildren(map);


            }
        });

        h.ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(position);
            }
        });
    }

    public void open(int position)
    {
        int old = open;
        if(open==position+1) open = 0;
        else open=position+1;
        notifyItemChanged(position);
        if(old>0)
            notifyItemChanged(old-1);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset.size()>0)
            return mDataset.size();
        else
            return 5;
    }
}
