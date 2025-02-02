package com.example.testmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnInfoWindowClickListener{
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 5000;
    double latitude,longitude;
    Polyline polyline = null;
    LatLng latLng;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng RR = new LatLng(10.110457,78.820328);
    LatLng HP = new LatLng(10.101053,78.840181);
    LatLng IN = new LatLng(10.099726,78.849584);
    LatLng BH = new LatLng(10.120343,78.792078);
    ArrayList<String> title = new ArrayList<>();
    String rr = "ESSR PETROLEUM";
    String hp = "HINDUSTHAN PETROLEUM";
    String in = "INDIAN OIL";
    String bh = "BHARAT PETROLEUM";
    String msg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        arrayList.add(RR);
        arrayList.add(HP);
        arrayList.add(IN);
        arrayList.add(BH);
        title.add(rr);
        title.add(hp);
        title.add(in);
        title.add(bh);
        checkLocationPermission();
        Log.d("wrk","msg");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);

        mapFragment.getMapAsync(this);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (client == null) {
                        bulidGoogleApiClient();
                    }
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Location Permission already granted
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        } else {
            //Request Location Permission
            checkLocationPermission();
        }
        //Findroutes(RR,HP);

    }


    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();


    }

    @Override
    protected void onPause() {
        super.onPause();
        //stop location updates when Activity is no longer active
        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //lastlocation = location;
        if(currentLocationMarker != null)
        {
            currentLocationMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        currentLocationMarker = mMap.addMarker(markerOptions);
        for(int i = 0;i<arrayList.size();i++){
            mMap.addMarker(new MarkerOptions().position(arrayList.get(i))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker4))
                    .title(title.get(i)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        mMap.setOnInfoWindowClickListener(this);
        if(client != null)
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this);
        }


    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);




        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }

    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;

    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MapActivity.this,"not working",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String mk = marker.getTitle();
        String msg1=null;
        //LatLng destination=null;
        switch (mk) {
            case "ESSR PETROLEUM":
                //destination = marker.getPosition();
                msg = "ESSAR PETROLEUM";
                msg1 = "Distance:600m";
                break;
            case "HINDUSTHAN PETROLEUM":
                //destination=marker.getPosition();
                msg = "HINDUSTHAN PETROLEUM";
                msg1 = "Distance:1km";

                break;
            case "INDIAN OIL":
                //destination=marker.getPosition();
                msg = "INDIAN OIL";
                msg1 = "Distance:2km";
                break;
            case "BHARAT PETROLEUM":
                //destination=marker.getPosition();
                msg = "BHARAT PETROLEUM";
                msg1 = "Distance:3km";
                break;
            default:
                msg = "UNAVAILABLE";
                break;
        }
        final Dialog dialog = new Dialog(MapActivity.this);
        dialog.setContentView(R.layout.custombox);
        dialog.setTitle("Details");
        TextView txt=(TextView)dialog.findViewById(R.id.textView);
        txt.setText(msg);
        TextView txt1=(TextView)dialog.findViewById(R.id.textView2);
        txt1.setText(msg1);
        TextView txt2=(TextView)dialog.findViewById(R.id.textView3);
        txt2.setText("Cost:83.63/lts");
        dialog.show();
        Button btn=(Button)dialog.findViewById(R.id.cancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button btn1 = (Button)dialog.findViewById(R.id.navigate);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this,UpdateData.class);
                intent.putExtra("BunkName",msg);
                finish();
                startActivity(intent);
            }
        });

        /*AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setMessage(msg+"\n"+msg1);
        final LatLng finalDestination = destination;
        builder.setPositiveButton("Navigate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MapActivity.this,UpdateData.class);
                intent.putExtra("BunkName",msg);
                startActivity(intent);
               /* if(polyline!=null)
                    polyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(latLng,finalDestination).color(R.color.colorAccent)
                        .clickable(true);
                polyline=mMap.addPolyline(polylineOptions);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();*/


    }


    /*public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            Toast.makeText(MainActivity.this,"Unable to get location", Toast.LENGTH_LONG).show();
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyD4uStbluZBnwKADWRtCPalZoddDXdNQbs")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(MainActivity.this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex ) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(RR);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                polyOptions.color(getResources().getColor(R.color.colorPrimary));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);

            }
            else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(RR,HP);
    }*/
    
}