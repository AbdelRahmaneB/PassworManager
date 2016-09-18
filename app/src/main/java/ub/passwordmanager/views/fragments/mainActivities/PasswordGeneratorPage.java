package ub.passwordmanager.views.fragments.mainActivities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.tools.PwdGenerator.PwdGenerator;
import ub.passwordmanager.views.adapters.PwdGenListAdapter;
import ub.passwordmanager.views.fragments.dialogs.EditPwdAccountDialog;

public class PasswordGeneratorPage extends Fragment {

    public List<String> mTitles;
    public List<String> mDesc;
    public List<Boolean> mChecked;

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

        final EditText mGeneratedPwd = (EditText) view.findViewById(R.id.t_pwd_gen);
        mGeneratedPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AppConfig.copyToClipBoard(getActivity(),mGeneratedPwd.getText().toString());
            }
        });

        // Instantiate the generate button
        final Button bt_pwdGen = (Button) view.findViewById(R.id.bt_pwd_gen);
        bt_pwdGen.requestFocus();
        bt_pwdGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String generatedPwd = PwdGenerator.generatePassword(getActivity());
                mGeneratedPwd.setText(generatedPwd);
                Toast.makeText(getContext(), "Data copied !!", Toast.LENGTH_SHORT).show();
            }
        });

        configureTheAdapter();

        // Instantiate the ListView and set the adapter
        ListView mOptionList = (ListView) view.findViewById(R.id.pwd_gen_options_list);
        PwdGenListAdapter PwdGenAdapter = new PwdGenListAdapter(getActivity(), mTitles, mDesc, mChecked);
        mOptionList.setAdapter(PwdGenAdapter);

        return view;
    }

    /**
     * This function initialise the required arrays for the options ListView
     */
    private void configureTheAdapter() {

        mTitles = Arrays.asList(getResources().getString(R.string.opt_title1),
                getResources().getString(R.string.opt_title2),
                getResources().getString(R.string.opt_title3),
                getResources().getString(R.string.opt_title5));

        mDesc = Arrays.asList(getResources().getString(R.string.opt_title1_desc),
                getResources().getString(R.string.opt_title2_desc),
                getResources().getString(R.string.opt_title3_desc),
                getResources().getString(R.string.opt_title5_desc));

        // Read from the preferences file
        mChecked = Arrays.asList(
                PwdGenerator.useLowerCase(getActivity()),
                PwdGenerator.useUpperCase(getActivity()),
                PwdGenerator.useSymbols(getActivity()),
                PwdGenerator.useNumbers(getActivity())
        );

    }




}
