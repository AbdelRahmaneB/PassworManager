package ub.passwordmanager.activities;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ub.passwordmanager.R;
import ub.passwordmanager.factories.FragmentFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // This field will contain the active fragment
    private Fragment activeFragment = null;

    // This field will contain the previous fragment


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

        // Get the default Fragment to show when the user log in
        activeFragment = FragmentFactory.getInstance().getFragment(-1);

        // Allow to place the current fragment int the fragment container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.my_frame_container, activeFragment)
                .commit();

        // Change the title of the activity
        setTitle("Home");

        // Select the menu "Home" in the navigation menu
        navigationView.getMenu().getItem(0).setChecked(true);

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
        switchFragment(FragmentFactory.getInstance().getFragment(item.getItemId()), true);

        // Change the title of the action bar
        setTitle(item.getTitle());

        // Check the chosen menu item (for visibility)
        item.setChecked(true);

        // Show the selected item
        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();

        // Close the Menu Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    /**
     * Redefine the @onBackPressed method
     * so that if the menu is open and we press the back button
     * we close the menu drawer, or in the other case we go back to the previous fragment
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            // Get back to the last fragment
            super.onBackPressed();

            // Test if there is still Fragments in the BackStack
            if ( getFragmentManager().getBackStackEntryCount() >= 0) {

                // Get the current fragment
                Fragment current = getSupportFragmentManager().findFragmentById(R.id.my_frame_container);

                // Affect the current Fragment to the active one
                if (!current.equals(activeFragment)) {
                    activeFragment = current;
                }

                int index = FragmentFactory.getInstance().getIndexOf(activeFragment.getClass().getSimpleName());

                // Select the menu "Home" in the navigation menu
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.getMenu().getItem(index).setChecked(true);

                // Change the title
                setTitle(navigationView.getMenu().getItem(index).getTitle().toString());

                // set the visibility of the floating button
                FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab);
                mFab.setVisibility(View.VISIBLE);
            }
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
                    .addToBackStack(null)
                    .commit();
            activeFragment = toFragment;

            // set the visibility of the floating button
            FloatingActionButton mFab = (FloatingActionButton) findViewById(R.id.fab);
            mFab.setVisibility(View.VISIBLE);
        }
    }


}
