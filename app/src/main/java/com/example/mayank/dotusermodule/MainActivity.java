package com.example.mayank.dotusermodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Context mContext;
    private Activity mActivity;

    private static int height;
    static int fil = 0;

    public static int geth()
    {
        return height * 5 / 6;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Get Height of the screen in pixels
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        height = metrics.heightPixels;

        ImageView im = (ImageView) findViewById(R.id.cart);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ActivityCart.class);
                startActivity(intent);
            }
        });

        try
        {
            Intent intent = getIntent();
            fil = intent.getIntExtra("Filter",0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class PlaceholderFragment extends Fragment {

        private static Context mContext;
        private ArrayList<Data> mDataset;
        RecyclerView mRecyclerView;
        static int secnum;
        private TextView tv;
        private static int lengthf = 0, qualityf = 0,fcf = 0;

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

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.item_recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext,2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            final RecyclerView.Adapter mAdapter = new AdapterItems(mDataset,mContext);
            mRecyclerView.setAdapter(mAdapter);

            tv = (TextView) rootView.findViewById(R.id.rem_from_cart);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(rootView.getContext(),ActivityCart.class);
                    startActivity(intent);
                }
            });

            ImageView iv = (ImageView) rootView.findViewById(R.id.filter_button);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(rootView.getContext(),ActivityFilter.class);
                    startActivity(intent);
//                    ((Activity)getContext()).overridePendingTransition(R.transition.slide_in_up, R.transition.slide_out_up);
                }
            });


            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://crackling-fire-4425.firebaseio.com");
            databaseReference.child("data").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int temp = fil;
                    lengthf = temp % 10;
                    temp /= 10;
                    qualityf = temp % 10;
                    temp /= 10;
                    fcf = temp;
                    setDataset(dataSnapshot);
                    Log.d("LLL", mDataset.size() + "");
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Hairstyles";
                case 1:
                    return "Beards";
//                case 2:
//                    return "SECTION 3";
            }
            return null;
        }
    }
}
