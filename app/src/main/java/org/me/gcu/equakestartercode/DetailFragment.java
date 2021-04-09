package org.me.gcu.equakestartercode;
//Aaron Amjid S1626987
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/*
DetailFragment class
Fragment class designed to display the details of a specified earthquake, uses a public setter method to get the quake data.
 */
public class DetailFragment extends Fragment {
    TextView title;
    TextView description;
    TextView link;
    TextView category;
    TextView coordinates;
    QuakeData quakedatatodisplay = new QuakeData();

    public DetailFragment() {

    }

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    public void setQuakeDataToDisplay(QuakeData q){
        quakedatatodisplay = q;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        title = (TextView)view.findViewById(R.id.earthquaketitle);
        description = (TextView)view.findViewById(R.id.description);
        link = (TextView)view.findViewById(R.id.link);
        category = (TextView)view.findViewById(R.id.category);
        coordinates = (TextView)view.findViewById(R.id.coordinates);

        title.setText("Title: "+ quakedatatodisplay.getTitle()); //Set text for details fragment
        description.setText("Description: " + quakedatatodisplay.getDescription());
        link.setText("Weblink: " + quakedatatodisplay.getLink());
        category.setText("Category: " + quakedatatodisplay.getCategory());
        coordinates.setText("Co-ordinates: "+ quakedatatodisplay.getGeoLat() + "," + quakedatatodisplay.getGeoLong() );
        return view;
    }
}