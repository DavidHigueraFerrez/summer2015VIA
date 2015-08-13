package viasummerschool.david.mainactivity;


public class Latitude  {
    private int id;
    private double latitude;

    public Latitude (int id, double latitude){
        this.id=id;
        this.latitude=latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;

    }

    public void latitude(int id, double latitude) {

        this.id = id;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "ID : "+this.id+" latitude : "+this.latitude;
    }


}
