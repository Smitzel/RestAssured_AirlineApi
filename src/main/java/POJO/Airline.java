package POJO;

import net.datafaker.Faker;

public class Airline {
    private static final Faker faker = new Faker();

    private String name;
    private String country;
    private String slogan;
    private String head_quarters;
    private String website;
    private String established;

    // Constructor met DataFaker
    public Airline() {
        this.name = faker.company().name();
        this.country = faker.country().name();
        this.slogan = faker.lorem().sentence();
        this.head_quarters = faker.address().cityName() + ", " + faker.address().country();
        this.website = faker.internet().url();
        this.established = String.valueOf(faker.number().numberBetween(1900, 2024));
    }

    // Getters en Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getSlogan() {
        return slogan;
    }
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getHead_quarters() {
        return head_quarters;
    }
    public void setHead_quarters(String head_quarters) {
        this.head_quarters = head_quarters;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEstablished() {
        return established;
    }
    public void setEstablished(String established) {
        this.established = established;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", slogan='" + slogan + '\'' +
                ", head_quarters='" + head_quarters + '\'' +
                ", website='" + website + '\'' +
                ", established='" + established + '\'' +
                '}';
    }
}

