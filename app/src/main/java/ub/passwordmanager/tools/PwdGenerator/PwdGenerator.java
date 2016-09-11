package ub.passwordmanager.tools.PwdGenerator;

import android.app.Activity;

import java.util.Random;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;

/**
 * This class is responsible for generating a random password
 * depending on the user choice in the Password generator view.
 *
 * Created by UcefBen on 08/09/2016.
 */
public abstract class PwdGenerator {

    /**
     * the role of this function is to generate a random password
     * @param activity : the current activity where the function is used
     * @return the generated password
     */
    public static String generatePassword(Activity activity) {
        StringBuilder SALT_CHARS = new StringBuilder("");

        // Put together the chain that we gonna use to generate the password
        initTheSaltChars(activity, SALT_CHARS);

        if (SALT_CHARS.length() == 0){
            setSaltCharsToDefault(activity, SALT_CHARS);
        }

        // Generate the Password based on the options
        StringBuilder salt = new StringBuilder();

        Random rnd = new Random();
        while (salt.length() < 12) {
            int index = (int) (rnd.nextFloat() * SALT_CHARS.toString().length());
            salt.append(SALT_CHARS.charAt(index));
        }

        // Return the Generated Password
        return salt.toString();
    }

    /**
     * Initialise the Salt Chars depending on the user choice
     */
    private static void initTheSaltChars(Activity activity, StringBuilder SALT_CHARS){
        if(useLowerCase(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_lower_case_value));

        if(useUpperCase(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_upper_case_value));

        if(useSymbols(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_symbols_value));

        if(useNumbers(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_numbers_value));
    }

    /**
     * Set the Salt Chars in case the user unChecked all the options
     */
    private static void setSaltCharsToDefault(Activity activity, StringBuilder SALT_CHARS){
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_lower_case_value));
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_upper_case_value));
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_numbers_value));
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_symbols_value));
    }


    /**
     * Function that return the value for LowerCase
     */
    public static Boolean useLowerCase(Activity activity){
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_LOWER_CASE);
    }

    /**
     * Function that return the value for UpperCase
     */
    public static Boolean useUpperCase(Activity activity){
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_UPPER_CASE);
    }


    /**
     * Function that return the value for Symbols
     */
    public static Boolean useSymbols(Activity activity){
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_USE_SYMBOLS);
    }

    /**
     * Function that return the value for Numbers
     */
    public static Boolean useNumbers(Activity activity){
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_USE_NUMBERS);
    }

}
