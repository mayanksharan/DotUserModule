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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        storageReference = storage.getReferenceFromUrl("gs://dotz-2f65f.appspot.com/");
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
        heightpixels = MainActivity.getHeight();
        eh.rl.getLayoutParams().height = (heightpixels) / 2;

        return eh;
    }

    public static Bitmap decodeBase64(String input) throws IOException
    {
        Log.d("img",input);
        byte[] decodedBytes = Base64.decode(input,0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public Bitmap getbmpfromURL(String surl){
        Log.d("img",surl);
        try {
            URL url = new URL(surl);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            urlcon.setDoInput(true);
            urlcon.connect();
            InputStream in = urlcon.getInputStream();
            Bitmap mIcon = BitmapFactory.decodeStream(in);
            return  mIcon;
        } catch (Exception e) {
            Log.e("Error", "jhsd");
            e.printStackTrace();
            return null;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ElementHolder h, final int position) {

//        StorageReference imgRef = storageReference.child("Hairstyles/h2.jpg");

        boolean flag = false;

        if(mDataset.size()>0) {


            h.tv.setText(mDataset.get(position).name);
            StorageReference imgRef = storage.getReferenceFromUrl(mDataset.get(position).image);

            final long ONE_MEGABYTE = 1024 * 1024;
            imgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Log.d("kjdh","dhsu");
                    h.iv.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(bytes, 0 , bytes.length),120,120,false));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception exception) {
                    // Handle any errors
                }
            });


//            h.iv.setImageBitmap(getbmpfromURL(mDataset.get(position).image));
//            URL url = null;
//            try {
//                url = new URL(mDataset.get(position).image);
//                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                h.iv.setImageBitmap(bmp);
//
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }

//            try {
//                Bitmap myBitmapAgain = decodeBase64(mDataset.get(position).image);
////                h.iv.setImageBitmap(Bitmap.createScaledBitmap(myBitmapAgain,120,120,false));
//                h.iv.setImageBitmap(myBitmapAgain);
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }
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
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://crackling-fire-4425.firebaseio.com");

                Map<String,Object> map = new HashMap<String, Object>();
                map.put(mDataset.get(position).name,"text");
                databaseReference.child("cart").updateChildren(map);


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
