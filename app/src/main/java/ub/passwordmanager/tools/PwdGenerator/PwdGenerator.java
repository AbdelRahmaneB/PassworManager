package ub.passwordmanager.tools.PwdGenerator;

import java.util.Random;

/**
 *
 * Created by UcefBen on 08/09/2016.
 */
public abstract class PwdGenerator {

    // ToDo : Complete this function.
    public static String generatePassword() {
        String loweCase = "abcdefghijklmnopqrstuvwxyz";
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "1234567890";
        String specialChars = "!@#$%^&*()_/-";

        String SALT_CHARS = loweCase + upperCase + numbers + specialChars;

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 12) {
            int index = (int) (rnd.nextFloat() * SALT_CHARS.length());
            salt.append(SALT_CHARS.charAt(index));
        }

        return salt.toString();

        //candidateChars.charAt(random.nextInt(candidateChars.length())); try thisone

    }
}
