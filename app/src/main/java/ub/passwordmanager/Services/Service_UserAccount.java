package ub.passwordmanager.Services;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ub.passwordmanager.Models.UserAccountModel;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.dataBase.DB_TablesInformation.DB_UserAccountTable;
import ub.passwordmanager.dataBase.DataBaseActions;
import ub.passwordmanager.tools.dataEncryption.DataEncryption;

/**
 * The role of this class is to handle the communication with The DataBase and the views
 * Created by UB on 10/09/2016.
 */
public class Service_UserAccount {

    // Instance of the class
    private static Service_UserAccount INSTANCE;

    private Service_UserAccount() {
        // Do nothing
    }

    /**
     * This method allow us to be sure that there will be only one instance of this class
     *
     * @return the instance of this class
     */
    public static Service_UserAccount getInstance() {
        if (INSTANCE == null) {
            synchronized (Service_UserAccount.class) {
                INSTANCE = new Service_UserAccount();
            }
        }
        return INSTANCE;
    }


    /**
     * This function Calls the persistence of new data into the DataBase.
     *
     * @param context     : The Application context where the function is called.
     * @param userAccount : the user account object with all the data from the view.
     * @return true if everything correct, or false otherwise.
     */
    public Boolean saveNewData(Context context, UserAccountModel userAccount) throws Exception {

        // Encrypt all the Data before sending it to the DataBase
        mEncryptValues(
                null,
                null,
                userAccount);

        // Set the date of last connexion
        userAccount.setLastLogIn(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()));
        userAccount.setId(-1); // No Id

        // Create the list of values to send to the DataBase
        List<String> values = Arrays.asList(
                userAccount.getRef(),
                userAccount.getUsername(),
                userAccount.getEmail(),
                userAccount.getPassword(),
                userAccount.getLastLogIn()
        );

        // Send the information to the DataBase
        return DataBaseActions.getInstance().newData(
                context,
                DB_UserAccountTable.KEY_TABLE_NAME,
                DB_UserAccountTable.KEY_AlL_COLUMNS,
                values
        );
    }


    /**
     * This function Calls the persistence of edit data into the DataBase.
     *
     * @param context     : The Application context where the function is called.
     * @param userAccount : the user account object with all the data from the view.
     * @return true if everything correct, or false otherwise.
     */
    public Boolean saveModifiedData(Context context, String oldPwd, UserAccountModel userAccount, Boolean reEncryptTheData) throws Exception {

        if (reEncryptTheData) {

            Service_PwdAccount.getInstance().reEncryptData(context, oldPwd);
        }

        // Encrypt all the Data before sending it to the DataBase
        mEncryptValues(
                null,
                null,
                userAccount);

        // Set the date of last connexion
        userAccount.setLastLogIn(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()));

        // Create the list of values to send to the DataBase
        List<String> values = Arrays.asList(
                Integer.toString(userAccount.getId()),
                userAccount.getRef(),
                userAccount.getUsername(),
                userAccount.getEmail(),
                userAccount.getPassword(),
                userAccount.getLastLogIn()
        );


        // Send the information to the DataBase
        return DataBaseActions.getInstance().editData(
                context,
                DB_UserAccountTable.KEY_TABLE_NAME,
                DB_UserAccountTable.KEY_AlL_COLUMNS,
                values
        );
    }


    /**
     * This function Calls the persistence of delete data from the DataBase.
     * Mostly only the 'Id' is required.
     *
     * @param context     : The Application context where the function is called.
     * @param userAccount : the user account object with all the data from the view.
     * @return true if everything correct, or false otherwise.
     */
    public Boolean deleteData(Context context, UserAccountModel userAccount) {
        // Send the information to the DataBase.
        return DataBaseActions.getInstance().deleteData(
                context,
                DB_UserAccountTable.KEY_TABLE_NAME,
                userAccount.getId()
        );
    }


    /**
     * This function is used to get all the User Account rows from the DataBase.
     *
     * @param context : The Application context where the function is called.
     * @return true if everything correct, or false otherwise.
     */
    public List<UserAccountModel> getAllAccounts(Context context) throws Exception {
        List<Object> mEncryptedList;
        List<UserAccountModel> mDecryptedList = new ArrayList<>();

        // Get All the UserAccount Object from DataBase
        mEncryptedList = DataBaseActions.getInstance().getAllAccounts(context, DB_UserAccountTable.KEY_TABLE_NAME);

        if (mEncryptedList == null)
            return null;


        // Loop on the list of object that we get from the DataBase
        for (Object account : mEncryptedList) {
            UserAccountModel tempDecryptedObject;

            // Decrypt All the fields of an object
            tempDecryptedObject = mDecryptObject((UserAccountModel) account);

            // Add the object into a temporary list of User Account
            if (tempDecryptedObject != null) {
                mDecryptedList.add(tempDecryptedObject);
            }
        }

        // Return the temporary list
        return mDecryptedList;
    }


    /**
     * The role of this Function is to test if the Username and Password are correct.
     *
     * @param context : The Application context where the function is called.
     * @return True if Account exist, False otherwise.
     * @throws Exception is case if an error occur in the Encryption process
     */
    public boolean verifyAuthenticationData(Context context) throws Exception {

        // Set the values to send to DataBase
        List<String> mColumns = Arrays.asList(
                DB_UserAccountTable.KEY_USERNAME,
                DB_UserAccountTable.KEY_PASSWORD
        );
        List<String> mValues = Arrays.asList(
                DataEncryption.getInstance().encryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        AppConfig.getInstance().getCurrentUser()),
                DataEncryption.getInstance().encryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        AppConfig.getInstance().getCurrentPassword()
                )
        );

        // Get the object from DataBase and test if it's Null or not
        return (DataBaseActions.getInstance().getAccount(context, DB_UserAccountTable.KEY_TABLE_NAME,
                mColumns, mValues)
                != null);
    }


    public String recoverPassword(Context context, String key) throws Exception {
        String mRef = getRef(context);
        return DataEncryption.getInstance().decryptData(
                key,
                mRef);
    }
    /* ************************** Private Methods *******************************/
    //---------------------------------------------------------------------------/

    private String getRef(Context context) {
        return ((UserAccountModel) DataBaseActions.getInstance().getAllAccounts(
                context,
                DB_UserAccountTable.KEY_TABLE_NAME
        ).get(0)).getRef();
    }

    /**
     * Generic function to Encrypt and add Data into a list with the corresponding Column.
     * In this function we Add only the columns that are not empty.
     *
     * @param LValue : The list of value where we'r going to add the Encrypted value.
     * @param LCols  : The list of columns where we'r going to add the column of the value.
     * @param KeyCol : The Column value that we want to add.
     * @param value  : The value that we want to Encrypt and Add.
     * @param isRef  : If True means to encrypt the Data with The Email, False use Password.
     * @throws Exception is case if an error occur in the Encryption process
     */
    private String mEncryptValue(List<String> LValue, List<String> LCols,
                                 String KeyCol, String value, Boolean isRef) throws Exception {
        if (!TextUtils.isEmpty(value)) {
            value = DataEncryption.getInstance().encryptData(
                    (isRef) ? value : AppConfig.getInstance().getCurrentPassword(),
                    (isRef) ? AppConfig.getInstance().getCurrentPassword() : value);
            if (LValue != null && LCols != null) {
                LValue.add(value);
                LCols.add(KeyCol);
            }
            return value;
        }
        return null;
    }

    /**
     * This function group all the Fields that we want to Encrypt.
     * Uses the {@link #mEncryptValue(List, List, String, String, Boolean)} function
     *
     * @param value       : The list of values that we want to populate.
     * @param columns     : The list of columns that we want to populate.
     * @param userAccount : the User Account Object that contain the Data.
     * @throws Exception is case if an error occur in the Encryption process
     */
    private void mEncryptValues(List<String> value, List<String> columns,
                                UserAccountModel userAccount) throws Exception {
        String fieldValue;

        // Get the values that are not empty.
        if (userAccount.getId() > 0) {
            if (value != null && columns != null) {
                value.add(Integer.toString(userAccount.getId()));
                columns.add(DB_UserAccountTable.KEY_ID);
            }
        }

        // Encrypt the Ref Value
        fieldValue = mEncryptValue(
                value,
                columns,
                DB_UserAccountTable.KEY_REF,
                userAccount.getEmail(), true); // The value to Encrypt with
        userAccount.setRef(fieldValue);

        // Encrypt the Username Value
        fieldValue = mEncryptValue(
                value,
                columns,
                DB_UserAccountTable.KEY_USERNAME,
                userAccount.getUsername(), false); // The value to encrypt
        userAccount.setUsername(fieldValue);

        // Encrypt the Email Value
        fieldValue = mEncryptValue(
                value,
                columns,
                DB_UserAccountTable.KEY_EMAIL,
                userAccount.getEmail(), false);
        userAccount.setEmail(fieldValue);

        // Encrypt the Password Value
        fieldValue = mEncryptValue(
                value,
                columns,
                DB_UserAccountTable.KEY_PASSWORD,
                userAccount.getPassword(), false);
        userAccount.setPassword(fieldValue);

    }

    /**
     * The role of this Function is to Decrypt the fields of an object.
     * The fields to Decrypt are : (Ref, Username, Email, Password)
     *
     * @param userAccount : The encrypted Object.
     * @return a Decrypted object
     * @throws Exception is case if an error occur in the Decryption process
     */
    private UserAccountModel mDecryptObject(UserAccountModel userAccount) throws Exception {


        // Decrypt the Username
        userAccount.setUsername(
                DataEncryption.getInstance().decryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        userAccount.getUsername())
        );

        // Decrypt the Email
        userAccount.setEmail(
                DataEncryption.getInstance().decryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        userAccount.getEmail())
        );

        // Decrypt the Password
        userAccount.setPassword(
                DataEncryption.getInstance().decryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        userAccount.getPassword())
        );

        return userAccount;
    }


}
