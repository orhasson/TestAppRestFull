package com.pac.roman.myapplication;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import objects.LocationObject;
import objects.Path;

public class ShowPathOnMap extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_path);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(StaticDataContainer.SPINNER_ITEM.get(0).getLatitude(),StaticDataContainer.SPINNER_ITEM.get(0).getLongitude()) , 14f) );
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapPath))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
             //   setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


  /*  private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, StaticDataContainer.ZOOM_LEVEL);
        mMap.animateCamera(cameraUpdate);
    }*/
}
