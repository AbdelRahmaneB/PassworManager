package ub.passwordmanager.fragments.Connection.SignIn;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ub.passwordmanager.R;


public class SignInUserInfoFragment extends Fragment {

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
        TextView l_notice = (TextView) view.findViewById(R.id.l_notice);
//        l_notice.requestFocus();

        TextInputLayout tp = (TextInputLayout) view.findViewById(R.id.input_username_signIn);
        tp.getEditText().setError("Error");



        // Inflate the layout for this fragment
        return view ;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
