package collections.apache_commons.entity;

public class Customer implements Comparable<Customer> {
    private Integer id;
    private String name;
    private Address address;

    public Customer(Integer id, String name, String locality, String city) {
        this.id = id;
        this.name = name;
        this.address = new Address(locality, city);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int compareTo(Customer customer) {
        return this.getName().compareTo(customer.getName());
    }
}
