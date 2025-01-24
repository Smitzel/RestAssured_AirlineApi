package utils;

import net.datafaker.Faker;

import java.util.UUID;
import java.util.Random;


public class TestData {
    public static final Faker faker = new Faker();

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getCountry() {
        return faker.country().name();
    }

    public static String getLogo() {
        int number = faker.random().nextInt(13) + 1;
        return "https://pigment.github.io/fake-logos/logos/medium/color/" + number + ".png";
    }

    public static String getSlogan() {
        return faker.company().catchPhrase();
    }

    public static String getCity() {
        return faker.address().cityName();
    }

    public static String getWebsite() {
        return faker.company().url();
    }

    public static int getYear() {
        Random random = new Random();
        int startYear = 1900;  // Starting year (inclusive)
        int endYear = 2024;    // Ending year (inclusive)
        // Generate a random year between startYear and endYear
        return startYear + random.nextInt(endYear - startYear + 1);
    }

    public static void main(String[] args) {
        System.out.println("UUID: " + getUUID());
        System.out.println("Country: " + getCountry());
        System.out.println("Logo: " + getLogo());
        System.out.println("Slogan: " + getSlogan());
        System.out.println("City: " + getCity());
        System.out.println("Website: " + getWebsite());
        System.out.println("Year: " + getYear());
    }
}
