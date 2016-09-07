package ub.passwordmanager.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This role of this class is to interact with the DataBase for :
 * - Adding Data.
 * - Modifying Data.
 * - Deleting Data.
 * - Extracting Data.
 * <p>
 * Created by UB on 07/09/2016.
 */
public abstract class DataBaseActions {

    //Logger Key fo this class
    private static final String KEY_LOG = "DBActions-";

    /**
     * This function handles the persistence of new data into the DataBase.
     *
     * @param context   : The Application context where the function is called.
     * @param tableName : The desired Table name.
     * @param columns   : List of the columns of the desired Table.
     * @param values    : List of the values for the desired Table.
     * @return : True if the everything went fine, False otherwise.
     */
    public static Boolean newData(Context context, String tableName, String[] columns, String[] values) {
        try {
            // Initialise the DataBase Helper
            DB_Helper dbh = new DB_Helper(context);

            // Get The database - Writing mode
            SQLiteDatabase db = dbh.getWritableDatabase();

            // Initialise the Query Adapter
            ContentValues cv = new ContentValues();

            // Get the data and insert it into the query Adapter
            for (int i = 0; i < columns.length; i++){
                cv.put(columns[i], values[i]);
            }

            // insert the values into the DataBase
            db.insert(tableName, null, cv);

            // Close the DataBase
            db.close();

            // Show a message in the log
            Log.e(KEY_LOG + "newDataError:", "Data added correctly !!");

            return true;
        } catch (Exception ex) {
            Log.e(KEY_LOG + "newDataError:", "[" + ex.getMessage() + "]");
            return false;
        }
    }


    /**
     * This function handles the persistence of modified data into the DataBase.
     *
     * @param context   : The Application context where the function is called.
     * @param tableName : The desired Table name.
     * @param columns   : List of the columns of the desired Table.
     * @param values    : List of the new values for the desired Table.
     * @return : True if the everything went fine, False otherwise.
     */
    public static Boolean editData(Context context, String tableName, String[] columns, String[] values) {
        try {
            // Initialise the DataBase Helper
            DB_Helper dbh = new DB_Helper(context);

            // Get The database - Writing mode
            SQLiteDatabase db = dbh.getWritableDatabase();

            // Initialise the Query Adapter
            ContentValues cv = new ContentValues();

            // Get the data and insert it into the query Adapter
            for (int i = 1; i < columns.length; i++){
                cv.put(columns[i], values[i]);
            }

            // The ID of the Data to Modify
            String[] oldValues = new String[] { String.valueOf(values[0]) };

            // Update the values in DataBase
            db.update(tableName, cv, "ID = ?", oldValues);

            // Close the DataBase
            db.close();

            // Show a message in the log
            Log.e(KEY_LOG + "EditData:", "Data modifications saved correctly !!");
            return true;
        } catch (Exception ex) {
            Log.e(KEY_LOG + "EdiDataError:", "[" + ex.getMessage() + "]");
            return false;
        }
    }

    /**
     * This function handles the persistence of Deleted data from the DataBase.
     *
     * @param context   : The Application context where the function is called.
     * @param tableName : The desired Table name.
     * @param id        : The ID of the desired Data in the Table.
     * @return : True if the everything went fine, False otherwise.
     */
    public static Boolean deleteData(Context context, String tableName, int id) {
        try {
            // Initialise the DataBase Helper
            DB_Helper dbh = new DB_Helper(context);

            // Get The database - Writing mode
            SQLiteDatabase db = dbh.getWritableDatabase();

            // The ID of the Data to Modify
            String[] selectedValues = new String[] { String.valueOf(id) };

            // Update the values in DataBase
            db.delete(tableName, "ID = ?", selectedValues);

            // Close the DataBase
            db.close();
            // Show a message in the log
            Log.e(KEY_LOG + "DeleteData:", "Data Deleted correctly !!");
            return true;
        } catch (Exception ex) {
            Log.e(KEY_LOG + "De_DataError:", "[" + ex.getMessage() + "]");
            return false;
        }
    }


}
