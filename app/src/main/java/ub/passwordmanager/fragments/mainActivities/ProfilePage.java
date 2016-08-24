package ub.passwordmanager.fragments.mainActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ub.passwordmanager.R;

public class ProfilePage extends Fragment {


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
        return inflater.inflate(R.layout.fragment_profil_page, container, false);
    }


}
