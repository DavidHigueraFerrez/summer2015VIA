package viasummerschool.david.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by David on 8/8/15.
 */
public class Settings extends Activity {

    private DataBaseManager dbm;
    ArrayList<Places> placesInDb;
    ArrayAdapter<Places> pAdapter;
    ArrayList<Latitude> latitudesInDb;
    ArrayAdapter<Latitude> latitudeAdapter;
    ArrayList<Longitud> longitudInDb;
    ArrayAdapter<Longitud> longitudAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    }

    public void addLocation(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
    protected void onListItemClick(ListView list, View view, int position, long id){
        dbm.open();
        dbm.delete(placesInDb.get(position).getId());
        dbm.delete(latitudesInDb.get(position).getId());
        dbm.delete(longitudInDb.get(position).getId());
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
    }
}
