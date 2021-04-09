package org.me.gcu.equakestartercode;
//Aaron Amjid S1626987
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment {
    /*
    Google Maps fragment
     */
    ArrayList<QuakeData> maparray = new ArrayList<QuakeData>();
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

                LatLng location = new LatLng(56.41,-6.21);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,5));
                for(QuakeData q : maparray){
                LatLng markerlocation = new LatLng(q.getGeoLat(),q.getGeoLong());
                String colorurl;
                    double mag = q.getMagnitude();
                    //switch statement doesn't accept double values, need to use ugly nested if statements
                    if(mag < 1){
                        googleMap.addMarker(new MarkerOptions().position(markerlocation).title(q.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                        //Uses BitmapDescriptorFactory to change the hue of the default marker to the appropriate value.
                    }
                    else if(mag >= 1 && mag < 1.5){
                        googleMap.addMarker(new MarkerOptions().position(markerlocation).title(q.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    }
                    else if(mag >= 1.5 && mag < 2){
                        googleMap.addMarker(new MarkerOptions().position(markerlocation).title(q.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                    else if(mag >= 2 && mag < 2.5){
                        googleMap.addMarker(new MarkerOptions().position(markerlocation).title(q.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    }
                    else{
                        googleMap.addMarker(new MarkerOptions().position(markerlocation).title(q.getTitle()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }

                }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    /*
    Setter method to get the array of quakedata objects to create markers from
     */
    public void setMarkerData(ArrayList<QuakeData> list){
        this.maparray = list;
    }
}