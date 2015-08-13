package viasummerschool.david.mainactivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private LocationService mService;
    private boolean mBound;
    private Button add;
    private GoogleMap mMap;
    private DataBaseManager dbm;
    private Marker marker;
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
        placesInDb = new ArrayList<>();
        setUpMapIfNeeded();
    }
    /*public void start(View v) {
        if(mBound) {
                mService.upDateLocation();
                Toast.makeText(getApplicationContext(), "ARRANCO", Toast.LENGTH_LONG).show();
                }
        else {
                    Toast.makeText(getApplicationContext(), "Not bound", Toast.LENGTH_LONG).show();
                }

        }
        */

   /* protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        Intent i = new Intent(this, LocationService.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
    }
    */

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
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

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
    public void add(View view){
        displayAlertDialog();

    }
    public void addToDataBase(String location){
        dbm.open();
            double Latitude1 =marker.getPosition().latitude;
            double Longitud1 = marker.getPosition().longitude;
                dbm.insertValue(location, Latitude1, Longitud1);
        dbm.close();

    }


    public void displayAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_layout, null);
        final EditText locations = (EditText) alertLayout.findViewById(R.id.editText);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Name your location");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setPositiveButton("Set name", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String location = locations.getText().toString();
                addToDataBase(location);
                Toast.makeText(getBaseContext(), location + " added to the dataBase", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MapsActivity.this, Settings.class);
                startActivity(i);

            }
        });
        if(marker!=null) {
           // start(v);
            AlertDialog dialog = alert.create();
            dialog.show();
        }else{
            Toast.makeText(getApplicationContext(), "not marker selected", Toast.LENGTH_LONG).show();
        }
    }
    /*private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            LocationService.MyBinder mb = (LocationService.MyBinder)service;
            mService = mb.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };
    */

}
