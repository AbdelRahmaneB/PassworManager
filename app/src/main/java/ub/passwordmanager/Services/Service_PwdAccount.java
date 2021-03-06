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

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.dataBase.DB_TablesInformation.DB_PwdAccountTable;
import ub.passwordmanager.dataBase.DataBaseActions;
import ub.passwordmanager.tools.dataEncryption.DataEncryption;

/**
 * The role of this class is to handle the communication with The DataBase and the views
 * Created by UB on 10/09/2016.
 */
public class Service_PwdAccount {

    // Instance of the class
    private static Service_PwdAccount INSTANCE;

    private Service_PwdAccount() {
        // Do nothing
    }

    /**
     * This method allow us to be sure that there will be only one instance of this class
     *
     * @return the instance of this class
     */
    public static Service_PwdAccount getInstance() {
        if (INSTANCE == null) {
            synchronized (Service_PwdAccount.class) {
                INSTANCE = new Service_PwdAccount();
            }
        }
        return INSTANCE;
    }


    /**
     * This function Calls the persistence of new data into the DataBase.
     *
     * @param context    : The Application context where the function is called.
     * @param pwdAccount : the user account object with all the data from the view.
     * @return true if everything correct, or false otherwise.
     */
    public Boolean saveNewData(Context context, PwdAccountModel pwdAccount) throws Exception {

        // Encrypt all the Data before sending it to the DataBase
        mEncryptObject(
                null,
                null,
                pwdAccount);

        pwdAccount.setId(-1); // No Id

        // Create the list of values to send to the DataBase
        List<String> values = Arrays.asList(
                pwdAccount.getWebSite(),
                pwdAccount.getEmail(),
                pwdAccount.getPassword(),
                pwdAccount.getOtherInfo(),
                pwdAccount.getLastUpdate()
        );

        // Send the information to the DataBase
        return DataBaseActions.getInstance().newData(
                context,
                DB_PwdAccountTable.KEY_TABLE_NAME,
                DB_PwdAccountTable.KEY_AlL_COLUMNS,
                values
        );
    }


    /**
     * This function Calls the persistence of edit data into the DataBase.
     *
     * @param context    : The Application context where the function is called.
     * @param pwdAccount : the user account object with all the data from the view.
     * @return true if everything correct, or false otherwise.
     */
    public Boolean saveModifiedData(Context context, PwdAccountModel pwdAccount) throws Exception {

        // Encrypt all the Data before sending it to the DataBase
        mEncryptObject(
                null,
                null,
                pwdAccount);

        // Create the list of values to send to the DataBase
        List<String> values = Arrays.asList(
                Integer.toString(pwdAccount.getId()),
                pwdAccount.getWebSite(),
                pwdAccount.getEmail(),
                pwdAccount.getPassword(),
                pwdAccount.getOtherInfo(),
                "" + pwdAccount.getLastUpdate()
        );

        // Send the information to the DataBase
        return DataBaseActions.getInstance().editData(
                context,
                DB_PwdAccountTable.KEY_TABLE_NAME,
                DB_PwdAccountTable.KEY_AlL_COLUMNS,
                values
        );
    }

    /**
     * This function Calls the persistence of delete data from the DataBase.
     * Mostly only the 'Id' is required.
     *
     * @param context    : The Application context where the function is called.
     * @param pwdAccount : the user account object with all the data from the view.
     * @return true if everything correct, or false otherwise.
     */
    public Boolean deleteData(Context context, PwdAccountModel pwdAccount) {
        // Send the information to the DataBase.
        return DataBaseActions.getInstance().deleteData(
                context,
                DB_PwdAccountTable.KEY_TABLE_NAME,
                pwdAccount.getId()
        );
    }


    /**
     * This function is used to get all the User Account rows from the DataBase.
     *
     * @param context : The Application context where the function is called.
     * @return true if everything correct, or false otherwise.
     */
    public List<PwdAccountModel> getAllAccounts(Context context, String oldPwdToDecryptWith) throws Exception {
        List<Object> mEncryptedList;
        List<PwdAccountModel> mDecryptedList = new ArrayList<>();

        // Get All the UserAccount Object from DataBase
        mEncryptedList = DataBaseActions.getInstance().getAllAccounts(context, DB_PwdAccountTable.KEY_TABLE_NAME);


        // Loop on the list of object that we get from the DataBase
        for (Object account : mEncryptedList) {
            PwdAccountModel tempDecryptedObject;

            // Decrypt All the fields of an object
            tempDecryptedObject = mDecryptObject(oldPwdToDecryptWith,
                    (PwdAccountModel) account);

            // Add the object into a temporary list of User Account
            if (tempDecryptedObject != null) {
                mDecryptedList.add(tempDecryptedObject);
            }
        }

        // Return the temporary list
        return mDecryptedList;
    }


    /**
     * This function is used to get a specific Password Account rows from the DataBase.
     *
     * @param context : The Application context where the function is called.
     * @return true if everything correct, or false otherwise.
     */
    public PwdAccountModel getAccountById(Context context, String id) throws Exception {
        Object mEncryptedList;

        // Set The list of Columns and values to send to DataBase
        List<String> columns = Arrays.asList(
                DB_PwdAccountTable.KEY_ID
        );
        List<String> values = Arrays.asList(id);

        // Get All the UserAccount Object from DataBase
        mEncryptedList = DataBaseActions.getInstance().getAccount(context, DB_PwdAccountTable.KEY_TABLE_NAME
                , columns, values);

        // Return the temporary list
        return mDecryptObject(AppConfig.getInstance().getCurrentPassword(),
                (PwdAccountModel) mEncryptedList);
    }


    /**
     * Function that decrypt and reEncrypt the PwdAccount
     * with the new password ans save it into the database.
     *
     * @param context             : Application context.
     * @param oldPwdToDecryptWith : The old password, used to decrypt Data
     * @return : True or False.
     * @throws Exception
     */
    public Boolean reEncryptData(Context context, String oldPwdToDecryptWith) throws Exception {
        // bool to test is the reEncrypting went ok
        boolean isEverythingGood = false;

        // Get the list of all the accounts int the database
        List<PwdAccountModel> mListPwdAccounts = getAllAccounts(context, oldPwdToDecryptWith);

        // Loop on the Data
        for (PwdAccountModel mPwdAccount : mListPwdAccounts) {
            // Save the changes in the data base
            isEverythingGood = saveModifiedData(context, mPwdAccount);
        }

        return isEverythingGood;
    }

    /* ************************** Private Methods *******************************/
    //---------------------------------------------------------------------------/

    /**
     * Generic function to Encrypt and add Data into a list with the corresponding Columns.
     * In this function we Add only the columns that are not empty.
     *
     * @param LValue : The list of value where we'r going to add the Encrypted value.
     * @param LCols  : The list of columns where we'r going to add the column of the value.
     * @param KeyCol : The Column value that we want to add.
     * @param value  : The value that we want to Encrypt and Add.
     * @throws Exception is case if an error occur in the Encryption process
     */
    private String mEncryptValue(List<String> LValue, List<String> LCols,
                                 String KeyCol, String value) throws Exception {
        if (!TextUtils.isEmpty(value)) {
            value = DataEncryption.getInstance().encryptData(
                    AppConfig.getInstance().getCurrentPassword(),
                    value);
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
     * Uses the {@link #mEncryptValue(List, List, String, String)} function
     *
     * @param value      : The list of values that we want to populate.
     * @param columns    : The list of columns that we want to populate.
     * @param pwdAccount : the Password Account Object that contain the Data.
     * @throws Exception is case if an error occur in the Encryption process
     */
    private void mEncryptObject(List<String> value, List<String> columns,
                                PwdAccountModel pwdAccount) throws Exception {
        String fieldValue;

        // Get the values that are not empty.
        if (pwdAccount.getId() > 0) {
            if (!(value == null) && !(columns == null)) {
                value.add(Integer.toString(pwdAccount.getId()));
                columns.add(DB_PwdAccountTable.KEY_ID);
            }
        }


        // Encrypt the Email/Username Value
        fieldValue = mEncryptValue(
                value,
                columns,
                DB_PwdAccountTable.KEY_USERNAME,
                pwdAccount.getEmail());
        pwdAccount.setEmail(fieldValue);

        // Encrypt the Password Value
        fieldValue = mEncryptValue(
                value,
                columns,
                DB_PwdAccountTable.KEY_PASSWORD,
                pwdAccount.getPassword());
        pwdAccount.setPassword(fieldValue);

        // Encrypt the Other Information value
        fieldValue = mEncryptValue(
                value,
                columns,
                DB_PwdAccountTable.KEY_OTHER_INFO,
                pwdAccount.getOtherInfo());
        pwdAccount.setOtherInfo(fieldValue);

    }

    /**
     * The role of this Function is to Decrypt the fields of an object.
     * The fields to Decrypt are : (Ref, Username, Email, Password)
     *
     * @param pwdAccount : The encrypted Object.
     * @return a Decrypted object
     * @throws Exception is case if an error occur in the Decryption process
     */
    private PwdAccountModel mDecryptObject(String mUsedPassword, PwdAccountModel pwdAccount) throws Exception {
        // Decrypt the Email/Username
        pwdAccount.setEmail(
                DataEncryption.getInstance().decryptData(
                        mUsedPassword,
                        pwdAccount.getEmail())
        );

        // Decrypt the Password
        pwdAccount.setPassword(
                DataEncryption.getInstance().decryptData(
                        mUsedPassword,
                        pwdAccount.getPassword())
        );

        // Decrypt the Other Information
        if (!pwdAccount.getOtherInfo().equals("")) {
            pwdAccount.setOtherInfo(
                    DataEncryption.getInstance().decryptData(
                            mUsedPassword,
                            pwdAccount.getOtherInfo())
            );
        }

        return pwdAccount;
    }


}
