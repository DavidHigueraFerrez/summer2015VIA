package viasummerschool.david.mainactivity;

import android.app.Activity;

/**
 * Created by David on 9/8/15.
 */
public class Places  {

    private int id;
    private String place;

    public Places(int id, String place) {
        this.id=id;
        this.place=place;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
       this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;

    }

    public void places(String place) {

        this.place = place;
    }

    @Override
    public String toString() {
        return     "ID : "+this.id+ " Place : "+this.place;
    }

}
