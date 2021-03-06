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
import android.widget.ImageView;
import android.widget.Toast;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.tools.PwdGenerator.PwdGenerator;
import ub.passwordmanager.views.fragments.OnDataPass;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in_pwd_info, container, false);

        // Init the view field
        this.tv_Password = (EditText) view.findViewById(R.id.t_password_signIn);
        this.tv_Confirmation = (EditText) view.findViewById(R.id.t_confirmPwd_signIn);
        this.t_InputPwd = (TextInputLayout) view.findViewById(R.id.input_pwd_sigIn);
        this.t_InputConfirmation = (TextInputLayout) view.findViewById(R.id.input_confirmPwd_signIn);

        // Init the Data holder
        this.mUserAccount = new UserAccountModel();

        // Init the password generator
        final ImageView generatePwd = (ImageView) view.findViewById(R.id.iv_pwd_gen_prof);
        generatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String generatedPwd = PwdGenerator.getInstance().generatePassword(getActivity());
                tv_Password.setText(generatedPwd);
                tv_Confirmation.setText(generatedPwd);
                Toast.makeText(getContext(), "Password Generated !!", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the textWatcher listener to  our fields
        this.tv_Password.addTextChangedListener(watchPwd);
        this.tv_Confirmation.addTextChangedListener(watchConfirmation);

        // return the view
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
        if (message == null)
            t_InputPwd.setErrorEnabled(false);
        t_InputPwd.setError(message);
    }

    /**
     * Method to define the Error Message of our Confirmation TextView
     */
    public void setConfirmErrorMessage(String message) {
        if (message == null)
            t_InputConfirmation.setErrorEnabled(false);
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
        this.mUserAccount.setConfirmationPwd(tv_Confirmation.getText().toString());

        // Notify the listener that the data changed so he can notify the activity
        dataPasser.onDataPass(this.mUserAccount);
    }

}
