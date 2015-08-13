package viasummerschool.david.mainactivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 8/8/15.
 */
public class Settings extends ListActivity {

    private DataBaseManager dbm;
    private List<String> places = new ArrayList<String>();
    ArrayList<Places> placesInDb;
    ArrayAdapter<Places> pAdapter;
    ArrayList<Latitude> latitudesInDb;
    ArrayAdapter<Latitude> latitudeAdapter;
    ArrayList<Longitud> longitudInDb;
    ArrayAdapter<Longitud> longitudAdapter;
    ListView lv;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        lv = getListView();
        dbm = new DataBaseManager(this);
        dbm.open();
            placesInDb=dbm.getPlaces();
            latitudesInDb=dbm.getLatitude();
            longitudInDb=dbm.getLongitud();
        dbm.close();
        pAdapter = new ArrayAdapter<Places>(this, android.R.layout.simple_list_item_1, android.R.id.text1, placesInDb);
        lv.setAdapter(pAdapter);

    }
    //sends the user to theMapsView Location
    public void addLocation(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
    //sends the user to the start_screen_activity
    public void goToStartActivity(View view){
        Intent i = new Intent(this, Screen_Start.class);
        startActivity(i);
    }


    //allows the user to delete from the ListView while click on an item
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        dbm.open();
            dbm.delete(placesInDb.get(position).getId());
            pAdapter.remove(placesInDb.get(position));
            lv.setAdapter(pAdapter);
        dbm.close();


        Toast.makeText(this, "deleted", Toast.LENGTH_LONG).show();


    }
}
