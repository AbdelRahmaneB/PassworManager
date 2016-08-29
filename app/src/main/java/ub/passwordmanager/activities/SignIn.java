package ub.passwordmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.factories.FragmentFactory;
import ub.passwordmanager.fragments.OnDataPass;
import ub.passwordmanager.fragments.Registration.SignInPwdInfoFragment;
import ub.passwordmanager.fragments.Registration.SignInUserInfoFragment;

public class SignIn extends AppCompatActivity implements OnDataPass {

    // This field will contain the active fragment
    private Fragment activeFragment = null;

    // The view fields
    private Button bt_next = null;
    private Button bt_previous = null;

    // Our Regex Pattern for the validity of the Email Address
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


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
     * <p>
     * - For the Case - "Next" : the method get the information from the fragment and send it
     * to the next one.
     * - For the case - "Save" : The method get the information from both fragment and send it
     * to the persistence class to save it in a Data Base
     * <p>
     * Uses the @switchFragment() method.
     */
    private void ActionForButtonNext() {

        if (getResources().getString(R.string.bt_text_next).equals(bt_next.getText().toString())) {

            // Switch the active fragment to "Password information fragment"
            switchFragment(FragmentFactory.getInstance().getSignInPwdInfoFragment(), true);

            try {
                // Send the  data to the next fragment
                Bundle args = new Bundle();
                args.putString("myMessage", "It worked"); // TODO : Replace the Values with the user Object
                activeFragment.setArguments(args);
            } catch (Exception ex) {
            }

            // Change The visibility, button text and the activity title.
            bt_previous.setVisibility(View.VISIBLE);
            bt_next.setText(getResources().getString(R.string.bt_text_register));
            setTitle(R.string.sign_in_part2);

        } else {
            // TODO : Text is "Register" : Call for saving Data and move to login page
            /** Steps :
             * 1) Get the information from the previews fragment.
             * 2) Create a user Object and send it to the data base.
             * 3) move to the login activity
             * 4) Clear the cache so this page can't be access by a back press (See splashScreen)
             */
            //Bundle bundle = activeFragment.getArguments();
            //Toast.makeText(getBaseContext(), "Info" + bundle.get("myMessage"), Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getApplicationContext(), LogIn.class));
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


    @Override
    public void onDataPass(Object data) {
        // ToDo : Get The object data from the Fragment
        if (data instanceof UserAccountModel) {
            UserAccountModel mUserAccount = (UserAccountModel) data;
            String str;
            // if isValid is tru then it's correct
            Boolean isValidUS ;
            Boolean isValidEM ;
            Boolean isValidPwd ;
            Boolean isValidConf ;
            Boolean isNotDiff ;

            // Username
            str = mUserAccount.getUsername();
            if (isUsernameEmpty(str)) {
                setErrorMessage(0); // Username Empty
                isValidUS = false;

            } else {
                setErrorMessage(-1); // Username valid
                isValidUS = true;
            }


            // Email
            str = mUserAccount.getEmail();
            if (isEmailEmpty(str)) {
                setErrorMessage(1); // Email Empty
                isValidEM = false;

            } else if (!isEmailValid(str)) {
                setErrorMessage(2); // Email Invalid
                isValidEM = false;

            } else {
                setErrorMessage(-1); // Email Correct
                isValidEM = true;
            }

            // Handle the button visibility
            if (isValidEM && isValidUS) {
                bt_next.setVisibility(View.VISIBLE);
            } else {
                bt_next.setVisibility(View.INVISIBLE);
            }

            if ("SignInPwdInfoFragment".equals(activeFragment.getClass().getSimpleName())) {
                // Password
                str = mUserAccount.getPassword();
                if (isPwdEmpty(str)) {
                    setErrorMessage(3); // Password Empty
                    isValidPwd = false;

                } else {
                    setErrorMessage(-1); // Password Correct
                    isValidPwd = true;
                }

                // Confirm Password
                str = mUserAccount.getmConfirmationPwd();
                if (isPwdEmpty(str)) {
                    setErrorMessage(4); // Confirmation Empty
                    isValidConf = false;

                } else {
                    setErrorMessage(-1); // Confirmation Correct
                    isValidConf = true;
                }

                if (!mUserAccount.getmConfirmationPwd().equals(
                        mUserAccount.getPassword()
                )) {
                    setErrorMessage(5); // Pwd != Conf
                    isNotDiff = false;
                } else {
                    setErrorMessage(-1); // Pwd == Conf
                    isNotDiff = true;
                }

                if (isValidPwd && isValidConf && isNotDiff) {
                    bt_next.setVisibility(View.VISIBLE);
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
     *                  *) - default : Everything correct
     */
    private void setErrorMessage(int IdMessage) {

        if ("SignInUserInfoFragment".equals(activeFragment.getClass().getSimpleName())) {
            SignInUserInfoFragment tempUserInfo = (SignInUserInfoFragment) activeFragment;

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
                    tempUserInfo.setUsernameErrorMessage(null);
                    tempUserInfo.setEmailErrorMessage(null);
                    break;

            }
        } else {

            SignInPwdInfoFragment tempPwdInfo = (SignInPwdInfoFragment) activeFragment;

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
                    tempPwdInfo.setPwdErrorMessage(null);
                    tempPwdInfo.setConfirmErrorMessage(null);
                    // Ad for the password and confirmation
                    break;

            }

        }

    }

    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean isEmailEmpty(String email) {
        return TextUtils.isEmpty(email);
    }

    private boolean isUsernameEmpty(String username) {
        return TextUtils.isEmpty(username);
    }

    private boolean isPwdEmpty(String pwd) {
        return TextUtils.isEmpty(pwd);
    }


}

