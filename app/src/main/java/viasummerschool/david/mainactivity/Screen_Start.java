package viasummerschool.david.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Screen_Start extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen__start);
    }

    public void goToMaps(View view){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
    public void goToSettings(View view){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    public void startLocationService(View view){
        Intent i = new Intent(Screen_Start.this, LocationService.class);
        Toast.makeText(getApplicationContext(), "ARRANCO", Toast.LENGTH_LONG).show();
        startService(i);
    }
    public void stopLocationService(View view){
        Intent i = new Intent(Screen_Start.this, LocationService.class);
        Toast.makeText(getApplicationContext(), "paramos", Toast.LENGTH_LONG).show();
        stopService(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screen__start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
