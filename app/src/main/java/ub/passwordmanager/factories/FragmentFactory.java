package ub.passwordmanager.factories;


import android.support.v4.app.Fragment;

import java.util.HashMap;

import ub.passwordmanager.R;
import ub.passwordmanager.views.fragments.Registration.SignInPwdInfoFragment;
import ub.passwordmanager.views.fragments.Registration.SignInUserInfoFragment;
import ub.passwordmanager.views.fragments.mainActivities.AboutPage;
import ub.passwordmanager.views.fragments.mainActivities.HomePage;
import ub.passwordmanager.views.fragments.mainActivities.ImportExportPage;
import ub.passwordmanager.views.fragments.mainActivities.PasswordGeneratorPage;
import ub.passwordmanager.views.fragments.mainActivities.ProfilePage;
import ub.passwordmanager.views.fragments.mainActivities.SettingPage;

public class FragmentFactory {

    /**
     * Our Instance for the factory using Singleton
     */
    private static FragmentFactory INSTANCE = new FragmentFactory();

    /**
     * List to contain our used Fragments for optimisation purpose
     */
    private HashMap<String, Fragment> fragmentCache = new HashMap<>();


    private FragmentFactory() {
        // Required empty public constructor
    }

    /**
     * This method allow us to be sure that there will be only one instance of this class
     *
     * @return the instance of this class
     */
    public static FragmentFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (FragmentFactory.class) {
                INSTANCE = new FragmentFactory();
            }
        }
        return INSTANCE;
    }

    /**
     * This function allow us to test if the instance of a fragment already exist.
     * and get it's instance
     *
     * @param fragmentClass : used to get the information about the fragment
     * @return Fragment : an instance of the requested fragment
     */
    private Fragment getFragment(Class fragmentClass) {
        String fragmentName = fragmentClass.toString();
        if (!fragmentCache.containsKey(fragmentName)) {
            try {
                fragmentCache.put(fragmentName, (Fragment) fragmentClass.newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return fragmentCache.get(fragmentClass.toString());
    }


    /**
     * Get the requested fragment.
     * All the available fragment that are shown in the menu drawer
     *
     * @param itemId : int value for the selected menu item
     * @return Fragment : instance of the requested fragment
     */
    public Fragment getFragment(int itemId) {
        Class fragmentClass;

        // get the requested fragment class
        switch (itemId) {
            case R.id.nav_home:
                fragmentClass = HomePage.class;
                break;

            case R.id.nav_profile:
                fragmentClass = ProfilePage.class;
                break;

            case R.id.nav_import_export:
                fragmentClass = ImportExportPage.class;
                break;

            case R.id.nav_password_generator:
                fragmentClass = PasswordGeneratorPage.class;
                break;

            case R.id.nav_settings:
                fragmentClass = SettingPage.class;
                break;

            case R.id.nav_About:
                fragmentClass = AboutPage.class;
                break;

            default:
                fragmentClass = HomePage.class;
        }
        return getFragment(fragmentClass);

    }

    /**
     * This Function allow the user to get the index
     * in the menu navigator of a specific fragment
     * Since we know the order of the menu we return
     * the index of the menu as seen in the navigator
     * we start from 0 to 5 - a total of 6 items
     *
     * @param fragmentName : the target fragment
     * @return the index of the target fragment
     */
    public int getIndexOf(String fragmentName) {

        int temp;
        switch (fragmentName) {
            case "HomePage":
                temp = 0;
                break;

            case "ProfilePage":
                temp = 1;
                break;

            case "ImportExportPage":
                temp = 2;
                break;

            case "PasswordGeneratorPage":
                temp = 3;
                break;

            case "SettingPage":
                temp = 4;
                break;

            case "AboutPage":
                temp = 5;
                break;

            default:
                temp = 0;
        }
        return temp;
    }


    /**
     * The Getters & Setters in the of the fragments
     */
    // -------------------------------------------------
    public Fragment getSignInUserInfoFragment() {
        return getFragment(SignInUserInfoFragment.class);
    }

    public Fragment getSignInPwdInfoFragment() {
        return getFragment(SignInPwdInfoFragment.class);
    }



}
