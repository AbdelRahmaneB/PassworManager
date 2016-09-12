package ub.passwordmanager.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ub.passwordmanager.R;
import ub.passwordmanager.Services.Service_ImportExportDataBase;
import ub.passwordmanager.Services.Service_PwdAccount;
import ub.passwordmanager.views.SplashScreen;

/**
 * Adapter for the Import/Export view.
 * <p/>
 * Created by UB on 25/08/2016.
 */
public class ImportExportListAdapter extends BaseAdapter {

    // Our Data from the fragment
    private List<String> mTitles;
    private List<String> mDesc;


    // Get the Context and the layoutInflater from the Fragment
    private Context mContext;
    private LayoutInflater inflater = null;
    private static Activity mActivity;

    public ImportExportListAdapter(Activity context, List<String> titles, List<String> description) {
        this.mTitles = titles;
        this.mDesc = description;

        this.mContext = context;
        mActivity = context;

        this.inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // Create our DataHolder
            Holder dh = new Holder();

            // Inflate the our row layout for the Import/Export listView
            convertView = inflater.inflate(R.layout.adapter_row_ie_list, null);

            // Get our Objects from the view
            dh.dh_Title = (TextView) convertView.findViewById(R.id.l_ie_title);
            dh.dh_Desc = (TextView) convertView.findViewById(R.id.l_ie_desc);

            // Set the value to the objects
            dh.dh_Title.setText(mTitles.get(position));
            dh.dh_Desc.setText(mDesc.get(position));

            // Create a listener for the those objects
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // For The DataBase import
                    if (mTitles.get(position).equals(mContext.getResources().getString(R.string.ie_title2))) {
                        // Import DataBase
                       if( Service_ImportExportDataBase.importDataBase(mContext) ){
                           mActivity.finish();
                           mActivity.getFragmentManager().popBackStack();
                           mActivity.startActivity(new Intent(mContext, SplashScreen.class));
                       }
                    }

                    // For The Data import
                    if (mTitles.get(position).equals(mContext.getResources().getString(R.string.ie_title4))) {
                        // Import XML File
                        Service_ImportExportDataBase.importFromXml(mContext);
                    }


                    // For The DataBase Export
                    if (mTitles.get(position).equals(mContext.getResources().getString(R.string.ie_title1))) {
                        // Export DataBase
                        Service_ImportExportDataBase.exportDataBase(mContext);
                    }

                    // For The Data Export
                    if (mTitles.get(position).equals(mContext.getResources().getString(R.string.ie_title3))) {
                        // Export XML File
                        Service_ImportExportDataBase.exportToXml(mContext);
                    }

                }
            });
        }

        return convertView;
    }

    /**
     * This Holder class allow us to set the model for our listView Adapter
     */
    private class Holder {

        private TextView dh_Title;
        private TextView dh_Desc;

        Holder() {
            // Leave it empty
        }

    }
}
