package prediction.football.goal.cup.world.com.whatsappstatussaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<Model> {
    int layout;
    Context context;
    ArrayList<Model> models;

    public VideoAdapter(Context context, int resource, ArrayList<Model> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layout=resource;
        models=objects;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.imagerow, parent, false);
        }
        // Lookup view for data population
        ImageView image=(ImageView)convertView.findViewById(R.id.img);
        // Populate the data into the template view using the data object

        String path=models.get(position).file.getAbsolutePath();
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        Bitmap extractedImage = media.getFrameAtTime();

        image.setImageBitmap(extractedImage);
        // Return the completed view to render on screen
        return convertView;
    }}
