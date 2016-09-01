package ub.passwordmanager.views.fragments.mainActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ub.passwordmanager.R;

public class ConsultAccount extends Fragment {


    public ConsultAccount() {
        // Required empty public constructor
    }


    public static ConsultAccount newInstance() {
        return new ConsultAccount();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consult_account, container, false);

        // Change the title
        getActivity().setTitle(getActivity().getResources().getString(R.string.Consult_Account_details));



        return view;
    }


}
