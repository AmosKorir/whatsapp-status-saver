package prediction.football.goal.cup.world.com.whatsappstatussaver;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private File[] listFile;
    private String[] FilePathStrings;
    private String[] FileNameStrings;
   ArrayList<Model> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        File root=Environment.getExternalStorageDirectory();
        File statusFolder=new File(root +"/WhatsApp/");
        statusFolder=new File(statusFolder+"/Media/");
        statusFolder=new File(statusFolder+"/.Statuses/");
        GridView gridView=(GridView)findViewById(R.id.images);
        arrayList=new ArrayList<Model>();


        if( statusFolder.exists() ){
            String [] names;

            listFile = statusFolder.listFiles();
            // List file path for Images
             FilePathStrings = new String[listFile.length];
            // List file path for Image file names
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
               Model m=new Model();
               m.setFile(listFile[i]);
               arrayList.add(m);

            }

            Adapter  adapter=new Adapter(Main2Activity.this,R.layout.imagerow,arrayList);
            gridView.setAdapter(adapter);



        }else {

            Toast.makeText(this,"not working",Toast.LENGTH_LONG).show();
        }

    }

}
