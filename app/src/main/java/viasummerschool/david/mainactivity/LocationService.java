package viasummerschool.david.mainactivity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Usuario on 10/08/2015.
 */
public class LocationService extends IntentService {

    //We create the local variables for the class
    private DataBaseManager dbm;
    private Location currentLocation;
    private LocationManager lm;
    ArrayList<Latitude> latitudesInDb;
    ArrayAdapter<Latitude> latitudeAdapter;
    ArrayList<Longitud> longitudInDb;
    ArrayAdapter<Longitud> longitudAdapter;

    private WifiManager wifiManager;

    public LocationService(){
        super("LocationService");
    }
    //method OnHandleIntent that manage the intent
    protected  void onHandleIntent(Intent i){
        dbm = new DataBaseManager(this);
        dbm.open();
            latitudesInDb=dbm.getLatitude();
            longitudInDb=dbm.getLongitud();
        dbm.close();
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lm.removeUpdates(locationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30, 100, locationListener);

    }

    //LocationListener with what we check the location
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            currentLocation = location;
            changeProvider(currentLocation);
            check(currentLocation);
            wifi();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onProviderDisabled(String provider) {

        }
    };

    //method that change the provider thinking on the ranges of the diferents networks trying to save batery. Range of network 1,5km
    //range of gps 100m and we check if we are inside the zone of the wifi every 5 min turning on the gps and later turn off and turn again
    //the network provider
    public void changeProvider(Location location) {

        Location marker = new Location("");
        int t =0;

        for (int i = 0; i < latitudesInDb.size(); i++) {
            for (int j = 0; j < longitudInDb.size(); j++) {
                marker.setLatitude(latitudesInDb.get(i).getLatitude());
                marker.setLongitude(longitudInDb.get(j).getLongitud());

                if (location.distanceTo(marker) < 1500 && location.distanceTo(marker)>100 ) {
                    lm.removeUpdates(locationListener);
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 100, locationListener);
                    Toast.makeText(getBaseContext(), "Change to GPS", Toast.LENGTH_LONG).show();
                }
                if(location.distanceTo(marker)<100 ){
                    t++;
                    lm.removeUpdates(locationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30, 100, locationListener);
                    Toast.makeText(getBaseContext(), "Change to Network", Toast.LENGTH_LONG).show();
                }

                if(t==10){
                    t=0;
                    lm.removeUpdates(locationListener);
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 100, locationListener);
                    Toast.makeText(getBaseContext(), "Change to GPS", Toast.LENGTH_LONG).show();

                    if(location.distanceTo(marker)<100 ){
                        t++;
                        lm.removeUpdates(locationListener);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30, 100, locationListener);
                        Toast.makeText(getBaseContext(), "Change to Network", Toast.LENGTH_LONG).show();
                    }
                }

            }


            lm.removeUpdates(locationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30, 100, locationListener);
            Toast.makeText(getBaseContext(), "Change to Network", Toast.LENGTH_LONG).show();
        }
    }

    //checks if the location is inside the range zone of the wifi, if it's correct it return true
    public boolean check(Location location) {

        Location marker = new Location("provider");

        for (int i = 0; i < latitudesInDb.size(); i++) {
            for (int j = 0; j < longitudInDb.size(); j++) {
                marker.setLatitude(latitudesInDb.get(i).getLatitude());
                marker.setLongitude(longitudInDb.get(j).getLongitud());

                if (location.distanceTo(marker) < 50) {
                    Toast.makeText(getBaseContext(), "Inside zone", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }
        Toast.makeText(getBaseContext(), "Outside zone", Toast.LENGTH_LONG).show();
        return false;

    }
    //it's a method that call check, and the turn on the wifi if the answer it's true
    public void wifi(){

        if(check(currentLocation)){
            wifiManager.setWifiEnabled(true);
            Log.i("TESTING1", "wifi on" + "");
            Toast.makeText(getBaseContext(), "Wi-Fi Enabled!", Toast.LENGTH_LONG).show();
        }
        else{
            wifiManager.setWifiEnabled(false);
            Log.i("TESTING2", "wifi off" + "");
            Toast.makeText(getBaseContext(), "Wi-Fi Disabled!", Toast.LENGTH_LONG).show();

        }
    }
}
