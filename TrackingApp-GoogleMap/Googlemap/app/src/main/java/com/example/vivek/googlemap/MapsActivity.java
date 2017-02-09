package com.example.vivek.googlemap;



/*vivek ravi* inclass-assignment- 12*/

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;

    private static final int DEFAULT_ZOOM = 15;
    private Location mCurrentLocation;
    private static boolean start = false;

    ArrayList<LatLng> locations;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locations = new ArrayList<LatLng>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);

        if (mCurrentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()), DEFAULT_ZOOM));
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(!start){
                    locations.clear();


                    Toast.makeText(MapsActivity.this, " Location Tracking starts", Toast.LENGTH_LONG).show();
                    start = true;
                    trackLocation();
                } else {
                    start = false;
                    Toast.makeText(MapsActivity.this, "Stoped location tracking", Toast.LENGTH_LONG).show();
                    stopTracking();
                }
            }
        });
    }

    public void trackLocation(){
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("GPS Not Enabled")
                    .setMessage("would you like to enable the GPS Settings?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            }).setCancelable(false);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    locations.add(new LatLng(location.getLatitude(), location.getLongitude()));
                    mMap.addMarker(new MarkerOptions()
                            .position(locations.get(0))
                            .title("Start Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(location.getLatitude(),
                                    location.getLongitude()), DEFAULT_ZOOM));


                    PolylineOptions polylineOptions = new PolylineOptions();


                    polylineOptions.addAll(locations);
                    polylineOptions
                            .width(5)
                            .color(Color.BLUE);


                    line = mMap.addPolyline(polylineOptions);

                    moveToBounds(line);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, mLocationListener);
        }
    }

    public void stopTracking(){
        LatLng lastLoc = locations.get(locations.size()-1);

        mMap.addMarker(new MarkerOptions()
                .position(lastLoc)
                .title("Stop Loc"));
    }

    private void moveToBounds(Polyline p){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i = 0; i < p.getPoints().size();i++){
            builder.include(p.getPoints().get(i));
        }

        LatLngBounds bounds = builder.build();
        int padding = 200; // offset from edges of the map in pixels

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.animateCamera(cu);
    }
}