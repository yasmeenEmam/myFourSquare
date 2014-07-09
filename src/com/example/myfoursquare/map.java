package com.example.myfoursquare;

import java.util.concurrent.ExecutionException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class map extends Activity {
	private GoogleMap googleMap;
	JSONObject venuesJSON;JSONObject data1,data2,data3;JSONArray jr;
	double Lngs,Lats;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.map);
	        
	    	GPSTracker gps;

	     // create class object
	        gps = new GPSTracker(map.this);

			// check if GPS enabled		
	        if(gps.canGetLocation()){
	        	
	        	 double latitude = gps.getLatitude();
	        	 Log.v("Lat", Double.toString(latitude));
	        	 double longitude = gps.getLongitude();
	        	 Log.v("Long", Double.toString(longitude));
	        	 String strm = "30.04442,31.235712";
	        	 String client_id = "T4TPMZOKYXPMLVNQEQGZFHYCOFM21YKW425HZ3UMRKRWSU4A";
	             String client_secret = "UXOQG0GCTF3JKX4VOO0U2YNQH2X1PY2SI31CDLQMMMSA3AXO";
	             String currentDateandTime = "20140706";
	        	 DefaultHttpClient httpclient = new DefaultHttpClient();
	             final HttpParams httpParams = httpclient.getParams();
	             HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
	             HttpConnectionParams.setSoTimeout(httpParams, 30000);
	             String[] url = {"https://api.foursquare.com/v2/venues/search?intent=checkin&ll="+strm+"&client_id="+client_id+"&client_secret="+client_secret+"&v="+currentDateandTime}; 
	             String result = "EmptyVenuesReult";
	             
				try {
					venuesJSON = new explore_venues().execute(url).get();
					result = venuesJSON.toString();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             Log.v("Venues result", result);
	            
	            
				try {
					data1 = venuesJSON.getJSONObject("response");
					jr = data1.getJSONArray("venues");
					data2 = jr.getJSONObject(2);
					data3 = data2.getJSONObject("location");
	                Log.v("data3 = ", data3.toString());
	          	                
	             if(data3 != null) {
	                 Lngs = data3.getDouble("lng");
	                 Log.v("Lng = ", Double.toString(Lngs));
	                 Lats = data3.getDouble("lat");
	                 Log.v("Lat = ", Double.toString(Lats));
	             }
	            
				}
	             catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	 show_map(latitude,longitude);
	        	
	        }else{
	        	// can't get location
	        	// GPS or Network is not enabled
	        	// Ask user to enable GPS/network in settings
	        	gps.showSettingsAlert();
	        }
	      
	    }
	 private void show_map(double lat, double longt){
		 
			try {
				// Loading map
				initilizeMap();

				// Changing map type
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

				// Showing / hiding your current location
				googleMap.setMyLocationEnabled(true);

				// Enable / Disable zooming controls
				googleMap.getUiSettings().setZoomControlsEnabled(true);
				//googleMap.animateCamera( CameraUpdateFactory.zoomTo( 10.2f));  
				
				// Enable / Disable my location button
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);

				// Enable / Disable Compass icon
				googleMap.getUiSettings().setCompassEnabled(true);

				// Enable / Disable Rotate gesture
				googleMap.getUiSettings().setRotateGesturesEnabled(true);

				// Enable / Disable zooming functionality
				googleMap.getUiSettings().setZoomGesturesEnabled(true);


				// lets place some 10 random markers
				/*for (int i = 0; i < 10; i++) {
					// random latitude and logitude
					double[] randomLocation = createRandLocation(lat,
							longt);*/
				
					// Adding a marker
					MarkerOptions marker = new MarkerOptions().position(
							new LatLng(lat, longt))
							.title(get_info());
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
					//String URLresult = imageURL();
					//Log.v("URLresult = ", URLresult);
					MarkerOptions marker2 = new MarkerOptions().position(
							new LatLng(Lats, Lngs))
							.title(get_info());
					marker2.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					/*Log.e("Random", "> " + randomLocation[0] + ", " + randomLocation[1]);

					 changing marker color
					if (i == 0)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
					if (i == 1)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
					if (i == 2)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
					if (i == 3)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
					if (i == 4)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
					if (i == 5)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
					if (i == 6)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					if (i == 7)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
					if (i == 8)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
					if (i == 9)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
					*/
					googleMap.addMarker(marker);
					googleMap.addMarker(marker2);

					// Move the camera to last position with a zoom level
					/*if (i == 9) {
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(new LatLng(randomLocation[0],
										randomLocation[1])).zoom(15).build();

						googleMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
					}*/
				

			} catch (Exception e) {
				Log.v("show_map", "ExecutionException");
				e.printStackTrace();
			}
 
	 }

		@Override
		protected void onResume() {
			super.onResume();
			initilizeMap();
		}

		/**
		 * function to load map If map is not created it will create it for you
		 * */
		private void initilizeMap() {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.mapfragment)).getMap();

				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}

		/*
		 * creating random postion around a location for testing purpose only
		 */
		private double[] createRandLocation(double latitude, double longitude) {

			return new double[] { latitude + ((Math.random() - 0.5) / 500),
					longitude + ((Math.random() - 0.5) / 500),
					150 + ((Math.random() - 0.5) * 10) };
		}
		
		/*private String imageURL() throws JSONException
		{
			JSONArray imageJSON = data2.getJSONArray("categories");
            Log.v("imageJSON = ", imageJSON.toString());
            JSONObject iconJSON = imageJSON.getJSONObject(1);
            Log.v("iconJSON = ", iconJSON.toString());
            JSONObject imageUrlObj = iconJSON.getJSONObject("icon");
            String URL = imageUrlObj.getString("prefix");
            Log.v("imageURL = ", URL);
            return URL;
		}*/
		private String get_info() throws JSONException
		{
			JSONObject infoJSON = jr.getJSONObject(5);
			Log.v("infoJSON = ", infoJSON.toString());
            String name = infoJSON.getString("name");
            Log.v("infoJSON Name = ", name);
            return name;
		}
}
