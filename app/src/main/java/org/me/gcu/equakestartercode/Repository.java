package org.me.gcu.equakestartercode;
//Aaron Amjid S1626987
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Repository {
/*
Repository class.
This class is designed to handle the parsing and storing of the data which is passed to other components
upon request.
 */
    private ArrayList<QuakeData> datastore = new ArrayList<QuakeData>();

    public ArrayList getXMLData() { //Gets the raw data from the URL
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";
        String quakeurl = "";
        String rawdata = "";
        ArrayList<QuakeData> datalist = new ArrayList<QuakeData>();
        QuakeData quake = new QuakeData();
        try {
            quakeurl = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
            Log.e("MyTag", "in try");
            aurl = new URL(quakeurl);
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            Log.e("MyTag", "after ready");
            //
            // Now read the data. Make sure that there are no specific hedrs
            // in the data file that you need to ignore.
            // The useful data that you need is in each of the item entries
            //
            while ((inputLine = in.readLine()) != null) {
                rawdata = rawdata + inputLine;
                Log.e("MyTag", inputLine);

            }
            in.close();
        } catch (IOException ae) {
            Log.e("MyTag", "ioexception in run");
        }
        try {                                               //XMLPullParser code, parses the raw data generated
            Log.e("StartTag", "Running Parser");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(rawdata));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                Log.e("StartTag", "In Document");
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        Log.e("StartTag", "In Item");
                    }
                    else if (xpp.getName().equalsIgnoreCase("title")) {
                            String temp = xpp.nextText();
                            quake.setTitle(temp);
                            try{
                                double mag = Double.parseDouble(temp.replaceAll(".*?([\\d.]+).*", "$1")); //Uses a java regex expression to get double values from title, the only one being the magnitude.
                                quake.setMagnitude(mag);
                            }
                            catch (NumberFormatException e){
                                e.printStackTrace();
                            }
                            Log.e("TitleTag", temp);
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                             Log.e("StartTag", "In Description");
                            String temp = xpp.nextText();
                            quake.setDescription(temp);
                        }
                          else if (xpp.getName().equalsIgnoreCase("link")) {
                             Log.e("StartTag", "In Link");
                            String temp = xpp.nextText();
                            quake.setLink(temp);
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            String temp = xpp.nextText();
                            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                            Date date = formatter.parse(temp);
                            quake.setPubDate(date);
                        } else if (xpp.getName().equalsIgnoreCase("category")) {
                            Log.e("StartTag", "In Category");
                            String temp = xpp.nextText();
                            quake.setCategory(temp);
                        } else if (xpp.getName().equalsIgnoreCase("lat")) {
                            Log.e("StartTag", "In GeoLat");
                            String temp = xpp.nextText();
                            double num = Double.parseDouble(temp);
                            quake.setGeoLat(num);
                        } else if (xpp.getName().equalsIgnoreCase("long")) {
                            String temp = xpp.nextText();
                            double num = Double.parseDouble(temp);
                            quake.setGeoLong(num);
                        }
                    }
                else if (eventType == XmlPullParser.END_TAG) { //Creates quakedata object after end of item tag.
                    if (xpp.getName().equalsIgnoreCase("item")) {
                        datalist.add(quake);
                        Log.e("QuakeTag", quake.toString());
                        quake = new QuakeData();
                    }
                }
                eventType = xpp.next();
                }

        } catch (XmlPullParserException | IOException  ae) {
            Log.e("MyTag", "Exception in io");
        }
        catch(ParseException ae){
            Log.e("MyTag", "Exception in parse");
        }
        Log.e("EndTag", datalist.toString());
        this.setDatastore(datalist); //Sets the data store variable for later use
        return datalist; //Returns the final list of QuakeData objects.
    }

    //Getters and Setters
    public ArrayList<QuakeData> getDatastore() {
        return datastore;
    }

    public void setDatastore(ArrayList<QuakeData> array){
        this.datastore = array;
    }

    public ArrayList<QuakeData> getItemsInDateRange(Date date1, Date date2) {

        ArrayList <QuakeData> dataarray = this.datastore;
        ArrayList<QuakeData> sortedarray = new ArrayList<QuakeData>();
        for (QuakeData quake : dataarray){
            if (date1.before(quake.getPubDate()) && date2.after(quake.getPubDate())){
                sortedarray.add(quake);
            }
        }
        return sortedarray;
    }
}
