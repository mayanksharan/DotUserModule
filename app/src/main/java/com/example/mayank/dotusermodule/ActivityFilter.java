package com.example.mayank.dotusermodule;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Map;
import java.util.Set;

public class ActivityFilter extends AppCompatActivity {

    private Spinner s1,s2,s3;
    private Button b1,b2;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_filter);
        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        b1 = (Button) findViewById(R.id.but1);
        b2 = (Button) findViewById(R.id.but2);

        mContext = this;

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fil = s1.getSelectedItemPosition() + (10 * s2.getSelectedItemPosition()) + (100 * s3.getSelectedItemPosition());
                Intent intent = new Intent(mContext,MainActivity.class);
                intent.putExtra("Filter",fil);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
