package ub.passwordmanager.views.fragments.mainActivities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;


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
        View view = inflater.inflate(R.layout.fragment_setting_page, container, false);

        // Get the listView instance
        ListView mSettingListView = (ListView) view.findViewById(R.id.settings_list_view);
        String[] values = new String[]{
                getResources().getString(R.string.setting_list_view_items_lang)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mSettingListView.setAdapter(adapter);


        // Add the listener for our listView
        mSettingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Call the function that handles the language changes
                showLocationDialog();
            }
        });

        return view;
    }


    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.lang_dialog_title));

        final String[] lang = {
                getResources().getString(R.string.lang_dialog_english),
                getResources().getString(R.string.lang_dialog_french),
                getResources().getString(R.string.lang_dialog_arabic)
        };

        builder.setItems(lang, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), lang[which], Toast.LENGTH_SHORT).show();
                switch (which) {
                    case 0:
                        AppConfig.getInstance().setAppLanguage(getActivity(), "en");
                        break;

                    case 1:
                        AppConfig.getInstance().setAppLanguage(getActivity(), "fr");
                        break;

                    case 2:
                        AppConfig.getInstance().setAppLanguage(getActivity(), "ar");
                        break;

                    default:
                        AppConfig.getInstance().setAppLanguage(getActivity(), "en"); // Default language
                        break;

                }
            }
        });


        AlertDialog dialog = builder.create();

        // display dialog
        ListView listView = dialog.getListView();

        listView.setDivider(new ColorDrawable(Color.GRAY));

        listView.setDividerHeight(1);

        dialog.show();
    }



}
