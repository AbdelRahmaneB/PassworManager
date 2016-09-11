package ub.passwordmanager.dataBase;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * This class contain two abstract static classes,
 * that holds the tables information (ColumnName, Table creation query)
 * <p/>
 * The Existing classes are {@link DB_UserAccountTable} and {@link DB_PwdAccountTable}
 * Created by UB on 06/09/2016.
 */
public class DB_TablesInformation {

    /**
     * This class is used to hold the information about the
     * User Account Table in the DataBase.
     */
    abstract static public class DB_UserAccountTable {

        // All the needed columns for the user account table
        public static final String KEY_TABLE_NAME = "UserAccount";
        public static final String KEY_ID = "Id";
        public static final String KEY_REF = "Ref";
        public static final String KEY_USERNAME = "Username";
        public static final String KEY_EMAIL = "Email";
        public static final String KEY_PASSWORD = "Password";
        public static final String KEY_LAST_CONNECTION = "LastConnection";


        // List containing all the columns of our Table, may be used in
        //    the Persistence class, so that we ca unify the CRUD methods
        public static final List<String> KEY_AlL_COLUMNS = Arrays.asList(
                                            KEY_ID,
                                            KEY_REF,
                                            KEY_USERNAME,
                                            KEY_EMAIL,
                                            KEY_PASSWORD,
                                            KEY_LAST_CONNECTION
                                        );

        // This field contain the query to create the User Account Table.
        public static final String KEY_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + KEY_TABLE_NAME + " ( " +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_REF + " VARCHAR NOT NULL, " +
                        KEY_USERNAME + " VARCHAR NOT NULL, " +
                        KEY_EMAIL + " VARCHAR NOT NULL, " +
                        KEY_PASSWORD + " VARCHAR NOT NULL, " +
                        KEY_LAST_CONNECTION + " VARCHAR NOT NULL )";

        // This filed contain the query to Delete the current Table.
        public static final String KEY_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + KEY_TABLE_NAME;

    }

    //-------------------------------------------------------------------------------------------

    /**
     * This class is used to hold the information about the
     * Password Account Table in the DataBase.
     */
    abstract static public class DB_PwdAccountTable {

        // All the needed columns for the user account table
        public static final String KEY_TABLE_NAME = "PwdAccount";
        public static final String KEY_ID = "Id";
        public static final String KEY_WEB_SITE = "WebSite";
        public static final String KEY_USERNAME = "Username";
        public static final String KEY_PASSWORD = "Password";
        public static final String KEY_OTHER_INFO = "OtherInfo";
        public static final String KEY_LAST_UPDATE = "LastUpdate";


        // List containing all the columns of our Table, may be used in
        //    the Persistence class, so that we ca unify the CRUD methods
        public static final List<String> KEY_AlL_COLUMNS = Arrays.asList(
                                            KEY_ID,
                                            KEY_WEB_SITE,
                                            KEY_USERNAME,
                                            KEY_PASSWORD,
                                            KEY_OTHER_INFO,
                                            KEY_LAST_UPDATE
                                        );

        // This field contain the query to create the Password Account Table.
        public static final String KEY_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + KEY_TABLE_NAME + " ( " +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_WEB_SITE + " VARCHAR NOT NULL, " +
                        KEY_USERNAME + " VARCHAR NOT NULL, " +
                        KEY_PASSWORD + " VARCHAR NOT NULL, " +
                        KEY_OTHER_INFO + " VARCHAR, " +
                        KEY_LAST_UPDATE + " VARCHAR NOT NULL )";

        // This filed contain the query to Delete the current Table.
        public static final String KEY_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + KEY_TABLE_NAME;

    }
}
