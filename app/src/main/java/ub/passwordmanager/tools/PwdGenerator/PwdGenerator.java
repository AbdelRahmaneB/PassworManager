package ub.passwordmanager.tools.PwdGenerator;

import android.app.Activity;

import java.util.Random;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;

/**
 * This class is responsible for generating a random password
 * depending on the user choice in the Password generator view.
 * <p/>
 * Created by UcefBen on 08/09/2016.
 */
public class PwdGenerator {

    /**
     * Our Instance for the factory using Singleton
     */
    private static PwdGenerator INSTANCE;

    private PwdGenerator() {
        // Required empty public constructor
    }

    /**
     * This method allow us to be sure that there will be only one instance of this class
     *
     * @return the instance of this class
     */
    public static PwdGenerator getInstance() {
        if (INSTANCE == null) {
            synchronized (PwdGenerator.class) {
                INSTANCE = new PwdGenerator();
            }
        }
        return INSTANCE;
    }

    /**
     * the role of this function is to generate a random password
     *
     * @param activity : the current activity where the function is used
     * @return the generated password
     */
    public String generatePassword(Activity activity) {
        StringBuilder SALT_CHARS = new StringBuilder("");

        // Put together the chain that we gonna use to generate the password
        initTheSaltChars(activity, SALT_CHARS);

        if (SALT_CHARS.length() == 0) {
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
    private void initTheSaltChars(Activity activity, StringBuilder SALT_CHARS) {
        if (useLowerCase(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_lower_case_value));

        if (useUpperCase(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_upper_case_value));

        if (useSymbols(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_symbols_value));

        if (useNumbers(activity))
            SALT_CHARS.append(activity.getResources().getString(R.string.opt_numbers_value));
    }

    /**
     * Set the Salt Chars in case the user unChecked all the options
     */
    private void setSaltCharsToDefault(Activity activity, StringBuilder SALT_CHARS) {
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_lower_case_value));
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_upper_case_value));
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_numbers_value));
        SALT_CHARS.append(activity.getResources().getString(R.string.opt_symbols_value));
    }


    /**
     * Function that return the value for LowerCase
     */
    public Boolean useLowerCase(Activity activity) {
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_LOWER_CASE);
    }

    /**
     * Function that return the value for UpperCase
     */
    public Boolean useUpperCase(Activity activity) {
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_UPPER_CASE);
    }


    /**
     * Function that return the value for Symbols
     */
    public Boolean useSymbols(Activity activity) {
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_USE_SYMBOLS);
    }

    /**
     * Function that return the value for Numbers
     */
    public Boolean useNumbers(Activity activity) {
        return (Boolean) AppConfig.getInstance().getSavedValueFromPreferences(
                activity,
                AppConfig.KEY_PREF_BOOLEAN,
                AppConfig.KEY_PREF_USE_NUMBERS);
    }

}
