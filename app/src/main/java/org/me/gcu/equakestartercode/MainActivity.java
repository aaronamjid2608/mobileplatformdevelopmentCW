package org.me.gcu.equakestartercode;
//Aaron Amjid S1626987
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    /*
    MainActivity
    Background threads are handled using ExecutorService which can generate thread pools and run code on background threads automatically.
    Main design of the application has a listview and a data panel. The layout of the two main components differs on orientation.
     */
    private TextView rawDataDisplay;
    private Button mapButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private ArrayList<QuakeData> arraydata;
    ListView listview;
    boolean refreshdata = true;
    FrameLayout framelayout;
    ExecutorService executorService = Executors.newFixedThreadPool(3); //executorService creates a thread pool of 3 threads

    public Repository repo = new Repository(); //initialise Repository class
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        mapButton = (Button)findViewById(R.id.mapbutton);
        mapButton.setOnClickListener(new View.OnClickListener() { //Button to manually load the map view again
            public void onClick(View v) {
                MapsFragment mapfragment = new MapsFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout,mapfragment,"mapfrag")
                        .commit();
                mapfragment.setMarkerData(arraydata);
            }
        });

        Log.e("MyTag","after mapButton");
        listview = (ListView)findViewById(R.id.quakelist);
        framelayout = (FrameLayout)findViewById(R.id.frame_layout);
        startProgress();
        MapsFragment mapfragment = new MapsFragment(); //create Google Maps fragment which opens by default
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,mapfragment,"mapfrag")
                .commit();
    }

    public void startProgress()
    {
        executorService.execute(new Runnable() { //executorservice automatically executes the runnable command on an open thread
            @Override
            public void run() {
                arraydata = repo.getXMLData(); //Calls the repository's getXMLData method to parse the data and return in an arraylist
                QuakeAdapter adapter = new QuakeAdapter(getApplicationContext(), arraydata);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() { //Runs code to populate listview on the main thread after getting data
                        listview.setAdapter(adapter);
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { //Create listener for listview item on click
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                QuakeData q = (QuakeData) parent.getItemAtPosition(position);
                                DetailFragment detailfragment = new DetailFragment(); //Displays the details fragment with the details of the earthquake
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frame_layout,detailfragment,"detailfrag")
                                        .commit();
                                detailfragment.setQuakeDataToDisplay(q);
                            }
                        });
                        MapsFragment mapfragment = (MapsFragment)getSupportFragmentManager().findFragmentByTag("mapfrag");
                        mapfragment.setMarkerData(arraydata);
                    }
                });
                while(refreshdata){
                    try {
                        Thread.sleep(300000); //Thread sleep for 5 minutes before updating
                        arraydata = repo.getXMLData();
                        Log.d("Update Data", "Updating Data");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("UI thread", "UI Thread Updating");
                                adapter.notifyDataSetChanged(); //Updates dataset inside the adapter
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}