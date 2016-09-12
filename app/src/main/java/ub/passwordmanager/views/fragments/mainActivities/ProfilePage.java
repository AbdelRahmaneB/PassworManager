package ub.passwordmanager.views.fragments.mainActivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_UserAccount;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.tools.PwdGenerator.PwdGenerator;
import ub.passwordmanager.views.SplashScreen;

public class ProfilePage extends Fragment {

    private EditText mUsername;
    private EditText mEmail;
    private EditText mOldPwd;
    private EditText mNewPwd;
    private EditText mConfirmPwd;

    private Button bt_saveProfile;

    private TextInputLayout tUsername;
    private TextInputLayout tEmail;
    private TextInputLayout tOldPwd;
    private TextInputLayout tNewPwd;
    private TextInputLayout tConfirmPwd;

    private ImageView mShowHidePwd;

    private View mProgressView;
    private View mProfileFormView;

    private UserAccountModel mUserAccountModel;


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

        // Set the listener for our Save button
        bt_saveProfile = (Button) view.findViewById(R.id.bt_saveProfile);
        bt_saveProfile.setOnClickListener(mButtonListener);

        // Initialise the fields
        this.initialiseTheView(view);

        // Set the user information.
        this.getUserAccountInformation();


        // Deactivate the Floating button
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);


        return view;
    }

    /**
     * Init the Fields from the view
     *
     * @param view : The user view in our case "ProfileView"
     */
    private void initialiseTheView(View view) {
        // For the EditText
        mUsername = (EditText) view.findViewById(R.id.t_prof_username_signIn);
        mUsername.addTextChangedListener(tw_Username);

        mEmail = (EditText) view.findViewById(R.id.t_prof_email);
        mEmail.addTextChangedListener(tw_Email);

        mOldPwd = (EditText) view.findViewById(R.id.t_password_prof);
        mOldPwd.addTextChangedListener(tw_OldPwd);

        mNewPwd = (EditText) view.findViewById(R.id.t_password_new_prof);
        mNewPwd.addTextChangedListener(tw_NewPwd);

        mConfirmPwd = (EditText) view.findViewById(R.id.t_confirmPwd_prof);
        mConfirmPwd.addTextChangedListener(tw_ConfirmPwd);

        // For the TextInputLayout
        tUsername = (TextInputLayout) view.findViewById(R.id.input_prof_username);
        tEmail = (TextInputLayout) view.findViewById(R.id.input_prof_email);
        tOldPwd = (TextInputLayout) view.findViewById(R.id.input_prof_old_pwd);
        tNewPwd = (TextInputLayout) view.findViewById(R.id.input_prof_pwd_new_prof);
        tConfirmPwd = (TextInputLayout) view.findViewById(R.id.input_prof_confirmPwd_prof);

        // For the views
        mProfileFormView = view.findViewById(R.id.profile_form);
        mProgressView = view.findViewById(R.id.login_progress);

        // Hide/Show Password
        mShowHidePwd = (ImageView) view.findViewById(R.id.iv_my_visibility_prof);
        mShowHidePwd.setOnClickListener(mShowHideListener);

        // For the Password generator
        final ImageView generatePwd = (ImageView) view.findViewById(R.id.iv_pwd_gen_prof);
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
     * Function to get and show the information of the user in the fields of the view.
     */
    private void getUserAccountInformation() {
        try {
            mUserAccountModel = Service_UserAccount.getAllAccounts(getContext()).get(0);
            mUsername.setTag(mUserAccountModel.getId());
            mUsername.setText(mUserAccountModel.getUsername());
            mEmail.setText(mUserAccountModel.getEmail());
            mOldPwd.setText(mUserAccountModel.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
     * The Listener for the Save button
     */
    View.OnClickListener mButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            saveProfile();
        }
    };


    /**
     * TextWatcher for the username
     */
    TextWatcher tw_Username = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String username = mUsername.getText().toString();
            if (TextUtils.isEmpty(username)) {
                // Show Message Error
                tUsername.setError(getResources().getString(R.string.empty_username_error_signIn));
                bt_saveProfile.setEnabled(false);
                return;
            }

            // Hide Message Error
            tUsername.setErrorEnabled(false);
            bt_saveProfile.setEnabled(true);
            mUserAccountModel.setUsername(username);
        }
    };

    /**
     * TextWatcher for the Email
     */
    TextWatcher tw_Email = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String str = mEmail.getText().toString();
            if (TextUtils.isEmpty(str)) {
                // Show Error message
                tEmail.setError(getResources().getString(R.string.empty_email_error_signIn));
                bt_saveProfile.setEnabled(false);
            } else {
                if (!AppConfig.getInstance().isEmailValid(str)) {
                    // Show Message Error
                    tEmail.setError(getResources().getString(R.string.invalid_email_error_signIn));
                    bt_saveProfile.setEnabled(false);
                    return;
                }

                // Hide Error Message
                tEmail.setErrorEnabled(false);
                bt_saveProfile.setEnabled(true);
                mUserAccountModel.setEmail(str);
            }
        }
    };

    /**
     * TextWatcher for the OldPassword
     */
    TextWatcher tw_OldPwd = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String str = mOldPwd.getText().toString();
            if (TextUtils.isEmpty(str)) {
                // Show Error Message
                tOldPwd.setError(getResources().getString(R.string.empty_password_error_signIn));
                bt_saveProfile.setEnabled(false);
            } else {
                if (!isOldPwdCorrect(str)) {
                    // Show Error Message
                    tOldPwd.setError(getResources().getString(R.string.not_correct_password_error_signIn));
                    bt_saveProfile.setEnabled(false);
                    return;
                }

                // Hide Error Message
                tOldPwd.setErrorEnabled(false);
                bt_saveProfile.setEnabled(true);
                mUserAccountModel.setPassword(str);
            }
        }
    };

    /**
     * TextWatcher for the New Password
     */
    TextWatcher tw_NewPwd = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mNewPwd.getText().length() > 0 && mConfirmPwd.getText().length() <= 0){
                // Show a message that the confirmation is required
                tConfirmPwd.setError(getResources().getString(R.string.empty_confirmation_error_signIn));

                //deactivate the button
                bt_saveProfile.setEnabled(false);
            }else{
                // Test if the new Pwd and confirmation matches
                if (!mNewPwd.getText().toString().equals(mConfirmPwd.getText().toString())){
                    // Show a message to notify that the password's doesn't match
                    tConfirmPwd.setError(getResources().getString(R.string.diff_error_signIn));

                    //deactivate the button
                    bt_saveProfile.setEnabled(false);
                }else {
                    // Hide the message
                    tConfirmPwd.setErrorEnabled(false);
                    tNewPwd.setErrorEnabled(false);


                    //Activate the button
                    bt_saveProfile.setEnabled(true);
                }
            }
        }

    };

    /**
     * TextWatcher for the Password confirmation
     */
    TextWatcher tw_ConfirmPwd = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mConfirmPwd.getText().length() > 0 && mNewPwd.getText().length() <= 0){
                // Show a message that the New password is required
                tNewPwd.setError(getResources().getString(R.string.empty_confirmation_error_signIn));

                //deactivate the button
                bt_saveProfile.setEnabled(false);
            }else{
                // Test if the new Pwd and confirmation matches
                if (!mNewPwd.getText().toString().equals(mConfirmPwd.getText().toString())){
                    // Show a message to notify that the password's doesn't match
                    tConfirmPwd.setError(getResources().getString(R.string.diff_error_signIn));

                    //deactivate the button
                    bt_saveProfile.setEnabled(false);
                }else {
                    // Hide the message
                    tConfirmPwd.setErrorEnabled(false);
                    tNewPwd.setErrorEnabled(false);

                    //Activate the button
                    bt_saveProfile.setEnabled(true);
                }
            }


        }
    };


    //************************************************************************************************


    /**
     * This part is for the fields test
     * -------------------------------------------------------------
     */

    /**
     * Verify if the Old password match's the one in the DataBase.
     *
     * @param pwd : The password that we want to check.
     * @return True or False.
     */
    private Boolean isOldPwdCorrect(String pwd) {
        return pwd.equals(AppConfig.getInstance().getCurrentPassword());
    }

    /**
     * Test if the user changed the password
     *
     * @return True or False.
     */
    private boolean isNewAndPwdConfirmationEmpty() {
        String newPwd = mNewPwd.getText().toString();
        String confPwd = mConfirmPwd.getText().toString();
        return (TextUtils.isEmpty(newPwd)) && (TextUtils.isEmpty(confPwd));
    }

    /**
     * The function that actually send the information to the DataBase
     *
     * @param mUserAccountModel : The object to save.
     * @param reEncryptTheData  : Mention if we need to re-Encrypt the PwdAccount Data.
     * @throws Exception
     */
    private void saveTheObject(UserAccountModel mUserAccountModel, Boolean reEncryptTheData) throws Exception {
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

            Toast.makeText(getContext(), "Data saved correctly !!", Toast.LENGTH_SHORT).show();
        } else
            showProgress(false);
    }

    /**
     * The role of this function to define witch password to use (Old Or new)
     * and also set the data before sending it to the DataBase
     */
    private void saveProfile() {
        try {
            Boolean reEncryptTheData = false;

            // Show the progress bar
            showProgress(true);

            if (isNewAndPwdConfirmationEmpty()) {
                // Set the ID
                mUserAccountModel.setId(Integer.parseInt(mUsername.getTag().toString()));

                // Set the password
                mUserAccountModel.setPassword(mOldPwd.getText().toString());

            } else {

                // password changed
                reEncryptTheData = true;

                // Set the ID
                mUserAccountModel.setId(Integer.parseInt(mUsername.getTag().toString()));

                // Set the password
                mUserAccountModel.setPassword(mNewPwd.getText().toString());

                // Change the current password
                AppConfig.getInstance().setCurrentPassword(
                        mNewPwd.getText().toString()
                );

            }

            // Save the object
            saveTheObject(mUserAccountModel, reEncryptTheData);

            //Restart the application to apply the changes
            getActivity().finish();
            getActivity().getSupportFragmentManager().popBackStack();
            startActivity(new Intent(getContext(), SplashScreen.class));

        } catch (Exception ex) {
            showProgress(false);
            Log.e("**** Saving Data *****", "[" + ex.getMessage() + "]");
            ex.printStackTrace();
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

            mProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mProfileFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mProfileFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
