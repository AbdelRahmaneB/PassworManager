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
 * Description :
 * This class represent the adapter for the "Password Generator Fragment".
 * it contain the data holder and Listeners.
 * <p>
 * Created by UcefBen on 24/08/2016.
 */
public class PwdGenListAdapter extends BaseAdapter {

    private List<String> mTitles;
    private List<String> mDesc;
    private List<Boolean> mCb;
    private static List<CheckBox> mCheckBoxHolder;

    private static LayoutInflater inflater = null;
    private static Activity mMainActivity;

    public PwdGenListAdapter(Activity mainActivity, List<String> titles, List<String> desc,
                             List<Boolean> cb) {
        // Init the fields
        mTitles = titles;
        mDesc = desc;
        mCb = cb;

        // Init the activity field
        mMainActivity = mainActivity;

        // Init the CheckBoxHolder
        PwdGenListAdapter.mCheckBoxHolder = new ArrayList<>();

        // Inflate the layout adapter
        inflater = (LayoutInflater) mainActivity.
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
            // Init the view
            Holder holder;
            holder = new Holder();
            convertView = inflater.inflate(R.layout.adapter_row_option_list, null);

            // Init the DataHolder fields
            holder.title = (TextView) convertView.findViewById(R.id.l_opt_title);
            holder.desc = (TextView) convertView.findViewById(R.id.l_opt_sub_item);
            holder.cb = (CheckBox) convertView.findViewById(R.id.l_opt_check);

            // fill the fields with corresponding data
            holder.title.setText(mTitles.get(position));
            holder.desc.setText(mDesc.get(position));
            holder.cb.setChecked(mCb.get(position));

            // set the onClickListener for the CheckBox's
            holder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCb.get(position)) {
                        mCb.set(position, false);
                    } else {
                        mCb.set(position, true);
                    }

                    // Save the change into the Preferences file
                    changeCheckBoxState(position);
                }
            });
            PwdGenListAdapter.mCheckBoxHolder.add(holder.cb);

            // set the onClickListener for when the line is selected
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
     *      - Position 0 : Save the choice of the Lowercase in the preferences file
     *      - Position 1 : Save the choice of the Uppercase in the preferences file
     *      - Position 2 : Save the choice of the Symbols in the preferences file
     *      - Position 3 : Save the choice of the Numbers in the preferences file
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
