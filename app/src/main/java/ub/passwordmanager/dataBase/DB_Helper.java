package ub.passwordmanager.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

import ub.passwordmanager.dataBase.DB_TablesInformation.*;

/**
 * This class offer a multitude of features to manipulate the Database.
 * Also this class extend from the {@link SQLiteOpenHelper} class.
 * <p/>
 * {@link #DB_Helper(Context)} : Constructor for the class to initialise the DataBase.
 * {@link #onCreate(SQLiteDatabase)} : Function to create the tables in the DataBase.
 * {@link #onUpgrade(SQLiteDatabase, int, int)} : Function to update the DataBase.
 * {@link #dropDataBase(SQLiteDatabase)} : Function to delete the DataBase.
 * {@link #dropDataBaseTables(SQLiteDatabase)} : Function to delete the DataBase Tables
 * Created by UB on 07/09/2016.
 */
public class DB_Helper extends SQLiteOpenHelper {

    // This static fields used to identify the DataBase and the version
    private static final String KEY_DATABASE_NAME = "DBManager.db";
    private static final int KEY_DATABASE_VERSION = 1;
    private static final String LOG_KEY = "DBHelper - ";


    // Default constructor where we initialise the DataBase Name and Version.
    public DB_Helper(Context context) {
        super(context, KEY_DATABASE_NAME, null, KEY_DATABASE_VERSION);
    }

    /**
     * This Function allow to create the Tables for the selected DataBase.
     *
     * @param dataBase : The selected DataBase.
     */
    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        try {
            // Using the defined query for creation the Tables "KEY_CREATE_TABLE"
            dataBase.execSQL(DB_UserAccountTable.KEY_CREATE_TABLE);
            dataBase.execSQL(DB_PwdAccountTable.KEY_CREATE_TABLE);

            // Print a message in the console to show the state
            Log.i(LOG_KEY + "onCreate :", "Tables created correctly.");
        } catch (Exception ex) {
            Log.e(LOG_KEY + "onCre Error:", "[" + ex.getMessage() + "]");
        }
    }

    /**
     * In this Function, if there is a change in the DataBase
     * we apply the new changes here.
     * Uses the {@link #onCreate(SQLiteDatabase)} function.
     *
     * @param dataBase   : The selected DataBase.
     * @param oldVersion : The version of the old DataBase.
     * @param newVersion : The version of the new DataBase.
     */
    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion, int newVersion) {
        try {
            // Delete the tables
            this.dropDataBaseTables(dataBase);

            // Create the tables from the beginning
            this.onCreate(dataBase);

            // Print a message in the console to show the state
            Log.i(LOG_KEY + "onUpgrade :", "DataBase updated correctly !!");
        } catch (Exception ex) {
            Log.e(LOG_KEY + "onUpG Error:", "[" + ex.getMessage() + "]");
        }
    }


    /**
     * This function allow us to Delete the DataBase.
     *
     * @param dataBase : The selected DataBase.
     */
    public void dropDataBase(SQLiteDatabase dataBase) {
        try {
            // Get the path of the database
            File f = new File(dataBase.getPath());

            // Delete the DataBase file
            f.delete();

            // Print a message in the console to show the state
            Log.i(LOG_KEY + "dropDataBase", "DataBase deleted correctly !!");
        } catch (Exception e) {
            Log.e(LOG_KEY + "DroDB ERROR:", "[" + e.getMessage() + "]");
        }
    }

    /**
     * This function allow us to Delete the Tables of the selected DataBase.
     *
     * @param dataBase : The selected DataBase
     */
    public void dropDataBaseTables(SQLiteDatabase dataBase) throws Exception {
        // Using the defined query for Deleting the Tables "KEY_DELETE_TABLE"
        dataBase.execSQL(DB_UserAccountTable.KEY_DELETE_TABLE);
        dataBase.execSQL(DB_PwdAccountTable.KEY_DELETE_TABLE);
    }

    /**
     * Function that returns the path DataBase
     *
     * @param dbh : instance of DB_helper.
     * @return the path of the DataBase.
     */
    public static String GetDataBasePath(DB_Helper dbh) {
        return dbh.getWritableDatabase().getPath();
    }
}
