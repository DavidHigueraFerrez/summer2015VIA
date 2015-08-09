package viasummerschool.david.mainactivity;

/**
 * Created by David on 9/8/15.
 */
public class Longitud {

    private int id;
    private double longitud;

    public Longitud(int id, double longitud){
        this.id=id;
        this.longitud=longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLatitude(double longitud) {
        this.longitud = longitud;

    }

    public void longitud(int id, double longitud) {

        this.id = id;
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "ID : "+this.id+" longitud : "+this.longitud;
    }


}
