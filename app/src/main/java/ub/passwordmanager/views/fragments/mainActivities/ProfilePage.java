package ub.passwordmanager.views.fragments.mainActivities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;

public class ProfilePage extends Fragment {

    private TextInputLayout mUsername;
    private TextInputLayout mEmail;
    private TextInputLayout mOldPwd;
    private TextInputLayout mNewPwd;
    private TextInputLayout mConfirmPwd;


    public ProfilePage() {
        // Required empty public constructor
    }

    public static ProfilePage newInstance() {
        return new ProfilePage();
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
        mUsername = (TextInputLayout) view.findViewById(R.id.input_prof_username);
        mEmail = (TextInputLayout) view.findViewById(R.id.input_prof_email);
        mOldPwd = (TextInputLayout) view.findViewById(R.id.input_prof_old_pwd);
        mNewPwd = (TextInputLayout) view.findViewById(R.id.input_prof_pwd_new_prof);
        mConfirmPwd = (TextInputLayout) view.findViewById(R.id.input_prof_confirmPwd_prof);

        // Set the listener for our Save button
        final Button bt_saveProfile = (Button) view.findViewById(R.id.bt_saveProfile);
        bt_saveProfile.setOnClickListener(mButtonListener);

        // Deactivate the Floating button
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        return view;
    }


    View.OnClickListener mButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Create our DataHolder
            UserAccountModel mUserAccountModel = null;

            // Test If all Fields are valid then Save the New Data
            if (isUsernameValid(mUserAccountModel) & isEmailValid(mUserAccountModel)
                    & isOldPwdValid() & isNewPwdValid()
                    & isCofPwdValid(mUserAccountModel)) {

                // Save the 'mUserAccountModel' Into DataBase
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
        if (TextUtils.isEmpty(mUsername.getEditText().getText().toString())) {
            // Show Message Error
            mUsername.setError(getResources().getString(R.string.empty_username_error_signIn));
            return false;
        }

        // Hide Message Error
        mUsername.setError(null);
        return true;
    }

    /**
     * Checks if the Email : !isEmpty, isValid
     *
     * Uses the {@link AppConfig#isEmailValid(String)}
     * @return True or False.
     */
    private boolean isEmailValid(UserAccountModel userAccountModel) {
        String str = mEmail.getEditText().getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error message
            mEmail.setError(getResources().getString(R.string.empty_email_error_signIn));
            return false;
        } else {
            if (!AppConfig.getInstance().isEmailValid(str)) {
                // Show Message Error
                mEmail.setError(getResources().getString(R.string.invalid_email_error_signIn));
                return false;
            }

            // Hide Error Message
            mEmail.setError(null);
            return true;

        }
    }

    /**
     * Check if the Old Password : !isEmpty, isCorrect
     *
     * @return True or False.
     */
    private Boolean isOldPwdValid() {
        String str = mOldPwd.getEditText().getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error Message
            mOldPwd.setError(getResources().getString(R.string.empty_password_error_signIn));
            return false;
        } else {
            if (!isPwdCorrect(str)) {
                // Show Error Message
                mOldPwd.setError(getResources().getString(R.string.not_correct_password_error_signIn));
                return false;
            }

            // Hide Error Message
            mOldPwd.setError(null);
            return true;
        }
    }

    /**
     * Check for the New Password : !isEmpty
     *
     * @return true or False
     */
    private Boolean isNewPwdValid() {
        String str = mNewPwd.getEditText().getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error Message
            mNewPwd.setError(getResources().getString(R.string.empty_password_error_signIn));
            return false;
        }

        // Hide Error Messages
        mNewPwd.setError(null);
        return true;
    }

    /**
     * Check if the Confirmation : !isEmpty, isMatchPwd
     *
     * @return True or False.
     */
    private boolean isCofPwdValid(UserAccountModel userAccountModel) {
        String str = mConfirmPwd.getEditText().getText().toString();
        if (TextUtils.isEmpty(str)) {
            // Show Error Message
            mConfirmPwd.setError(getResources().getString(R.string.empty_confirmation_error_signIn));
            return false;
        } else {
            if (!isMatchPwd(mNewPwd.getEditText().getText().toString(), str)) {
                // Show Error Message
                mConfirmPwd.setError(getResources().getString(R.string.diff_error_signIn));
                return false;
            }

            // Hide Error Message
            mConfirmPwd.setError(null);
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
        // ToDo : add the function to text if the password is th right one
        return true;
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

}
