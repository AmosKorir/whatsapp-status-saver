package prediction.football.goal.cup.world.com.whatsappstatussaver;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageAdapter extends ArrayAdapter {
    static int MY_STORAGE_REQUEST=99;

    public ImageAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
    }


    public static void apkSave(Context ctx, String url, String name){
        int permission= ContextCompat.checkSelfPermission(ctx,android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if((ActivityCompat.shouldShowRequestPermissionRationale((Activity) ctx,
                    Manifest.permission.CAMERA))) {







            }else{
                ActivityCompat.requestPermissions((Activity) ctx,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_STORAGE_REQUEST);



            }


        }else {
            // Toast.makeText(ctx, "Saved to ApksExtractor", Toast.LENGTH_SHORT).show();
            String fileName=name+".";
//            checkAndCreateDirectory("ApksExtractor");
            File root= Environment.getExternalStorageDirectory();
            File source=new File(url);
            try {
                FileInputStream in=new FileInputStream(source);
                FileOutputStream f = new FileOutputStream(new File(root + "/ApksExtractor/", fileName+"apk"));

                Uri uri=Uri.fromFile(root);
                byte[]bArr=new byte[1024];
                while(true){

                    try {
                        int read = in.read(bArr);
                        if (read < 0) {
                            break;
                        }
                        f.write(bArr,0,read);
                    }catch (Exception e){
                        Toast.makeText(ctx,e+"",Toast.LENGTH_SHORT).show();
                    }


                    f.close();
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
