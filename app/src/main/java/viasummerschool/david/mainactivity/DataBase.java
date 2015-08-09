package viasummerschool.david.mainactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBase extends SQLiteOpenHelper {

    Places places;
    int id;
    Latitude latitude;
    Longitud longitud;
    private static final String LOCATIONS="locations";
    private String tableDefault = "CREATE TABLE "+LOCATIONS+ " (" +
            id+ "INTEGER PRIMARY KEY AUTOINCREMENT, " +
            places+ "STRING, " +  latitude+" DOUBLE, " +longitud+" DOUBLE" +
            ")";


    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(tableDefault);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table TEST");
        db.execSQL(tableDefault);
    }
}