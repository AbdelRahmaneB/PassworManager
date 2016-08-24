package ub.passwordmanager.fragments.mainActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ub.passwordmanager.R;


public class SettingPage extends Fragment {


    public SettingPage() {
        // Required empty public constructor
    }

    public static SettingPage newInstance() {
        return new SettingPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_page, container, false);
    }
}
