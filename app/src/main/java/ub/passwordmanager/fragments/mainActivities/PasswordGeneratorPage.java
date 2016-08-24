package ub.passwordmanager.fragments.mainActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import ub.passwordmanager.R;
import ub.passwordmanager.adapters.PwdGenListAdapter;

public class PasswordGeneratorPage extends Fragment {

    public String[] mTitles;
    public String[] mDesc;
    public int[] mChecked;

    public PasswordGeneratorPage() {
        // Required empty public constructor
    }


    public static PasswordGeneratorPage newInstance() {
        return new PasswordGeneratorPage();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_password_generator, container, false);

        // Instantiate the generate button
        Button bt_pwdGen = (Button) view.findViewById(R.id.bt_pwd_gen);
        bt_pwdGen.requestFocus();

        configureTheAdapter();

        // Instantiate the ListView and set the adapter
        ListView mOptionList = (ListView) view.findViewById(R.id.pwd_gen_options_list);
        PwdGenListAdapter PwdGenAdapter = new PwdGenListAdapter(getActivity(), mTitles, mDesc, mChecked);
        mOptionList.setAdapter(PwdGenAdapter);


        // Deactivate the Floating button
        FloatingActionButton mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.INVISIBLE);

        return view;
    }

    /**
     * This function initialise the required arrays for the options ListView
     */
    private void configureTheAdapter() {

        mTitles = new String[]{getResources().getString(R.string.opt_title1),
                getResources().getString(R.string.opt_title2),
                getResources().getString(R.string.opt_title3),
                getResources().getString(R.string.opt_title5)};

        mDesc = new String[]{getResources().getString(R.string.opt_title1_desc),
                getResources().getString(R.string.opt_title2_desc),
                getResources().getString(R.string.opt_title3_desc),
                getResources().getString(R.string.opt_title5_desc)};

        // ToDo : Read from the preferences file
        mChecked = new int[]{1,1,1,1};
    }

}
