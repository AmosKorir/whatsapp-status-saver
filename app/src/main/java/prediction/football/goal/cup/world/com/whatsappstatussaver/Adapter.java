package prediction.football.goal.cup.world.com.whatsappstatussaver;

import android.content.Context;
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

public class Adapter extends ArrayAdapter<Model> {
    int layout;
    Context context;
   ArrayList<Model> models;

    public Adapter(Context context, int resource, ArrayList<Model> objects) {
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

        image.setImageURI(Uri.fromFile(models.get(position).file));
        // Return the completed view to render on screen
        return convertView;
}}
