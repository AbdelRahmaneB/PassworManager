package ub.passwordmanager.factories;


import android.support.v4.app.Fragment;

import java.util.HashMap;

import ub.passwordmanager.fragments.Connection.SignIn.SignInPwdInfoFragment;
import ub.passwordmanager.fragments.Connection.SignIn.SignInUserInfoFragment;

public class FragmentFactory {

    // Our Instance for the factory using Singleton
    private static FragmentFactory INSTANCE = new FragmentFactory();

    // This list will contain our used Fragments for optimisation purpose
    private HashMap<String, Fragment> fragmentCache = new HashMap<>();


    private FragmentFactory() {
    }

    /**
     * This method allow us to be sur that there will be only one instance of this class     *
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
     * Get the requested fragment
     *
     * @param itemId : int value for the selected menu item
     * @return Fragment : instance of the requested fragment
     */
    public Fragment getFragment(int itemId) {
        Class fragmentClass;

        // get the requested fragment class
        switch (itemId) {
            // TO DO : when adding the Menu complete this section

            default:
                //fragmentClass = SignInMainFragment.class;
        }
        return null;//getFragment(fragmentClass);
    }

    public Fragment getSignInUserInfoFragment() {
        return getFragment(SignInUserInfoFragment.class);
    }

    public Fragment getSignInPwdInfoFragment() {
        return getFragment(SignInPwdInfoFragment.class);
    }


}
