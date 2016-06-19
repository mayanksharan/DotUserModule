package com.example.mayank.dotusermodule;

import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mayank on 12/06/16.
 */
public class AdapterInfo extends RecyclerView.Adapter<AdapterInfo.ElementHolder>{

    private Data data;
    boolean expanded = false;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public AdapterInfo(Data data) {
        this.data = data;
    }

    public static class ElementHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView cb,lb;
        RelativeLayout rl;
        CardView cv;
        public ElementHolder(View v,int type) {
            super(v);
            if(type == 0){
                rl = (RelativeLayout) v.findViewById(R.id.si_rl);
                cb = (ImageView) v.findViewById(R.id.si_img);
            }
            else if(type == 1) {
                tv = (TextView) v.findViewById(R.id.info_name);
                cb = (ImageView) v.findViewById(R.id.si_cb);
                lb = (ImageView) v.findViewById(R.id.si_lb);
            }
            else if(type == 2)
            {
                tv = (TextView) v.findViewById(R.id.si_desc);
                cv = (CardView) v.findViewById(R.id.desc_card);
            }
            else if(type == 3)
            {
                tv = (TextView) v.findViewById(R.id.si_eb);
            }
      }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public AdapterInfo.ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info0, parent, false);
            ElementHolder eh = new ElementHolder(v,0);
            eh.rl.getLayoutParams().height = MainActivity.getHeight() * 4 / 5;
            return eh;
        }
        else if(viewType == 1)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info1, parent, false);
            ElementHolder eh = new ElementHolder(v,1);
            return eh;
        }
        else if(viewType == 2)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info2, parent, false);
            ElementHolder eh = new ElementHolder(v,2);
            return eh;
        }
        else
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info3, parent, false);
            ElementHolder eh = new ElementHolder(v,3);
            return eh;
        }
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, int position) {

        switch (position)
        {
            case 0 : StorageReference imgRef = storage.getReferenceFromUrl(data.image);

                    final long ONE_MEGABYTE = 1024 * 1024;

                    imgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Log.d("kjdh", "dhsu");
                            h.cb.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception exception) {
                        }
                    });
                    break;

            case 1 : h.tv.setText(data.name);
                     h.cb.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                             final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://crackling-fire-4425.firebaseio.com");

                             Map<String, Object> map = new HashMap<String, Object>();
                             map.put(data.name, "text");
                             databaseReference.child("cart").updateChildren(map);
                         }
                     });
                    h.lb.setOnClickListener(new View.OnClickListener() {
                        boolean flag = false;

                        @Override
                        public void onClick(View v) {
                            if (flag) {
                                flag = false;
                                h.lb.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            } else {
                                flag = true;
                                h.lb.setImageResource(R.drawable.ic_favorite_red_500_18dp);
                            }
                        }
                    });
                    break;

            case 2 :if(!expanded)
                    {
                        h.tv.setText(R.string.exp_op1);
                        h.tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                expanded = true;
                                h.tv.setText(R.string.large_text);
                                notifyItemChanged(3);
                                notifyItemChanged(2);
                            }
                        });

                    }
                    else
                    {
                        h.tv.setText(R.string.large_text);
                        h.tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                expanded = false;
                                h.tv.setText(R.string.exp_op1);
                                notifyItemChanged(3);
                                notifyItemChanged(2);
                            }
                        });
                    }
                    break;
            case 3 :if(!expanded)
                    {
                        h.tv.setText("MORE TEXT");
                        h.tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                expanded = true;
                                h.tv.setText("LESS TEXT");
                                notifyItemChanged(2);
                                notifyItemChanged(3);
                            }
                        });
                    }
                    else
                    {
                        h.tv.setText("LESS TEXT");
                        h.tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                expanded = false;
                                h.tv.setText("MORE TEXT");
                                notifyItemChanged(2);
                                notifyItemChanged(3);
                            }
                        });
                    }
                    break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
            return 4;
    }
}
