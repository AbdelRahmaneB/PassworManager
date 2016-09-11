package ub.passwordmanager.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;

/**
 * Class Description
 * Created by UB on 24/08/2016.
 */
public class PwdGenListAdapter extends BaseAdapter {

    private List<String> mTitles;
    private List<String> mDesc;
    private List<Boolean> mCb;
    private static List<CheckBox> mCheckBoxHolder;

    private Context context;
    private static LayoutInflater inflater = null;
    private static Holder holder;
    private static Activity mMainActivity;

    public PwdGenListAdapter(Activity mainActivity, List<String> titles, List<String> desc,
                             List<Boolean> cb) {
        mTitles = titles;
        mDesc = desc;
        mCb = cb;

        context = mainActivity;
        mMainActivity = mainActivity;

        PwdGenListAdapter.mCheckBoxHolder = new ArrayList<>();

        inflater = (LayoutInflater) context.
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
            holder = new Holder();
            convertView = inflater.inflate(R.layout.adapter_row_option_list, null);

            holder.title = (TextView) convertView.findViewById(R.id.l_opt_title);
            holder.desc = (TextView) convertView.findViewById(R.id.l_opt_sub_item);
            holder.cb = (CheckBox) convertView.findViewById(R.id.l_opt_check);

            holder.title.setText(mTitles.get(position));
            holder.desc.setText(mDesc.get(position));
            holder.cb.setChecked(mCb.get(position));
            holder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCb.get(position)){
                        mCb.set(position, false);
                    }else{
                        mCb.set(position, true);
                    }

                    // Save the change into the Preferences file
                    changeCheckBoxState(position);
                }
            });
            PwdGenListAdapter.mCheckBoxHolder.add(holder.cb);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (PwdGenListAdapter.mCheckBoxHolder.get(position).isChecked()) { // true = UnCheck
                        mCb.set(position, false);
                        PwdGenListAdapter.mCheckBoxHolder.get(position).setChecked(false);
                    } else { // false = check
                        mCb.set(position, true);
                        PwdGenListAdapter.mCheckBoxHolder.get(position).setChecked(true);
                    }


                    // Save the change into the Preferences file
                    changeCheckBoxState(position);
                }
            });

        }
        return convertView;

    }

    /**
     * Save the change into the Preferences file
     */
    private void changeCheckBoxState(int position) {

        // Save the changes
        switch (position) {
            case 0:
                // Lower Case
                AppConfig.getInstance().saveValueToPreference(
                        mMainActivity,
                        AppConfig.KEY_PREF_BOOLEAN,
                        AppConfig.KEY_PREF_LOWER_CASE,
                        mCb.get(position)
                );
                break;

            case 1:
                // Upper Case
                AppConfig.getInstance().saveValueToPreference(
                        mMainActivity,
                        AppConfig.KEY_PREF_BOOLEAN,
                        AppConfig.KEY_PREF_UPPER_CASE,
                        mCb.get(position)
                );
                break;

            case 2:
                // Symbols
                AppConfig.getInstance().saveValueToPreference(
                        mMainActivity,
                        AppConfig.KEY_PREF_BOOLEAN,
                        AppConfig.KEY_PREF_USE_SYMBOLS,
                        mCb.get(position)
                );
                break;


            case 3:
                // Numbers
                AppConfig.getInstance().saveValueToPreference(
                        mMainActivity,
                        AppConfig.KEY_PREF_BOOLEAN,
                        AppConfig.KEY_PREF_USE_NUMBERS,
                        mCb.get(position)
                );
                break;
        }

    }


    /**
     * This Holder class allow us to set the model for our listView Adapter
     */
    private class Holder {

        private TextView title;
        private TextView desc;
        private CheckBox cb;

        Holder() {
            // leave it empty
        }
    }
}
