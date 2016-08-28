package ub.passwordmanager.fragments.mainActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import ub.passwordmanager.R;

public class AboutPage extends Fragment {

    private String creationYear = "2016";

    public AboutPage() {
        // Required empty public constructor
    }

    public static AboutPage newInstance() {
        return new AboutPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about_page, container, false);


        // Set the year of creation
        TextView myear = (TextView) view.findViewById(R.id.l_about_year);
        Calendar date = Calendar.getInstance();
        int currentYear = date.get(Calendar.YEAR);

        if (2016 != currentYear){
            this.creationYear = "2016" + " - " + currentYear;
        }

        myear.setText(this.creationYear);

        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        return view;
    }

}
