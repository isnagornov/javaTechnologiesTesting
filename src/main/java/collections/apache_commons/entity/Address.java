package collections.apache_commons.entity;

public class Address {
    private String locality;
    private String city;

    public Address(String locality, String city) {
        this.locality = locality;
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
