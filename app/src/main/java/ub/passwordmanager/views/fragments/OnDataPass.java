package ub.passwordmanager.views.fragments;

/** Description :
 * This interface will be implemented by
 *  the activity that implement this fragment.
 * It role is to define a method that will
 *  link between the fragments and the activity
 *
 *  Created by UB on 28/08/2016.
 */
public interface OnDataPass {

    // In this method we can retrieve the data passed by the fragment to the activity
    void onDataPass(Object data);
}
