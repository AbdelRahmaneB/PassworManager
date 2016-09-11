package ub.passwordmanager.appConfig;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ToDo : Description :
 * <p/>
 * Created by UB on 29/08/2016.
 */
public class AppConfig {

    //Our Instance for the AppConfig using Singleton
    private static AppConfig Instance;

    // The name of the preferences file
    private final String PREFS_NAME = "MyAppPreferences";

    // The current Password that the uses to connect
    private String mCurrentPassword;
    private String mCurrentUser;

    // Our Regex Pattern for the validity of the Email Address
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final String KEY_PREF_USERNAME = "Username";
    public static final String KEY_PREF_STRING = "String";
    public static final String KEY_PREF_BOOLEAN = "Boolean";
    public static final String KEY_PREF_INT = "int";


    private AppConfig() {
        // Require an empty constructor
    }

    /**
     * This method allow us to be sure that there will be only one instance of this class
     *
     * @return the instance of this class
     */
    public static AppConfig getInstance() {
        if (Instance == null) {
            synchronized (AppConfig.class) {
                Instance = new AppConfig();
            }
        }
        return Instance;
    }

    /**
     * Method to test the validity of our email
     *
     * @param email : the desired email to test.
     * @return @True or @False : as a result of the test
     */
    public boolean isEmailValid(String email) {
        Matcher matcher = AppConfig.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    //******************** Preferences File *****************************//
    //-------------------------------------------------------------------//

    /**
     * This function handles the saving of 'String, Boolean and int' values into the Preferences file.
     * this function handles 3 types for the moment :
     * - String.
     * - Boolean.
     * - int.
     *
     * @param activity       : Current activity.
     * @param preferencesKey : The key of the object that we want to save.
     * @param valueType      : the type of the value.
     * @param value          : the value to save
     */
    public void saveValueToPreference(Activity activity, String valueType, String preferencesKey, Object value) {
        SharedPreferences prefSettings = activity.getSharedPreferences(this.PREFS_NAME, 0);
        SharedPreferences.Editor prefEditor = prefSettings.edit();
        switch (valueType) {
            case "String":
                prefEditor.putString(preferencesKey, (String) value);
                break;

            case "Boolean":
                prefEditor.putBoolean(preferencesKey, (Boolean) value);
                break;

            case "int":
                prefEditor.putInt(preferencesKey, (int) value);
                break;

            default:
                Log.e("Saving to preferences", "No match for value type");
                break;
        }
        prefEditor.apply();
    }


    /**
     * This function allow us to access and get 'String, Boolean and int' data from the preferences file.
     * This function handles 3 types for the moment :
     * - String.
     * - Boolean.
     * - int.
     *
     * @param activity       : the current activity.
     * @param preferencesKey : the object that we want to access from the preferences file.
     * @param valueType      : The type of the value
     * @return the value of the object depending on the given key.
     */
    public Object getSavedValueFromPreferences(Activity activity, String valueType, String preferencesKey) {
        SharedPreferences PrefSettings = activity.getSharedPreferences(this.PREFS_NAME, 0);
        switch (valueType) {
            case "String":
                return PrefSettings.getString(preferencesKey, null);

            case "Boolean":
                return PrefSettings.getBoolean(preferencesKey, false);

            case "int":
                return PrefSettings.getInt(preferencesKey, -1);

            default:
                Log.e("Saving to preferences", "No match for value type");
                return null;
        }
    }


    //******************** Getters and Setters **************************//
    //------------------------------------------------------------------//
    public String getCurrentPassword() {
        return mCurrentPassword;
    }

    public void setCurrentPassword(String mCurrentPassword) {
        this.mCurrentPassword = mCurrentPassword;
    }

    public String getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(String mCurrentUser) {
        this.mCurrentUser = mCurrentUser;
    }


}
