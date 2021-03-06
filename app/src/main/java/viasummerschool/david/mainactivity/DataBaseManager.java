package viasummerschool.david.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;


public class DataBaseManager {

    private DataBase dataBase;
    private SQLiteDatabase bdd;
    private GoogleMap mMap;
    String  tableName = "locations";
    String rowPlaces = "Places";
    String rowLatitude = "Latitude";
    String rowLongitud = "Longitud";


    public DataBaseManager(Context context){

        dataBase = new DataBase(context, tableName, null, 1);
    }

    //open the dataBase
    public void open(){
        bdd = dataBase.getWritableDatabase();
    }

    //closes the dataBase
    public void close(){

        bdd.close();
    }
    //delete the dataBase
    public void delete(int id){

        bdd.delete(tableName, "id" + "=" + id, null);
    }
    //insertValues on the dataBase
    public void insertValue(String name, double latitude, double longitud){
        ContentValues values = new ContentValues();
        values.put(rowPlaces, name);
        values.put(rowLatitude, latitude);
        values.put(rowLongitud, longitud);
        bdd.insert(tableName, null, values);

    }
    //pointer that search for the placesList on the dataBase
    ArrayList getPlaces(){
        ArrayList<Places> entry = new ArrayList<>();
        Cursor c = bdd.query(tableName, new String[]{"id","places","latitude", "longitud"},null , null, null, null, null);
        c.moveToFirst();
        Log.i("TEST", c.getCount() + "");
        while(c.moveToNext()){
            //error while making getInt BE CAREFULL
            entry.add(new Places(c.getInt(0),c.getString(1)));
        }
        return entry;
    }

    //Pointer that search the Latitude of the dataBase
    ArrayList getLatitude(){
        ArrayList<Latitude> entry = new ArrayList<>();
        int i =0;
        Cursor c = bdd.query(tableName, new String[]{"id","places","latitude", "longitud"},null , null, null, null, null);
        c.moveToFirst();
        while(c.moveToNext()){
            //error while making getInt BE CAREFULL
            entry.add(new Latitude(c.getInt(0), c.getDouble(1)));
        }
        return entry;
    }
    //pointer that searchs the longitud of the dataBase
    ArrayList getLongitud(){
        ArrayList<Longitud> entry = new ArrayList<>();
        Cursor c = bdd.query(tableName, new String[]{"id","places","latitude", "longitud"},null , null, null, null, null);
        c.moveToFirst();
        while(c.moveToNext()){
            //error while making getInt BE CAREFULL
            entry.add(new Longitud(c.getInt(0), c.getDouble(1)));
        }
        return entry;
    }


}
