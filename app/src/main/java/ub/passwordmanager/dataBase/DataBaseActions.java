package ub.passwordmanager.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.dataBase.DB_TablesInformation.DB_PwdAccountTable;
import ub.passwordmanager.dataBase.DB_TablesInformation.DB_UserAccountTable;

/**
 * This role of this class is to interact with the DataBase for :
 * - Adding Data.
 * - Modifying Data.
 * - Deleting Data.
 * - Extracting Data.
 * <p/>
 * Created by UcefBen on 07/09/2016.
 */
public class DataBaseActions {

    //Logger Key fo this class
    private static final String KEY_LOG = "DBActions-";

    // the class instance
    private static DataBaseActions INSTANCE;

    private DataBaseActions() {
        // Do nothing
    }

    /**
     * This method allow us to be sure that there will be only one instance of this class
     *
     * @return the instance of this class
     */
    public static DataBaseActions getInstance() {
        if (INSTANCE == null) {
            synchronized (DataBaseActions.class) {
                INSTANCE = new DataBaseActions();
            }
        }
        return INSTANCE;
    }

    /**
     * This function handles the persistence of new data into the DataBase.
     *
     * @param context   : The Application context where the function is called.
     * @param tableName : The desired Table name.
     * @param columns   : List of the columns of the desired Table.
     * @param values    : List of the values for the desired Table.
     * @return : True if the everything went fine, False otherwise.
     */
    @SuppressWarnings("ConstantConditions")
    public Boolean newData(Context context, String tableName, List<String> columns, List<String> values) {
        SQLiteDatabase db = null;
        try {
            // Get the DataBase
            db = getDataBase(context);

            // Initialise the Query Adapter
            ContentValues cv = new ContentValues();

            // Get the data and insert it into the query Adapter
            for (int i = 0; i < columns.size() - 1; i++) {
                cv.put(columns.get(i + 1), values.get(i));
            }

            // insert the values into the DataBase
            db.insert(tableName, null, cv);

            // Close the DataBase
            db.close();

            // Show a message in the log
            Log.i(KEY_LOG + "newData:", "Data added correctly !!");

            return true;
        } catch (Exception ex) {
            Log.e(KEY_LOG + "newDataError:", "[" + ex.getMessage() + "]");
            db.close();
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
    @SuppressWarnings("ConstantConditions")
    public Boolean editData(Context context, String tableName, List<String> columns, List<String> values) {
        SQLiteDatabase db = null;
        try {
            // Get the DataBase
            db = getDataBase(context);

            // Initialise the Query Adapter
            ContentValues cv = new ContentValues();

            // Get the data and insert it into the query Adapter
            for (int i = 1; i < columns.size(); i++) {
                cv.put(columns.get(i), values.get(i));
            }

            // The ID of the Data to Modify
            String[] oldValues = new String[]{String.valueOf(values.get(0))};

            // Update the values in DataBase
            db.update(tableName, cv, "Id = ?", oldValues);

            // Close the DataBase
            db.close();

            // Show a message in the log
            Log.i(KEY_LOG + "EditData:", "Data modifications saved correctly !!");
            return true;
        } catch (Exception ex) {
            Log.e(KEY_LOG + "EdiDataError:", "[" + ex.getMessage() + "]");
            db.close();
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
    @SuppressWarnings("ConstantConditions")
    public Boolean deleteData(Context context, String tableName, int id) {
        SQLiteDatabase db = null;
        try {
            // Get the DataBase
            db = getDataBase(context);

            // The ID of the Data to Modify
            String[] selectedValues = new String[]{String.valueOf(id)};

            // Update the values in DataBase
            db.delete(tableName, "Id = ?", selectedValues);

            // Close the DataBase
            db.close();

            // Show a message in the log
            Log.i(KEY_LOG + "DeleteData:", "Data Deleted correctly !!");
            return true;
        } catch (Exception ex) {
            Log.e(KEY_LOG + "De_DataError:", "[" + ex.getMessage() + "]");
            db.close();
            return false;
        }
    }

    /**
     * The role of this function is to get the Data depending on a custom query.
     *
     * @param context   : The Application context where the function is called.
     * @param tableName : The desired Table name.
     * @param columns   : List of the columns needed for the query.
     * @param values    : List of the new values needed for the query.
     * @return an object and can return "null" if the object not found
     */
    @SuppressWarnings("ConstantConditions")
    public Object getAccount(Context context, String tableName, List<String> columns, List<String> values) {
        SQLiteDatabase db = null;
        Cursor mCursor = null;
        try {
            // Get the DataBase
            db = getDataBase(context);

            // Set the string query and Create a cursor to fetch all the Data from te query
            mCursor = db.rawQuery(buildQueryString(tableName, columns, values).toString(), null);
            Object mTempObject = null;

            // Get the data from the cursor
            if (mCursor != null && mCursor.moveToFirst())
                mTempObject = fillTheObject(mCursor, tableName);


            // Close the cursor
            mCursor.close();

            // Close the DataBase
            db.close();

            // Show a message in the log
            Log.e(KEY_LOG + "GetAccount :", "Data extraction done successfully !!");
            return mTempObject; // Return the object
        } catch (Exception ex) {
            Log.e(KEY_LOG + "GetAccError:", "[" + ex.getMessage() + "]");
            mCursor.close();
            db.close();
            return null;
        }
    }

    /**
     * The role of this function is to get all the object in the specified Table
     *
     * @param context   : The Application context where the function is called.
     * @param tableName : The desired Table name.
     * @return a list of object from the selected Table.
     */
    @SuppressWarnings("ConstantConditions")
    public List<Object> getAllAccounts(Context context, String tableName) {
        SQLiteDatabase db = null;
        Cursor mCursor = null;
        try {
            // Get the DataBase
            db = getDataBase(context);

            // Set the string query and Create a cursor to fetch all the Data from te query
            if (DB_UserAccountTable.KEY_TABLE_NAME.equals(tableName))
                mCursor = db.rawQuery(buildQueryString(tableName, DB_UserAccountTable.KEY_LAST_CONNECTION), null);
            else
                mCursor = db.rawQuery(buildQueryString(tableName, DB_PwdAccountTable.KEY_LAST_UPDATE), null);

            List<Object> mTempObject = new ArrayList<>();

            // Get the data from the cursor
            if (mCursor.moveToFirst()) {
                do {
                    mTempObject.add(fillTheObject(mCursor, tableName));
                } while (mCursor.moveToNext());
            }

            // Close the cursor
            mCursor.close();

            // Close the DataBase
            db.close();

            // Show a message in the log
            Log.i(KEY_LOG + "GetAccounts :", "Data extraction done successfully !!");
            return mTempObject; // Return the object
        } catch (Exception ex) {
            Log.e(KEY_LOG + "GetAccError:", "[" + ex.getMessage() + "]");
            ex.printStackTrace();
            mCursor.close();
            db.close();
            return null;
        }
    }


    /* ************************** Private Methods *******************************/
    //---------------------------------------------------------------------------/


    /**
     * This Function allow us to get the DataBase Instance.
     * we create this function to avoid the reuse of those 2 lines.
     *
     * @param context : The application context.
     * @return an instance of SQLiteDatabase.
     */
    private SQLiteDatabase getDataBase(Context context) {
        // Initialise the DataBase Helper
        DB_Helper dbh = new DB_Helper(context);

        // Get The database - Writing mode
        return dbh.getWritableDatabase();
    }

    /**
     * The purpose of this function is to create a custom query
     * to get Data from the DataBase.
     *
     * @param tableName : The table from which we want to extract the data.
     * @param columns   : the columns to use in this query.
     * @param values    : the values to filter data in this query.
     * @return the customise query.
     */
    @SuppressWarnings("ConstantConditions")
    private StringBuilder buildQueryString(String tableName, List<String> columns,
                                           List<String> values) {

        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
        int counter = (columns.size() - 1);  // Number of line in the table

        for (int i = 0; i < counter; i++) {
            query.append(columns.get(i));
            query.append("=");

            // Check if the value stored is int value or String value to add the "'"
            if (values.get(i).matches("\\d+")) {
                query.append(values.get(i)); // All the numbers
            } else {
                query.append("'");
                query.append(values.get(i));
                query.append("'");
            }

            // Check if there is more
            if (counter > 1) {
                query.append(" AND ");
            }

        }

        return query;
    }


    /**
     * this function is the same as {@link #buildQueryString(String, List, List)}
     * the only difference is that this one is used with no parameters
     *
     * @param tableName : The table from which we want to extract the data.
     * @return the customise query
     */
    private String buildQueryString(String tableName, String dateColumnName) {
        return ("SELECT * FROM " + tableName + " order  by " + dateColumnName + " DESC");
    }


    /**
     * Choose the model class we will use to fill the object.
     *
     * @param cursor    : Cursor that contain the extracted data.
     * @param tableName : The table name to define which class to use.
     * @return the filled object.
     * @throws ParseException : to catch the error if there is a parsing error.
     */
    private Object fillTheObject(Cursor cursor, String tableName) throws ParseException {

        switch (tableName) {
            case "UserAccount":
                // Return the object
                return getUserObject(cursor);

            case "PwdAccount":
                // Return the object
                return getPwdObject(cursor);

            default:
                // Nothing to do here
                return null;
        }
    }

    /**
     * Function to fill the UserAccount Object
     *
     * @param cursor : Cursor that contain the extracted data.
     * @return UserAccount object filled with data.
     * @throws ParseException : to catch the error if there is a parsing error.
     */
    private UserAccountModel getUserObject(Cursor cursor) throws ParseException {
        return new UserAccountModel(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
        );
    }


    /**
     * Function to fill the PwdAccount Object
     *
     * @param cursor : Cursor that contain the extracted data.
     * @return PwdAccount object filled with data.
     * @throws ParseException : to catch the error if there is a parsing error.
     */
    private PwdAccountModel getPwdObject(Cursor cursor) throws ParseException {
        return new PwdAccountModel(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                (cursor.getString(4) == null) ? "" : cursor.getString(4),
                cursor.getString(5)
        );


    }


}
