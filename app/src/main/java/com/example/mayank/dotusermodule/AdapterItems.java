package com.example.mayank.dotusermodule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mayank on 04/06/16.
 */
public class AdapterItems extends RecyclerView.Adapter<AdapterItems.ElementHolder>{

    android.content.Context mContext,context;
    int heightpixels;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference;

    private ArrayList<Data> mDataset;

    final long ONE_MEGABYTE = 1024 * 1024;

    public AdapterItems(ArrayList<Data> myDataset, android.content.Context context) {
        this.mContext = context;
        mDataset = myDataset;
        storageReference = storage.getReferenceFromUrl("gs://project-1465488381886623726.appspot.com");
    }

    public static class ElementHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl;
        ImageView iv,sb,lb,cb;
        TextView tv;
        public ElementHolder(View v) {
            super(v);
            rl = (RelativeLayout) v.findViewById(R.id.ci_rl);
            tv = (TextView) v.findViewById(R.id.ci_title);
            iv = (ImageView) v.findViewById(R.id.ci_img);
            sb = (ImageView) v.findViewById(R.id.share_button);
            lb = (ImageView) v.findViewById(R.id.like_button);
            cb = (ImageView) v.findViewById(R.id.addToCart_button);
        }
    }

    public AdapterItems.ElementHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        ElementHolder eh = new ElementHolder(v);
        heightpixels = MainActivity.geth();
        eh.rl.getLayoutParams().height = (heightpixels) / 2;

        return eh;
    }

    public static Bitmap decodeBase64(String input) throws IOException
    {
        Log.d("img",input);
        byte[] decodedBytes = Base64.decode(input,0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, final int position) {

//        final boolean[] flag = {true};
//        final Bitmap[] bitmap = new Bitmap[1];
//        StorageReference imgRef = storageReference.child("img"+position+".jpg");
//
//        imgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                // Data returns, use this as needed
//                Log.d("df","fd");
//                bitmap[0] = BitmapFactory.decodeByteArray(bytes , 0, bytes.length);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(Exception e) {
//                // Handle any errors
//                flag[0] = false;
//            }
//        });
//
//        if(flag[0]){
//            h.iv.setImageBitmap(bitmap[0]);
//        }
//        else
//        {
//            h.iv.setImageResource(R.drawable.img0);
//        }

        boolean flag = false;

        if(mDataset.size()>0) {


            h.tv.setText(mDataset.get(position).name);
            try {
                Bitmap myBitmapAgain = decodeBase64(mDataset.get(position).image);
                h.iv.setImageBitmap(myBitmapAgain);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            h.tv.setText("TestData"+position);
        }

        h.sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });

        h.lb.setOnClickListener(new View.OnClickListener() {
            boolean flag = false;
            @Override
            public void onClick(View v) {
                if(flag) {
                    flag = false;
                    h.lb.setImageResource(R.drawable.ic_favorite_border_white_18dp);
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
            }
        });

        if(position < mDataset.size()) {
            h.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityServiceInfo.class);
                    intent.putExtra("name", mDataset.get(position).name);
                    intent.putExtra("gender", mDataset.get(position).gender);
                    intent.putExtra("quality", mDataset.get(position).quality);
                    intent.putExtra("length", mDataset.get(position).length);
                    intent.putExtra("faceCut", mDataset.get(position).faceCut);
                    intent.putExtra("image", mDataset.get(position).image);
                    context.startActivity(intent);
                }
            });
        }
        else
        {
            h.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityServiceInfo.class);
                    intent.putExtra("name", "TESTNAME");
                    intent.putExtra("gender", "TESTGENDER");
                    intent.putExtra("quality", "TESTQUALITY");
                    intent.putExtra("length", "TESTLENGTH");
                    intent.putExtra("faceCut","TESTFACECUT");
                    intent.putExtra("image","");
                    context.startActivity(intent);
                }
            });
        }
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
