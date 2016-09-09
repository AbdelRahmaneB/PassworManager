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
        String SALTCHARS = loweCase + upperCase + numbers;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 12) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

        //candidateChars.charAt(random.nextInt(candidateChars.length())); try thisone

    }
}
