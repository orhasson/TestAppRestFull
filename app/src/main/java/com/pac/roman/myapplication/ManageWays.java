package com.pac.roman.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.codehaus.jackson.map.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

import objects.LocationObject;
import objects.Path;
import objects.WaysManagerObject;
import providers.ServerConnection;

/**
 * Created by 123123123 on 02/05/2015.
 */
public class ManageWays extends FragmentActivity implements LocationListener {
    Spinner sp;
    List<String> li;
    Button StartRecording;
    Button StopRecording;
    List<LocationObject> LocMap;
    ArrayList<Path> allPathsFromServer;
    //UserAdapter adapter = new UserAdapter(this, SetOfLocPaths);
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5; // 5 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 2000;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    Path NPath;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_ways);
        StartRecording = (Button)findViewById(R.id.Startrec);
        StopRecording = (Button)findViewById(R.id.Stoprec);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        li = new ArrayList<>();
        sp=(Spinner) findViewById(R.id.waysSpinner);
        getUserPaths();
    }

    public void getUserPaths(){
        WaysManagerObject waysManagerObject = new WaysManagerObject();
        String msgFromServer = "";
        ObjectMapper objectMapper = new ObjectMapper();
        waysManagerObject.setPassword(StaticDataContainer.USER_PASS);
        waysManagerObject.setUserName(StaticDataContainer.USER_NAME);
        try {
            ServerConnection serverConnection = ServerConnection.getServerConnection(waysManagerObject);
            serverConnection.start();
            while (msgFromServer.equals("")) {
                Thread.sleep(100);
                msgFromServer = serverConnection.getMsgFromServer();
            }
            if(msgFromServer.equals("User has no recorded paths")){
                allPathsFromServer=new ArrayList<>();
            }
            else if(msgFromServer.equals("Failed to get user paths")){
                    //todo: add ex. popup
               }
            else{
                allPathsFromServer = objectMapper.readValue(msgFromServer, objectMapper.getTypeFactory().constructCollectionType(List.class, Path.class));
            }
        }catch (Exception e){}

        if(!allPathsFromServer.isEmpty()){
            for(Path p : allPathsFromServer)
                li.add(p.getPathName());
        }
        add();
    }


    public Location getLocation(){

        try {
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
      catch (Exception e) {e.printStackTrace();}
    return location;
}

    public void OnClickStartRec(View view) {
        LocMap=new ArrayList<>();
        getLocation();
        }
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void OnClickStopRec(View view){
        if(locationManager != null){
            locationManager.removeUpdates(ManageWays.this);
        }

      //  listView.setAdapter(adapter);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input= new EditText(ManageWays.this);
        alert.setTitle("Save Entry");
        alert.setMessage("Do you want to save this entry?");
        alert.setView(input);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                NPath = new Path();
                NPath.setPathName(input.getText().toString());
                li.add(input.getText().toString());
                add();
                sp.getSelectedItem();
                sendToServer();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }
    private void add() {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,li);
        sp.setAdapter(adp);
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void sendToServer(){
        NPath.setListOfLocationObjects(LocMap);
        NPath.setParentName(StaticDataContainer.USER_NAME);
        NPath.setParentPassword(StaticDataContainer.USER_PASS);
        allPathsFromServer.add(NPath);
        String msgFromServer = "";
        try {
            ServerConnection serverConnection = ServerConnection.getServerConnection(NPath);
            serverConnection.start();

            while (msgFromServer.equals("")) {
                Thread.sleep(100);
                msgFromServer = serverConnection.getMsgFromServer();
            }
        }catch (Exception e){}
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    @Override
    public void onLocationChanged(Location location) {
        if (canGetLocation()) {
            LocationObject a = new LocationObject();
                a.setLatitude(getLatitude());
                a.setLongitude(getLongitude());
            Toast.makeText(this,"Lat: "+Double.toString(location.getLatitude())+","+"Long: "+Double.toString(location.getLongitude()), Toast.LENGTH_SHORT).show();
            LocMap.add(a);
            }

        }
    public void SeePathOnMap(View view){
        StaticDataContainer.SPINNER_ITEM = allPathsFromServer.get(sp.getSelectedItemPosition()).getListOfLocationObjects();
        Intent intent = new Intent(this, ShowPathOnMap.class);
        //startActivity(intent);
        setUpMapIfNeeded();
        ShowPath();
    }

    public void ShowPath(){
        PolylineOptions line = new PolylineOptions();
        line.width(4f).color(777);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        for (LocationObject locationObject : StaticDataContainer.SPINNER_ITEM) {
            /*if (i == 0) { //todo here we can add first and last icons points, like "start" and "finish"
                MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_a));
                mGoogleMap.addMarker(startMarkerOptions);
            } else if (i == mPoints.size() - 1) {
                MarkerOptions endMarkerOptions = new MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_b));
                mGoogleMap.addMarker(endMarkerOptions);
            }*/
            LatLng newPoint = new LatLng(locationObject.getLatitude(), locationObject.getLongitude());
            MarkerOptions marker=new MarkerOptions();
            marker.position(newPoint);
            mMap.addMarker(marker);
            line.add(newPoint);
            latLngBuilder.include(newPoint);
        }
        line.color(Color.BLUE).width(4);
        mMap.addPolyline(line);

        //int size = getResources().getDisplayMetrics().widthPixels;
        //LatLngBounds latLngBounds = latLngBuilder.build();
        //CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
        //mMap.moveCamera(track);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(StaticDataContainer.SPINNER_ITEM.get(0).getLatitude(), StaticDataContainer.SPINNER_ITEM.get(0).getLongitude()), 14f) );
    }



    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Mapfrag))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //   setUpMap();
            }
        }
    }



        @Override
        public void onProviderDisabled (String provider){
        }

        @Override
        public void onProviderEnabled (String provider){
        }

        @Override
        public void onStatusChanged (String provider,int status, Bundle extras){
        }


}
