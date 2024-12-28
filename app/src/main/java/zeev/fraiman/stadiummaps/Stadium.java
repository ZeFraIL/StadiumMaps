package zeev.fraiman.stadiummaps;

import java.io.Serializable;

public class Stadium implements Serializable {
    private String name;
    private String city;
    private String lat;
    private String lng;
    private String image;

    public Stadium(String name, String city, String lat, String lng, String image) {
        this.name = name;
        this.city = city;
        this.lat = lat;
        this.lng = lng;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Stadium{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
