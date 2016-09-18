package ub.passwordmanager.views.fragments.mainActivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import ub.passwordmanager.R;

public class AboutPage extends Fragment {

    // Field that hold the year of creating of this App
    private String creationYear = "2016";

    public AboutPage() {
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
        View view = inflater.inflate(R.layout.fragment_about_page, container, false);

        // Init the year field
        TextView mYear = (TextView) view.findViewById(R.id.l_about_year);

        // get the ToDay date
        Calendar date = Calendar.getInstance();
        int currentYear = date.get(Calendar.YEAR);

        // set the year
        if (2016 != currentYear) {
            this.creationYear = "2016" + " - " + currentYear;
        }
        mYear.setText(this.creationYear);

        // return the view
        return view;
    }

}
