package com.example.mayank.dotusermodule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class ActivityServiceInfo extends AppCompatActivity {

    private ImageView sb,lb,img;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private TextView rem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.activity_serviceinfo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img = (ImageView) findViewById(R.id.si_img);
        sb = (ImageView) findViewById(R.id.si_sb);
        lb = (ImageView) findViewById(R.id.si_lb);

        Intent intent = getIntent();

        Data temp = new Data(intent.getStringExtra("name"),intent.getStringExtra("gender"),intent.getStringExtra("length"),intent.getStringExtra("quality"),intent.getStringExtra("faceCut"),intent.getStringExtra("image"));

        if(!intent.getStringExtra("image").equals("")) {
            try {
                Bitmap myBitmapAgain = decodeBase64(intent.getStringExtra("image"));
                img.setImageBitmap(myBitmapAgain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        lb.setOnClickListener(new View.OnClickListener() {
            boolean flag = false;
            @Override
            public void onClick(View v) {
                if(flag) {
                    flag = false;
                    lb.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                }
                else {
                    flag = true;
                    lb.setImageResource(R.drawable.ic_favorite_red_500_18dp);
                }
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.item_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new AdapterInfo(temp);
        mRecyclerView.setAdapter(mAdapter);

        rem = (TextView) findViewById(R.id.rem_from_cart2);
        rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ActivityCart.class);
                startActivity(intent);
            }
        });

    }

    public static Bitmap decodeBase64(String input) throws IOException
    {
        Log.d("img",input);
        byte[] decodedBytes = Base64.decode(input,0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
