package ub.passwordmanager.fragments.Registration;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.fragments.OnDataPass;


public class SignInPwdInfoFragment extends Fragment {

    // Instance of our interface to send data to our Activity
    private OnDataPass dataPasser;

    // Our DataHolder for the UserAccount
    private UserAccountModel mUserAccount;


    // Our view fields
    private EditText tv_Password;
    private EditText tv_Confirmation;
    private TextInputLayout t_InputPwd;
    private TextInputLayout t_InputConfirmation;

    public SignInPwdInfoFragment() {
        // Required empty public constructor
    }


    public static SignInPwdInfoFragment newInstance() {
        return new SignInPwdInfoFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in_pwd_info, container, false);

        this.tv_Password = (EditText) view.findViewById(R.id.t_password_signIn);
        this.tv_Confirmation = (EditText) view.findViewById(R.id.t_confirmPwd_signIn);
        this.t_InputPwd = (TextInputLayout) view.findViewById(R.id.input_pwd_sigIn);
        this.t_InputConfirmation = (TextInputLayout) view.findViewById(R.id.input_confirmPwd_signIn);

        this.mUserAccount = new UserAccountModel();

        // Add the textWatcher listener to  our fields
        this.tv_Password.addTextChangedListener(watchPwd);
        this.tv_Confirmation.addTextChangedListener(watchConfirmation);

        return view;
    }

    /**
     * TextWatcher Listener for the Password field
     */
    TextWatcher watchPwd = new TextWatcher() {
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
     * TextWatcher Listener for the Confirmation field
     */
    TextWatcher watchConfirmation = new TextWatcher() {
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
     * Method to define the Error Message of our Password TextView
     */
    public void setPwdErrorMessage(String message) {
        t_InputPwd.setError(message);
    }

    /**
     * Method to define the Error Message of our Confirmation TextView
     */
    public void setConfirmErrorMessage(String message) {
        t_InputConfirmation.setError(message);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

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
        this.mUserAccount.setPassword(tv_Password.getText().toString());
        this.mUserAccount.setmConfirmationPwd(tv_Confirmation.getText().toString());

        // Notify the listener that the data changed so he can notify the activity
        dataPasser.onDataPass(this.mUserAccount);
    }

}
