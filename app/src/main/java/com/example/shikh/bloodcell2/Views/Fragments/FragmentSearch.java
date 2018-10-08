package com.example.shikh.bloodcell2.Views.Fragments;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shikh.bloodcell2.R;
import com.example.shikh.bloodcell2.Utils.Constants;
import com.example.shikh.bloodcell2.Utils.Hero;
import com.example.shikh.bloodcell2.Utils.ListViewAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentSearch extends Fragment implements OnMapReadyCallback,LocationListener,GoogleMap.OnMarkerClickListener {

    private static final String JSON_URL = "http://rrsaikat.mydiscussion.net/myjson/location.php";
    private ProgressDialog progressDialog;
    private GoogleMap mMap;
    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Hero> heroList;
    private MapView mapView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

//        listView = (ListView) root.findViewById(R.id.listView);
        heroList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        //this method will fetch and parse the data
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        progressDialog.setMessage("Sending Map Request...");
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
            progressDialog.show();

            //creating a string request to send request to the url
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    JSON_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                //getting the whole json object from the response
                                JSONObject obj = new JSONObject(response);
                                Log.d("heyj","aaaa");
                                //we have the array named hero inside the object
                                //so here we are getting that json array
                                JSONArray heroArray = obj.getJSONArray("FL");
                                Log.d("heyj","aaaa");
                                //now looping through all the elements of the json array
                                for (int i = 0; i < heroArray.length(); i++) {
                                    //getting the json object of the particular index inside the array

                                    JSONObject jsonObject1=heroArray.getJSONObject(i);
                                    Log.d("hey",jsonObject1.getString("1"));
                                    String lat_i = jsonObject1.getString("1");
                                    String long_i = jsonObject1.getString("2");

                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(lat_i) , Double.parseDouble(long_i)))
                                            .title(Double.valueOf(lat_i).toString() + "," + Double.valueOf(long_i).toString())
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                                    );

                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.6850,90.3563), 6.0f));
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}




//package com.example.shikh.bloodcell2.Views.Fragments;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.shikh.bloodcell2.R;
//import com.example.shikh.bloodcell2.Utils.Constants;
//import com.example.shikh.bloodcell2.Utils.Hero;
//import com.example.shikh.bloodcell2.Utils.ListViewAdapter;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class FragmentSearch extends Fragment {
//
//    private static final String JSON_URL = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";
//    private ProgressDialog progressDialog;
//
//    //listview object
//    ListView listView;
//
//    //the hero list where we will store all the hero objects after parsing json
//    List<Hero> heroList;
//
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View root = inflater.inflate(R.layout.fragment_search, container, false);
//
//        listView = (ListView) root.findViewById(R.id.listView);
//        heroList = new ArrayList<>();
//        progressDialog = new ProgressDialog(getContext());
//        //this method will fetch and parse the data
//        loadHeroList();
//        return root;
//    }
//
//        private void loadHeroList() {
//            //getting the progressbar
//
//            progressDialog.setMessage("Sending Donation Request...");
//            progressDialog.show();
//            //creating a string request to send request to the url
//            StringRequest stringRequest = new StringRequest(
//                    Request.Method.POST,
//                    JSON_URL,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            progressDialog.dismiss();
//                            try {
//                                //getting the whole json object from the response
//                                JSONObject obj = new JSONObject(response);
//
//                                //we have the array named hero inside the object
//                                //so here we are getting that json array
//                                JSONArray heroArray = obj.getJSONArray("heroes");
//
//                                //now looping through all the elements of the json array
//                                for (int i = 0; i < heroArray.length(); i++) {
//                                    //getting the json object of the particular index inside the array
//                                    JSONObject heroObject = heroArray.getJSONObject(i);
//
//                                    //creating a hero object and giving them the values from json object
//                                    Hero hero = new Hero(heroObject.getString("name"),
//                                            heroObject.getString("imageurl"));
//
//                                    //adding the hero to herolist
//                                    heroList.add(hero);
//                                }
//
//                                //creating custom adapter object
//                                ListViewAdapter adapter = new ListViewAdapter(heroList, getContext());
//
//                                //adding the adapter to listview
//                                listView.setAdapter(adapter);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            progressDialog.hide();
//                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//            //creating a request queue
//            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//
//            //adding the string request to request queue
//            requestQueue.add(stringRequest);
//        }
//    }
