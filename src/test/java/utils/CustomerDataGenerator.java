package utils;

import java.util.Random;
import java.util.stream.Collectors;

public class CustomerDataGenerator {
    public static String generateRandomPostCode() {
        return new Random().ints(10, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

    public static String generateFirstNameFromPostCode(String postCode) {
        StringBuilder firstName = new StringBuilder();
        for (int i = 0; i < postCode.length(); i += 2) {
            int num = Integer.parseInt(postCode.substring(i, Math.min(i + 2, postCode.length())));
            char c = (char) ('a' + (num % 26));
            firstName.append(c);
        }
        return firstName.toString();
    }
}