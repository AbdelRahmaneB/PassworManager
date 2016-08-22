package ub.passwordmanager.fragments.Connection.SignIn;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import ub.passwordmanager.R;


public class SignInPwdInfoFragment extends Fragment {


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

        final ImageView iv = (ImageView) view.findViewById(R.id.iv_my_visibility_sigIn);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getBaseContext(), "Button", Toast.LENGTH_SHORT).show();
                if ("Visible".equals(iv.getTag().toString())) {
                    iv.setBackgroundResource(R.drawable.ic_visibility_off);
                    iv.setTag("NotVisible");
                }else{
                    iv.setBackgroundResource(R.drawable.ic_visibility);
                    iv.setTag("Visible");
                }

            }
        });

        return view;
    }


    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
