package ub.passwordmanager.views.fragments.Registration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.views.fragments.OnDataPass;


public class SignInUserInfoFragment extends Fragment {

    // Instance of our interface to send data to our Activity
    private OnDataPass dataPasser;

    // Our DataHolder for the UserAccount
    private UserAccountModel mUserAccount;


    // Our view fields
    private EditText tv_Username;
    private EditText tv_Email;
    private TextInputLayout t_InputEmail;
    private TextInputLayout t_InputUsername;

    public SignInUserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignInUserInfoFragment.
     */
    public static SignInUserInfoFragment newInstance() {
        return new SignInUserInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in_user_info, container, false);

        this.tv_Username = (EditText) view.findViewById(R.id.t_username_signIn);
        this.tv_Email = (EditText) view.findViewById(R.id.t_email_signIn);
        this.t_InputEmail = (TextInputLayout) view.findViewById(R.id.input_email_signIn);
        this.t_InputUsername = (TextInputLayout) view.findViewById(R.id.input_username_signIn);

        this.mUserAccount = new UserAccountModel();
        // Add the textWatcher listener to  our fields
        this.tv_Username.addTextChangedListener(watchUsername);
        this.tv_Email.addTextChangedListener(watchEmail);


        // Inflate the layout for this fragment
        return view;
    }

    /**
     * TextWatcher Listener for the Username field
     */
    TextWatcher watchUsername = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Nothing to do here
        }

        @Override
        public void afterTextChanged(Editable editable) {
            passData();
        }
    };

    /**
     * TextWatcher Listener for the Email field
     */
    TextWatcher watchEmail = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Nothing to do here
        }

        @Override
        public void afterTextChanged(Editable editable) {
            passData();
        }
    };


    /**
     * Method to define the Error Message of our Email TextView
     */
    public void setEmailErrorMessage(String message) {
        t_InputEmail.setError(message);
    }

    /**
     * Method to define the Error Message of our Username TextView
     */
    public void setUsernameErrorMessage(String message) {
        t_InputUsername.setError(message);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Description :
     * This method is called once the fragment is attached to it's activity.
     *
     * @param context : of the activity that the fragment is attached to.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Attach the fragment to the activity
        if (context instanceof Activity) {
            dataPasser = (OnDataPass) context;
        }
    }

    /**
     * Description :
     * In this method we pass our object to the activity
     */
    public void passData() {
        // Fill the object before passing it to the Activity
        this.mUserAccount.setUsername(tv_Username.getText().toString());
        this.mUserAccount.setEmail(tv_Email.getText().toString());

        // Notify the listener that the data changed so he can notify the activity
        dataPasser.onDataPass(this.mUserAccount);
    }
}
