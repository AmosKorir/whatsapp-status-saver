package prediction.football.goal.cup.world.com.whatsappstatussaver;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.tappx.sdk.android.TappxInterstitial;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

   static String defaul="1";


   TabLayout tabLayout;
    private PlaceholderFragment.SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    static TabLayout.Tab images,video;
    TappxInterstitial tappxInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("type", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        String current="";
        if (sharedPreferences.contains("key")){
         current=sharedPreferences.getString("key",null);
        }

        if(!current.isEmpty()){
            defaul=current;
        }

        tappxInterstitial = new TappxInterstitial(MainActivity.this, "Pub-40838-Android-0233");
        tappxInterstitial.setAutoShowWhenReady(true);
        tappxInterstitial.loadAd();




        MobileAds.initialize(this,"ca-app-pub-2839585061501474~6968450450");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter));
        mAdView = findViewById(R.id.adView);
       // mAdView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mAdView.setVisibility(View.VISIBLE);
            }

        });




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout=(TabLayout)findViewById(R.id.tablayout);
          images=tabLayout.newTab();
        images.setText("Images");

        video=tabLayout.newTab();
        video.setText("Video");
        tabLayout.addTab(images);
        tabLayout.addTab(video);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new PlaceholderFragment.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

       mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               if (position==1){
                   video.select();
               }else {
                   images.select();
               }

           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos=tab.getPosition();
                if(pos==0){

                    mViewPager.setCurrentItem(0);

                }
                if(pos==1){

                    mViewPager.setCurrentItem(1);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( tappxInterstitial.isReady()){
                    tappxInterstitial.show();
                }

                createDialog();
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }else{
            if( tappxInterstitial.isReady()){
                tappxInterstitial.show();
            }
        }


    }




    //function to create a dialog

    public  void createDialog(){
        Button Gb,What;
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialoglayout);

        Gb=(Button)dialog.findViewById(R.id.gb);
        What=(Button)dialog.findViewById(R.id.w);
        What.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("key","1");
                editor.commit();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });
        Gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("key","0");
                editor.commit();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                finish();
            }
        });

        dialog.show();

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        int MY_PERMISSIONS_REQUEST_READ_CONTACTS=900;
        File[] listFile;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int num = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView;
            if (num == 1) {
                rootView = inflater.inflate(R.layout.fragment_main, container, false);
                GridView gridView = (GridView) rootView.findViewById(R.id.images);
                images.select();
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    String name=null;
                    if(defaul=="1"){
                        name="/WhatsApp/";

                    }else {
                        name="/GBWhatsApp/";
                    }

                    File root = Environment.getExternalStorageDirectory();
//                    File statusFolder=new File(root +"/WhatsApp/");
                    File statusFolder = new File(root + name);
                    statusFolder = new File(statusFolder + "/Media/");
                    statusFolder = new File(statusFolder + "/.Statuses/");
                    final ArrayList<Model> arrayList = new ArrayList<Model>();
                    if (statusFolder.exists()) {
                        String[] names;

                        listFile = statusFolder.listFiles();

                        for (int i = 0; i < listFile.length; i++) {
                            Model m = new Model();
                            m.setFile(listFile[i]);
                            arrayList.add(m);

                        }

                        Adapter adapter = new Adapter(getActivity(), R.layout.imagerow, arrayList);
                        gridView.setAdapter(adapter);

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Model model=arrayList.get(position);
                                String paht=model.file.getAbsolutePath();
                                startActivity(new Intent(getActivity(),Imageviewer.class)
                                        .putExtra("path",paht)
                                );
                            }
                        });


                    } else {

                        Toast.makeText(getActivity(), "not working", Toast.LENGTH_LONG).show();
                    }


                }
                return rootView;
            }

            if (num == 2) {
                rootView = inflater.inflate(R.layout.videolayout, container, false);
                GridView gridView = (GridView) rootView.findViewById(R.id.images);

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    String name=null;
                    if(defaul=="1"){
                        name="/WhatsApp/";

                    }else {
                        name="/GBWhatsApp/";
                    }
                    // Permission has already been granted
                    File root = Environment.getExternalStorageDirectory();
//                    File statusFolder=new File(root +"/WhatsApp/");
                    File statusFolder = new File(root + name);
                    statusFolder = new File(statusFolder + "/Media/");
                    statusFolder = new File(statusFolder + "/.Statuses/");
                    final ArrayList<Model> arrayList = new ArrayList<Model>();
                    if (statusFolder.exists()) {
                        String[] names;

                        listFile = statusFolder.listFiles();

                        for (int i = 0; i < listFile.length; i++) {
                            Model m = new Model();
                            m.setFile(listFile[i]);

                            if (listFile[i].getName().contains(".mp4")){

                                arrayList.add(m);
                            }


                        }

                        final VideoAdapter adapter = new VideoAdapter(getActivity(), R.layout.imagerow, arrayList);
                        gridView.setAdapter(adapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Model model=arrayList.get(position);
                                String paht=model.file.getAbsolutePath();
                                startActivity(new Intent(getActivity(),VideoActivity.class)
                                        .putExtra("path",paht)
                                );
                            }
                        });


                    } else {

                        Toast.makeText(getActivity(), "not working", Toast.LENGTH_LONG).show();
                    }


                }
                return rootView;
            }




              return null;

                 }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

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
            // Show 3 total pages.
            return 2;
        }
    }
}}
