package ub.passwordmanager.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ub.passwordmanager.R;
import ub.passwordmanager.factories.FragmentFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // This field will contain the active fragment
    private Fragment activeFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the customized action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Add the even handler for the floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Add the action for creation a new account
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Add the navigation menu drawer to the view
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Add the listener for our navigation menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Get the default Fragment to show when the user log in or changed view
        if (savedInstanceState == null)
            activeFragment = FragmentFactory.getInstance().getFragment(-1);
        else
            activeFragment = getSupportFragmentManager()
                    .getFragment(savedInstanceState
                            , "activeFragment");

        // Allow to place the current fragment int the fragment container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.my_frame_container, activeFragment)
                .commit();

        // Change the stat of the activity
        changeActivityState(
                FragmentFactory
                        .getInstance()
                        .getIndexOf(activeFragment
                                .getClass()
                                .getSimpleName())
        );

    }


    /**
     * This function is derived from the "NavigationView.OnNavigationItemSelectedListener".
     * it allow us to add the listener for when an item is selected in the menu
     *
     * @param item : the selected item in the menu
     * @return True or false depending if we found the desired menu
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Get and switch to the chosen fragment
        switchFragment(FragmentFactory
                        .getInstance()
                        .getFragment(item.getItemId())
                , true);

        // Change the stat of the activity
        changeActivityState(
                FragmentFactory
                        .getInstance()
                        .getIndexOf(activeFragment
                                .getClass()
                                .getSimpleName())
        );

        // Show the selected item
        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

        return true;
    }

    /**
     * This function allow us to save the current State of the activity
     * In our case we save only the Active Fragment.
     * @param savedInstanceState : we save all our information in this bundle
     *                              se that our fragment can communicate.
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the Activity
        getSupportFragmentManager().putFragment(savedInstanceState, "activeFragment", activeFragment);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Redefine the @onBackPressed method
     * so that if the menu is open and we press the back button
     * we close the menu drawer, or in the other case we go back to the "Home" fragment
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ("HomePage".equals(activeFragment.getClass().getSimpleName()))
                // Call the original @BackPressed, to close all activities and fragments
                super.onBackPressed();
            else
                // When the Back button is pressed, we go back to the Home fragment
                switchFragment(FragmentFactory.getInstance().getFragment(-1), false);
        }
    }

    /**
     * Description
     * This function allow us to switch between the fragments
     *
     * @param toFragment  : the desired fragment
     * @param LeftToRight : Choose the direction of the animation
     */
    protected void switchFragment(Fragment toFragment, boolean LeftToRight) {
        if (activeFragment != toFragment) {
            // Insert the fragment by replacing any existing fragment
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations((LeftToRight) ? R.anim.trans_left_in : R.anim.trans_right_in,
                            (LeftToRight) ? R.anim.trans_left_out : R.anim.trans_right_out)
                    .hide(activeFragment)
                    .show(toFragment)
                    .replace(R.id.my_frame_container, toFragment)
                    .commit();
            activeFragment = toFragment;

            // Change the stat of the activity
            changeActivityState(
                    FragmentFactory
                            .getInstance()
                            .getIndexOf(activeFragment
                                    .getClass()
                                    .getSimpleName())
            );
        }
    }

    /**
     * When the selected menu changes, this function allow us to :
     * - Set the title;
     * - Select the item in the menu;
     * - Close the drawer;
     * - Show the floating button
     *
     * @param index : the index of the selected Menu Item|Fragment
     */
    private void changeActivityState(int index) {

        // Select the menu "Home" in the navigation menu
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem item = navigationView.getMenu().getItem(index);

        // Change the title of the action bar
        setTitle(item.getTitle());

        // Check the chosen menu item (for visibility)
        item.setChecked(true);

        // Close the Menu Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // set the visibility of the floating button
        FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);
    }


}
