package DataGeneration;

/**
 * Created by GrantRowberry on 3/6/17.
 */

public class LocationData {
    Location[] data;
    public void print(){
        for (Location l: data) {
            System.out.println(l.getLatitude());
            System.out.println(l.getLongitude());
            System.out.println(l.getCity());
            System.out.println(l.getCountry());
        }
    }
}
