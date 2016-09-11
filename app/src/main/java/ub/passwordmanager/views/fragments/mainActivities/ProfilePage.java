package ub.passwordmanager.views.fragments.mainActivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_UserAccount;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.tools.PwdGenerator.PwdGenerator;
import ub.passwordmanager.views.activities.LogIn;
import ub.passwordmanager.views.activities.MainActivity;

public class ProfilePage extends Fragment {

    private EditText mUsername;
    private EditText mEmail;
    private EditText mOldPwd;
    private EditText mNewPwd;
    private EditText mConfirmPwd;

    private TextInputLayout tUsername;
    private TextInputLayout tEmail;
    private TextInputLayout tOldPwd;
    private TextInputLayout tNewPwd;
    private TextInputLayout tConfirmPwd;

    private ImageView mShowHidePwd;

    private View mProgressView;
    private View mProfilFormView;


    public ProfilePage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil_page, container, false);

        // Initialise the fields
        initialiseTheView(view);

        // Set the listener for our Save button
        final Button bt_saveProfile = (Button) view.findViewById(R.id.bt_saveProfile);
        bt_saveProfile.setOnClickListener(mButtonListener);


        // Deactivate the Floating button
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        // Set the user information.
        getUserAccountInformation();

        return view;
    }

    /**
     * Function to get and show the information of the user in the fields of the view.
     */
    private void getUserAccountInformation() {
        try {
            UserAccountModel userAccount = Service_UserAccount.getAllAccounts(getContext()).get(0);
            mUsername.setTag(userAccount.getId());
            mUsername.setText(userAccount.getUsername());
            mEmail.setText(userAccount.getEmail());
            mOldPwd.setText(userAccount.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialiseTheView(View view){
        mUsername = (EditText) view.findViewById(R.id.t_prof_username_signIn);
        mEmail = (EditText) view.findViewById(R.id.t_prof_email);
        mOldPwd = (EditText) view.findViewById(R.id.t_password_prof);
        mNewPwd = (EditText) view.findViewById(R.id.t_password_new_prof);
        mConfirmPwd = (EditText) view.findViewById(R.id.t_confirmPwd_prof);

        tUsername = (TextInputLayout) view.findViewById(R.id.input_prof_username);
        tEmail = (TextInputLayout) view.findViewById(R.id.input_prof_email);
        tOldPwd = (TextInputLayout) view.findViewById(R.id.input_prof_old_pwd);
        tNewPwd = (TextInputLayout) view.findViewById(R.id.input_prof_pwd_new_prof);
        tConfirmPwd = (TextInputLayout) view.findViewById(R.id.input_prof_confirmPwd_prof);

        mProfilFormView = view.findViewById(R.id.profile_form);
        mProgressView = view.findViewById(R.id.login_progress);

        mShowHidePwd = (ImageView) view.findViewById(R.id.iv_my_visibility_prof);
        mShowHidePwd.setOnClickListener(mShowHideListener);

        final ImageView generatePwd =(ImageView) view.findViewById(R.id.iv_pwd_gen_prof);
        generatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String generatedPwd = PwdGenerator.generatePassword(getActivity());
                mNewPwd.setText(generatedPwd);
                mConfirmPwd.setText(generatedPwd);
                Toast.makeText(getContext(), "Password Generated !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * The Listener for the Save button
     */
    View.OnClickListener mButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            showProgress(true);
            if (saveTheUserAccount()) {
                // alert the user that everything is correct
                Toast.makeText(getContext(), "Data saved correctly !!", Toast.LENGTH_SHORT).show();

                //Restart the application to apply the changes
                getActivity().finish();
                startActivity(new Intent(getContext(), LogIn.class));
            } else {
                // alert the user that everything is correct
                Toast.makeText(getContext(), getResources().getString(R.string.editing_account_error)
                        , Toast.LENGTH_SHORT).show();
                showProgress(false);
            }

        }
    };

    /**
     * The Listener for the Show/Hide Password
     */
    View.OnClickListener mShowHideListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (getResources().getString(R.string.hint_tagVisibility)
                    .equals(mShowHidePwd.getTag().toString())) {
                mShowHidePwd.setTag("NotVisible");
                mShowHidePwd.setImageResource(R.drawable.ic_visibility_off);

                // Show the passwords
                mOldPwd.setTransformationMethod(null);
                mNewPwd.setTransformationMethod(null);
                mConfirmPwd.setTransformationMethod(null);

            } else {
                mShowHidePwd.setTag(getResources().getString(R.string.hint_tagVisibility));
                mShowHidePwd.setImageResource(R.drawable.ic_visibility);

                // Hide The Passwords
                mOldPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                mConfirmPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    };


    /**
     * This part is for the fields test
     * -------------------------------------------------------------
     */

    /**
     * checks if the Username : !isEmpty
     *
     * @return True or False
     */
    private boolean isUsernameValid(UserAccountModel userAccountModel) {
        String username = mUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            // Show Message Error
            tUsername.setError(getResources().getString(R.string.empty_username_error_signIn));
            return false;
        }

        // Hide Message Error
        tUsername.setError(null);
        userAccountModel.setUsername(username);
        return true;
    }

    /**
     * Checks if the Email : !isEmpty, isValid
     * <p/>
     * Uses the {@link AppConfig#isEmailValid(String)}
     *
     * @return True or False.
     */
    private boolean isEmailValid(UserAccountModel userAccountModel) {
        String str = mEmail.getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error message
            tEmail.setError(getResources().getString(R.string.empty_email_error_signIn));
            return false;
        } else {
            if (!AppConfig.getInstance().isEmailValid(str)) {
                // Show Message Error
                tEmail.setError(getResources().getString(R.string.invalid_email_error_signIn));
                return false;
            }

            // Hide Error Message
            tEmail.setError(null);
            userAccountModel.setEmail(str);
            return true;

        }
    }

    /**
     * Check if the Old Password : !isEmpty, isCorrect
     *
     * @return True or False.
     */
    private Boolean isOldPwdValid() {
        String str = mOldPwd.getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error Message
            tOldPwd.setError(getResources().getString(R.string.empty_password_error_signIn));
            return false;
        } else {
            if (!isPwdCorrect(str)) {
                // Show Error Message
                tOldPwd.setError(getResources().getString(R.string.not_correct_password_error_signIn));
                return false;
            }

            // Hide Error Message
            tOldPwd.setError(null);
            return true;
        }
    }

    /**
     * Check for the New Password : !isEmpty
     *
     * @return true or False
     */
    private Boolean isNewPwdValid() {
        String str = mNewPwd.getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error Message
            tNewPwd.setError(getResources().getString(R.string.empty_password_error_signIn));
            return false;
        }

        // Hide Error Messages
        tNewPwd.setError(null);
        return true;
    }

    /**
     * Check if the Confirmation : !isEmpty, isMatchPwd
     *
     * @return True or False.
     */
    private boolean isCofPwdValid(UserAccountModel userAccountModel) {
        String str = mConfirmPwd.getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error Message
            tConfirmPwd.setError(getResources().getString(R.string.empty_confirmation_error_signIn));
            return false;
        } else {
            if (!isMatchPwd(mNewPwd.getText().toString(), str)) {
                // Show Error Message
                tConfirmPwd.setError(getResources().getString(R.string.diff_error_signIn));
                return false;
            }

            // Hide Error Message
            tConfirmPwd.setError(null);
            userAccountModel.setPassword(str);
            return true;
        }
    }

    /**
     * Verify if the Old password match's the one in the DataBase.
     *
     * @param pwd : The password that we want to check.
     * @return True or False.
     */
    private Boolean isPwdCorrect(String pwd) {
        return pwd.equals(AppConfig.getInstance().getCurrentPassword());
    }

    /**
     * Test if the password changed or not
     *
     * @return True or False.
     */
    private boolean isNewAndPwdConfirmationEmpty() {
        String newPwd = mNewPwd.getText().toString();
        String confPwd = mConfirmPwd.getText().toString();
        return (TextUtils.isEmpty(newPwd)) && (TextUtils.isEmpty(confPwd));
    }

    /**
     * Verify if the new Password match it's Confirmation.
     *
     * @param pwd     : The new Password.
     * @param confirm : The Password Confirmation.
     * @return True or False.
     */
    private Boolean isMatchPwd(String pwd, String confirm) {
        return pwd.equals(confirm);
    }

    /**
     * Test If the fields are correctly filled
     *
     * @param mUserAccountModel : the object holder
     * @return True if everything correct or false otherwise;
     */
    private boolean isTheFieldsCorrect(UserAccountModel mUserAccountModel) {
        return (isUsernameValid(mUserAccountModel) & isEmailValid(mUserAccountModel)
                & isOldPwdValid());
    }

    /**
     * The role of this function is to Test and save the object into the DataBase
     *
     * @return True if everything Correct, False otherwise.
     */
    private Boolean saveTheUserAccount() {

        // Create our DataHolder
        UserAccountModel mUserAccountModel = new UserAccountModel();

        Boolean reEncryptTheData = false;

        // Test if the fields are correctly filled
        if (isTheFieldsCorrect(mUserAccountModel)) {

            // Test if the Password changed
            switch (verifyPassword(mUserAccountModel)) {
                case -1:
                    return false;

                case 1:
                    reEncryptTheData = true;
                    break;

                default:
                    // value = 0 Do Nothing
                    break;
            }

            // Set the ID
            mUserAccountModel.setId(Integer.parseInt(mUsername.getTag().toString()));

            try {
                // Save the object into DataBase
                return saveObject(mUserAccountModel, reEncryptTheData);

            } catch (Exception ex) {
                Log.e("SaveTheUserAccount", "[" + ex.getMessage() + "]");
                ex.printStackTrace();
                return false;
            }

        }

        // There was a problem
        return false;
    }

    /**
     * The function that actually send the information to the DataBase
     * @param mUserAccountModel : The object to save.
     * @param reEncryptTheData : Mention if we need to re-Encrypt the PwdAccount Data.
     * @return true or false.
     * @throws Exception
     */
    private Boolean saveObject(UserAccountModel mUserAccountModel, Boolean reEncryptTheData) throws Exception {
        if (Service_UserAccount.saveModifiedData(getContext(), mUserAccountModel, reEncryptTheData)) {

            // Refresh the Current Username
            AppConfig.getInstance().setCurrentUser(
                    mUsername.getText().toString()
            );

            // Save the new Username into the Preferences file
            AppConfig.getInstance().saveValueToPreference(getActivity(),
                    AppConfig.KEY_PREF_STRING,
                    AppConfig.KEY_PREF_USERNAME,
                    mUsername.getText().toString());

            // Refresh the fields
            mOldPwd.setText(AppConfig.getInstance().getCurrentPassword());
            mNewPwd.setText(null);
            mConfirmPwd.setText(null);

            return true;
        } else
            return false;
    }

    /**
     * Function to test the validity of the password.
     * -1 : There was a problem.
     * 0 : Password didn't change.
     * 1 : Password changed.
     *
     * @param mUserAccountModel : the object holder.
     * @return True or False.
     */
    private int verifyPassword(UserAccountModel mUserAccountModel) {

        // Test if the Password changed
        if (!isNewAndPwdConfirmationEmpty()) {
            // The fields are not empty then we should test
            if (!isNewPwdValid() & !isCofPwdValid(mUserAccountModel)) {
                return -1; // the password and the confirmation doesn't match
            }

            // Save the new Password
            AppConfig.getInstance().setCurrentPassword(
                    mNewPwd.getText().toString()
            );
            return 1;
        } else {
            mUserAccountModel.setPassword(AppConfig.getInstance().getCurrentPassword());
            return 0;
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProfilFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProfilFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
