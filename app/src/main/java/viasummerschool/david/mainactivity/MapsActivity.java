package viasummerschool.david.mainactivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    //declare of variables

    private GoogleMap mMap;
    private DataBaseManager dbm;
    private MarkerOptions marker;
    ArrayList<Places> placesInDb;
    ArrayAdapter<Places> pAdapter;
    ArrayList<Latitude> latitudesInDb;
    ArrayAdapter<Latitude> latitudeAdapter;
    ArrayList<Longitud> longitudInDb;
    ArrayAdapter<Longitud> longitudAdapter;

    //onCreate method where we open de db,and initialize the maps
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        dbm = new DataBaseManager(this);
        dbm.open();
            placesInDb=dbm.getPlaces();
            latitudesInDb=dbm.getLatitude();
            longitudInDb=dbm.getLongitud();
        dbm.close();
        setUpMapIfNeeded();
    }


    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    //method for making available the search on google maps
    public void onSearch(View view){
        List<Address> addressList = null;
        EditText location_tf = (EditText) findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();
        if(location!=null || location.equals("")){
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(marker.position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }

    }

    //method for the buttons of zoomIn and zoomOut
    public void onZoom(View view){
        if(view.getId() == R.id.Bzoomin){
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if(view.getId()==R.id.Bzoomout){
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }
    public void changeType(View view){
        if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

    }

    //method for setting up the maps
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    //method for allowing the system to get my location
    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
    }


    //method fot the add button that saves the marker in the dataBase
    private void add(View view, String name){
        dbm.open();
            double Latitude1 =marker.getPosition().latitude;
            double Longitud1 = marker.getPosition().longitude;
                dbm.insertValue(name, Latitude1, Longitud1);
                placesInDb.clear();
                latitudesInDb.clear();
                longitudInDb.clear();
                placesInDb.addAll(dbm.getPlaces());
                latitudesInDb.addAll(dbm.getLatitude());
                longitudInDb.addAll(dbm.getLongitud());
                pAdapter.notifyDataSetChanged();
                latitudeAdapter.notifyDataSetChanged();
                longitudAdapter.notifyDataSetChanged();
        dbm.close();
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
}
