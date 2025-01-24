package POJO;

import net.datafaker.Faker;

public class Passenger {
    private static final Faker faker = new Faker();
    private String name;
    private int trips;
    private String airline;

    // Constructor with DataFaker
    public Passenger() {
        this.name = faker.name().fullName();
        this.trips = faker.random().nextInt(1, 250);
        this.airline = faker.internet().uuid();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", trips=" + trips +
                ", airline=" + airline +
                '}';
    }
}

