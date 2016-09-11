package ub.passwordmanager.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_UserAccount;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.factories.FragmentFactory;
import ub.passwordmanager.views.fragments.OnDataPass;
import ub.passwordmanager.views.fragments.Registration.SignInPwdInfoFragment;
import ub.passwordmanager.views.fragments.Registration.SignInUserInfoFragment;

public class SignIn extends AppCompatActivity implements OnDataPass {

    // The log Key
    private final String LOG_KEY = "SignIn - ";

    // This field will contain the active fragment
    private Fragment activeFragment = null;

    // This fields will contain the Data from both views
    private UserAccountModel userAccount;

    // The view fields
    private Button bt_next = null;
    private Button bt_previous = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        bt_next = (Button) findViewById(R.id.bt_next);
        bt_previous = (Button) findViewById(R.id.bt_previous);


        // Affect the active fragment to the Fragment Container
        if (savedInstanceState == null) {
            // This field allow us to keep in memory the active fragment
            activeFragment = FragmentFactory.getInstance().getSignInUserInfoFragment();

            // Allow to place the current fragment int the fragment container
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.signIn_fragment_container, activeFragment)
                    .commit();
        } else {
            activeFragment = getSupportFragmentManager().getFragment(savedInstanceState, "activeFragment");
            if (activeFragment.getClass() == FragmentFactory.getInstance().getSignInPwdInfoFragment().getClass()) {
                actionsOfTheButtons(R.id.bt_next);
            }
        }


        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionsOfTheButtons(R.id.bt_next);
            }
        });

        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionsOfTheButtons(R.id.bt_previous);
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the Activity
        getSupportFragmentManager().putFragment(savedInstanceState, "activeFragment", activeFragment);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (activeFragment == FragmentFactory.getInstance().getSignInUserInfoFragment()) {
            super.onBackPressed();
        } else
            actionsOfTheButtons(R.id.bt_previous);
    }

    /**
     * This method define the actions needed to be done when a button is clicked
     *
     * @param IdButton : Accept the button Id
     */
    private void actionsOfTheButtons(int IdButton) {

        switch (IdButton) {
            case R.id.bt_next:
                // Go to the next fragment - "Password information fragment"
                ActionForButtonNext();
                break;

            case R.id.bt_previous:
                // Go back to the previous fragment - "User information fragment"
                actionForButtonPrevious();
                break;

            default:
                break;
        }
    }


    // TODO : find a solution to not duplication this part of the code

    /**
     * Description
     * This function allow us to switch between the fragments
     *
     * @param toFragment  : the desired fragment
     * @param LeftToRight : Choose the direction of the animation
     */
    protected void switchFragment(Fragment toFragment, boolean LeftToRight) {
        if (activeFragment != toFragment) {
            // Insert the fragment by replacing any existing fragment
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations((LeftToRight) ? R.anim.trans_left_in : R.anim.trans_right_in,
                            (LeftToRight) ? R.anim.trans_left_out : R.anim.trans_right_out)
                    .hide(activeFragment)
                    .show(toFragment)
                    .replace(R.id.signIn_fragment_container, toFragment)
                    .commit();
            activeFragment = toFragment;
        }
    }

    /**
     * Description :
     * Give the user the liberty to go to the next fragment.
     * it has two option either The "Next action" or The "Save action" :
     * <p/>
     * - For the Case - "Next" : the method get the information from
     * the fragment and send it to the next one.
     * <p/>
     * - For the case - "Save" : The method get the information from
     * both fragment and send it to the persistence class to save it in a Data Base
     * <p/>
     * Uses the @switchFragment() method.
     */
    private void ActionForButtonNext() {

        if (getResources().getString(R.string.bt_text_next).equals(bt_next.getText().toString())) {

            // Switch the active fragment to "Password information fragment"
            switchFragment(FragmentFactory.getInstance().getSignInPwdInfoFragment(), true);

            // Change The visibility, button text and the activity title.
            bt_previous.setVisibility(View.VISIBLE);
            bt_next.setVisibility(View.INVISIBLE);
            bt_next.setText(getResources().getString(R.string.bt_text_register));
            setTitle(R.string.sign_in_part2);

        } else {

            if (userAccount != null) {
                try {
                    String username = userAccount.getUsername();
                    // Save the values in the DataBase
                    if (Service_UserAccount.saveNewData(getBaseContext(), userAccount)) {

                        // Adding the username to the preferences file
                        AppConfig.getInstance().saveValueToPreference(
                                SignIn.this,
                                AppConfig.KEY_PREF_STRING,
                                AppConfig.KEY_PREF_USERNAME,
                                username
                        );

                        // Inform the user that everything is correct
                        Toast.makeText(getBaseContext(),
                                getResources().getString(R.string.registration_ok),
                                Toast.LENGTH_SHORT).show();

                        // Go to the next Activity
                        startActivity(new Intent(getApplicationContext(), LogIn.class));

                    } else {
                        // Warn the user that there was a problem
                        Toast.makeText(getBaseContext(),
                                getResources().getString(R.string.registration_error),
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Log.e(LOG_KEY + "Register", "[" + ex.getMessage() + "]");
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Description :
     * Give the user the liberty to go back to the previous fragment
     * Uses the @switchFragment() method.
     */
    private void actionForButtonPrevious() {
        // Switch back to "User information fragment"
        switchFragment(FragmentFactory.getInstance().getSignInUserInfoFragment(), false);

        // Change The visibility, button text and the activity title.
        bt_previous.setVisibility(View.INVISIBLE);
        bt_next.setText(getResources().getString(R.string.bt_text_next));
        setTitle(R.string.sign_in_part1);
    }


    /**
     * This function is used to link between the fragments and their activity
     *
     * @param data : The {@link UserAccountModel} as DataHolder
     */
    @Override
    public void onDataPass(Object data) {
        if (data instanceof UserAccountModel) {
            UserAccountModel mUserAccount = (UserAccountModel) data;

            // For the {@link SignInUserInfoFragment} Fragment.
            if (isEmailValid(mUserAccount.getEmail())
                    & isUsernameValid(mUserAccount.getUsername())) {
                bt_next.setVisibility(View.VISIBLE);
                this.userAccount = mUserAccount; // To Set the user Account
                AppConfig.getInstance().setCurrentUser(mUserAccount.getUsername());
            } else {
                bt_next.setVisibility(View.INVISIBLE);
            }

            // For the {@link SignInPwdInfoFragment} Fragment.
            if ("SignInPwdInfoFragment".equals(activeFragment.getClass().getSimpleName())) {

                String pwd = mUserAccount.getPassword();
                if (isPwdValid(pwd) &&
                        isCofPwdValid(pwd, mUserAccount.getConfirmationPwd())) {
                    bt_next.setVisibility(View.VISIBLE);
                    this.userAccount.setPassword(mUserAccount.getPassword()); // To Set the user Account
                    this.userAccount.setConfirmationPwd(mUserAccount.getConfirmationPwd());
                    AppConfig.getInstance().setCurrentPassword(mUserAccount.getPassword());
                } else {
                    bt_next.setVisibility(View.INVISIBLE);
                }

            }


        }

    }

    /**
     * This method is  used to set the message error
     * depending on the field
     *
     * @param IdMessage : the id of the fields according to its position in the view
     *                  *) - Number 0 : represent the Username field => Empty
     *                  *) - Number 1 : represent the Email field => Empty
     *                  *) - Number 2 : represent the Email field => Invalid
     *                  *) - Number 3 : represent the Password field => Empty
     *                  *) - Number 4 : represent the Confirmation field => Empty
     *                  *) - Number 5 : represent the Confirmation and Pwd field => Different
     *                  *) - default : Everything correct (-1, -2)
     */
    private void setErrorMessage(int IdMessage) {
        if ("SignInUserInfoFragment".equals(activeFragment.getClass().getSimpleName())) {
            SignInUserInfoFragment tempUserInfo = (SignInUserInfoFragment) activeFragment;
            // Messages for the SignInUserInfoFragment
            mUserInfoErrorMessage(IdMessage, tempUserInfo);

        } else {
            SignInPwdInfoFragment tempPwdInfo = (SignInPwdInfoFragment) activeFragment;
            // Messages for the SignInPwdInfoFragment
            mPwdInfoErrorMessage(IdMessage, tempPwdInfo);

        }
    }

    /**
     * Messages for the user information
     *
     * @param IdMessage    : The ID of the message to show.
     * @param tempUserInfo : the user information data holder
     */
    private void mUserInfoErrorMessage(int IdMessage, SignInUserInfoFragment tempUserInfo) {
        switch (IdMessage) {
            case 0:
                tempUserInfo.setUsernameErrorMessage(
                        getResources().getText(R.string.empty_username_error_signIn)
                                .toString()
                );
                break;

            case 1:
                tempUserInfo.setEmailErrorMessage(
                        getResources().getText(R.string.empty_email_error_signIn)
                                .toString()
                );
                break;

            case 2:
                tempUserInfo.setEmailErrorMessage(
                        getResources().getText(R.string.invalid_email_error_signIn)
                                .toString()
                );
                break;

            default:
                if (IdMessage == -1)
                    tempUserInfo.setUsernameErrorMessage(null);
                else
                    tempUserInfo.setEmailErrorMessage(null);
                break;
        }
    }

    /**
     * Messages for the Password information
     *
     * @param IdMessage   : The ID of the message to show.
     * @param tempPwdInfo : the user information data holder
     */
    private void mPwdInfoErrorMessage(int IdMessage, SignInPwdInfoFragment tempPwdInfo) {
        switch (IdMessage) {
            case 3:
                tempPwdInfo.setPwdErrorMessage(
                        getResources().getString(R.string.empty_password_error_signIn)
                );
                break;

            case 4:
                tempPwdInfo.setConfirmErrorMessage(
                        getResources().getString(R.string.empty_confirmation_error_signIn)
                );
                break;

            case 5:
                tempPwdInfo.setConfirmErrorMessage(
                        getResources().getString(R.string.diff_error_signIn)
                );
                break;

            default:
                if (IdMessage == -1)
                    tempPwdInfo.setPwdErrorMessage(null);
                else
                    tempPwdInfo.setConfirmErrorMessage(null);
                break;
        }
    }


    /**
     * This part is for the fields test
     * -------------------------------------------------------------
     */

    /**
     * checks if the Username : !isEmpty
     *
     * @return True or False
     */
    private boolean isUsernameValid(String username) {
        if (TextUtils.isEmpty(username)) {
            setErrorMessage(0); // Username Empty
            return false;

        }

        setErrorMessage(-1); // Username valid
        return true;
    }

    /**
     * Checks if the Email : !isEmpty, isValid
     * <p/>
     * Uses the {@link AppConfig#isEmailValid(String)}
     *
     * @return True or False.
     */
    private boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) {
            setErrorMessage(1); // Email Empty
            return false;

        } else {
            if (!AppConfig.getInstance().isEmailValid(email)) {
                setErrorMessage(2); // Email Invalid
                return false;
            }
        }

        setErrorMessage(-2); // Email Correct
        return true;
    }

    /**
     * Check if the Old Password : !isEmpty, isCorrect
     *
     * @return True or False.
     */
    private Boolean isPwdValid(String pwd) {
        // ToDo : test the length of the password
        if (TextUtils.isEmpty(pwd)) {
            setErrorMessage(3); // Password Empty
            return false;

        }

        setErrorMessage(-1); // Password Correct
        return true;
    }

    /**
     * Check if the Confirmation : !isEmpty, equals
     *
     * @return True or False.
     */
    private boolean isCofPwdValid(String pwd, String confirmPwd) {
        if (TextUtils.isEmpty(confirmPwd)) {
            setErrorMessage(4); // Confirmation Empty
            return false;
        } else {
            if (!confirmPwd.equals(pwd)) {
                setErrorMessage(5); // Pwd != Conf
                return false;
            }
        }

        setErrorMessage(-2); // Pwd == Conf
        return true;
    }


}

