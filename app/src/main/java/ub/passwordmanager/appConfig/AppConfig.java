package ub.passwordmanager.appConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** ToDo : Description :
 *
 * Created by UB on 29/08/2016.
 */
public class AppConfig {

    /**
     * Our Instance for the AppConfig using Singleton
     */
    private static AppConfig Instance;

    // The current Password that the uses to connect
    private String mCurrentPassword;
    private String mCurrentUser;

    // Our Regex Pattern for the validity of the Email Address
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);



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
