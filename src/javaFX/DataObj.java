package javaFX;

public class DataObj {
    private double lat;
    private double lon;
    private int cost;
    private String cuisine;
    private String rate;
    private String url;
    private String name;
    private String country;
    private String city;

    public DataObj(double lat, double lon, int cost, String cuisine, String rate, String url, String name, String country, String city) {
        this.city = city;
        this.cost = cost;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.cuisine = cuisine;
        this.rate = rate;
        this.name = name;
        this.url = url;
    }
    public double getLat(){
        return this.lat;
    }
    public double getLon(){
        return this.lon;
    }
    public int getCost(){
        return this.cost;
    }
    public String getCity(){
        return this.city;
    }
    public String getRate(){
        return this.rate;
    }
    public String getCountry(){
        return this.country;
    }
    public String getCouisine(){
        return this.cuisine;
    }
    public String getName(){
        return this.name;
    }
    public String getUrl(){
        return this.url;
    }
}
