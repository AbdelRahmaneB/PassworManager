package ub.passwordmanager.fragments.Registration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.fragments.OnDataPass;


public class SignInUserInfoFragment extends Fragment {

    // Instance of our interface to send data to our Activity
    private OnDataPass dataPasser;

    // Our DataHolder for the UserAccount
    private UserAccountModel mUserAccount;

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

        EditText tv_Username = (EditText) view.findViewById(R.id.t_username_signIn);
        EditText tv_Email = (EditText) view.findViewById(R.id.t_email_signIn);

        tv_Username.addTextChangedListener(new TextWatcher() {
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
                Toast.makeText(getContext(),"Lol",Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    /** Description :
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
     *
     * @param data : User account which contain Username and Email
     */
    public void passData(UserAccountModel data) {
        dataPasser.onDataPass(data);
    }
}
