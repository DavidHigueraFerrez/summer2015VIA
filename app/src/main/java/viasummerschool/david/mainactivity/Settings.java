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
       // Log.i("TESTING", placesInDb.size() + "");
        dbm.close();
       // Log.i("TESTING2", placesInDb.size() + "");
        pAdapter = new ArrayAdapter<Places>(this, android.R.layout.simple_list_item_1, android.R.id.text1, placesInDb);
        lv.setAdapter(pAdapter);

    }

    public void addLocation(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
    public void goToStartActivity(View view){
        Intent i = new Intent(this, Screen_Start.class);
        startActivity(i);
    }



    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        dbm.open();
            dbm.delete(placesInDb.get(position).getId());
            pAdapter.remove(placesInDb.get(position));
            lv.setAdapter(pAdapter);
        dbm.close();


        Toast.makeText(this, "deleted", Toast.LENGTH_LONG).show();


    }

  //  public void onClick(DialogInterface dialog, int item) {
  //       lv.setOnItemClickListener(new OnItemClickListener() {

    //    public void onItemClick(AdapterView<?> parent, View view,int position, long id){
      //  dbm.open();
       //     dbm.delete(placesInDb.get(position).getId());
       //     placesInDb.clear();
        //dbm.close();
       // }
    //});}
}
