package prediction.football.goal.cup.world.com.whatsappstatussaver;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Imageviewer extends AppCompatActivity {
    File dest;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewer);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.inter));
        mAdView = findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mAdView.setVisibility(View.VISIBLE);
            }

        });


        Bundle extra=getIntent().getExtras();

        String path=extra.getString("path");

        final File file=new File(path);


        File root = Environment.getExternalStorageDirectory();
        File statusFolder = new File(root + "/StatusSaver/");
        if(!statusFolder.exists()){
            statusFolder.mkdir();
        }
        dest=new File(statusFolder+"/"+ file.getName());

        final Uri uri=Uri.fromFile(file);
        ImageView imageView=(ImageView)findViewById(R.id.image);
        imageView.setImageURI(uri);

        ImageButton save=(ImageButton)findViewById(R.id.save);
        final ImageButton share=(ImageButton)findViewById(R.id.sharebutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    copyFileUsingFileStreams(file,dest);
                    Toast.makeText(Imageviewer.this, "Saved ", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }
        });
    }

    private static void copyFileUsingFileStreams(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output=null;
        try {
            input=new FileInputStream(source);
            output=new FileOutputStream(dest);
            byte[] buf = new byte[1024];
             int bytesRead;
               while ((bytesRead = input.read(buf)) > 0) {

                output.write(buf, 0, bytesRead);

            }


        } catch (Exception e){
            Log.d("copying_error",e.toString());
        }finally {
            {
                input.close();
                output.close();
            }
        }


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
    }
}
