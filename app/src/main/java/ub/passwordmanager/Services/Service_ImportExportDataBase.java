package ub.passwordmanager.Services;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ub.passwordmanager.Models.PwdAccountModel;
import ub.passwordmanager.R;
import ub.passwordmanager.appConfig.AppConfig;
import ub.passwordmanager.dataBase.DB_Helper;
import ub.passwordmanager.dataBase.DB_TablesInformation.DB_PwdAccountTable;
import ub.passwordmanager.tools.dataEncryption.DataEncryption;

/**
 * The role of this class is to provide the service for
 * saving the dataBase and an XML file that contains the unEncrypted Data.
 * Note that to use this class you need the "android.permission.WRITE_EXTERNAL_STORAGE"
 * Created by UcefBen on 12/09/2016.
 */
public abstract class Service_ImportExportDataBase {

    // The name of the DataBase
    private static final String DATABASE_NAME = "DBManager";

    // the folder path where we are going to save the DataBase and the XML file
    private static final String BACKUP_FOLDER_NAME = Environment.getExternalStorageDirectory()
            .toString() + "/PasswordManager";


    /**
     * This function give the possibility to export the data base
     * to a specific location in the external storage of the device
     *
     * @param context : the activity context.
     */
    public static void exportDataBase(Context context) {
        // Test if the folder exist otherwise show a message to the user
        if (!setBackUpFolder()) {
            // Add an error message "Problem when creating the folder"
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_import_export_problem),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // get the DataBase path
            String mDataBasePath = DB_Helper.GetDataBasePath(new DB_Helper(context));

            // Create a copy of the DataBase
            File origFile = new File(mDataBasePath);
            File newFile = new File(getDataBaseName());

            // Delete the file if exist
            if (isDataBaseFileExist()) {
                FileUtils.deleteQuietly(newFile);
            }

            // Copy the file
            FileUtils.copyFile(origFile, newFile);


            // Show a message that everything went correctly
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_export_database_message_done),
                    Toast.LENGTH_SHORT).show();

        } catch (IOException ex) {
            // Show a message that there is a problem
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_export_database_message_error),
                    Toast.LENGTH_SHORT).show();

            Log.e("Error Export DB", "[" + ex.getMessage() + "]");
            ex.printStackTrace();
        }
    }

    /**
     * This function import a dataBase from the external storage,
     * it return a true or false statement to indicate if the
     * application need to be restarted or not.
     *
     * @param context : the application context.
     */
    public static Boolean importDataBase(Context context) {
        if (isDataBaseFileExist()) {
            try {
                // get the DataBase path
                String mDataBasePath = DB_Helper.GetDataBasePath(new DB_Helper(context));

                // Create a copy of the DataBase
                File origFile = new File(mDataBasePath);
                File newFile = new File(getDataBaseName());

                FileUtils.deleteQuietly(origFile);
                FileUtils.copyFile(newFile, origFile);

                // Show a message that everything went correctly
                Toast.makeText(context,
                        context.getResources().getString(R.string.l_export_database_message_done),
                        Toast.LENGTH_SHORT).show();

                return true;

            } catch (IOException ex) {
                // Show a message that everything went correctly
                Toast.makeText(context,
                        context.getResources().getString(R.string.l_export_database_message_done),
                        Toast.LENGTH_SHORT).show();
                Log.i("Importation Error :", "[" + ex.getMessage() + "]");
                ex.printStackTrace();
                return false;
            }
        } else {
            // Show a message that no file found
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_export_import_noData_file),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * This function export the data from the PwdAccount Table.
     * it save's the data un-Encrypted into an XML file.
     *
     * @param context : the application context.
     */
    public static void exportToXml(Context context) {

        // Test if the folder exist otherwise show a message to the user
        if (!setBackUpFolder()) {
            // Add an error message "Problem when creating the folder"
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_import_export_problem),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // write the XML file from the Data
            writeXmlFile(context);

            // Show a message that everything went correctly
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_export_xml_message_done),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            // Show a message that everything went correctly
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_export_xml_message_done),
                    Toast.LENGTH_SHORT).show();
            Log.e("Error Export Xml ", "[" + ex.getMessage() + "]");
            ex.printStackTrace();
        }
    }

    /**
     * This function import the data from an XML file,
     * encrypt it and save it into the PwdAccount Table.
     * it return a true or false statement to indicate if the
     * application need to be restarted or not.
     *
     * @param context : the application context.
     */
    public static Boolean importFromXml(Context context) {
        try {
            if (isXmlDataFileExist()) {

                // Read and save the data
                readXmlFile(context);

                // Show a message that everything went correctly
                Toast.makeText(context,
                        context.getResources().getString(R.string.l_import_database_message_done),
                        Toast.LENGTH_SHORT).show();

                return true;

            } else {
                // Show a message that no file found
                Toast.makeText(context,
                        context.getResources().getString(R.string.l_export_import_noData_file),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }catch(Exception ex){
            // Show a message that there is a problem
            Toast.makeText(context,
                    context.getResources().getString(R.string.l_export_database_message_error),
                    Toast.LENGTH_SHORT).show();

            Log.e("Error Import XML", "[" + ex.getMessage() + "]");
            ex.printStackTrace();
            return null;
        }

    }


    //******************** Private Functions ***********************************//
    //--------------------------------------------------------------------------//

    /**
     * Function to write the extracted data into XML
     *
     * @param context : Activity context
     * @throws Exception
     */
    private static void writeXmlFile(Context context) throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.
                createElement(
                        DB_PwdAccountTable.KEY_TABLE_NAME + "s"
                );
        doc.appendChild(rootElement);

        List<PwdAccountModel> mListAccounts = Service_PwdAccount.getAllAccounts(context);

        for (PwdAccountModel mPwdAccount : mListAccounts) {

            // ******* Les Elements *********//
            // ------------------------------//

            // staff elements
            Element mAccount = doc.createElement(DB_PwdAccountTable.KEY_TABLE_NAME);
            rootElement.appendChild(mAccount);

            // WebSite elements
            Element mSite = doc.createElement(DB_PwdAccountTable.KEY_WEB_SITE);// Element
            mSite.appendChild(doc.createTextNode(mPwdAccount.getWebSite()));// Value
            mAccount.appendChild(mSite);

            // Email/Username elements
            Element mEmail = doc.createElement(DB_PwdAccountTable.KEY_USERNAME);// Element
            mEmail.appendChild(doc.createTextNode(mPwdAccount.getEmail()));// Value
            mAccount.appendChild(mEmail);

            // Password elements
            Element mPassword = doc.createElement(DB_PwdAccountTable.KEY_PASSWORD);// Element
            mPassword.appendChild(doc.createTextNode(mPwdAccount.getPassword())); // Value
            mAccount.appendChild(mPassword);

            // Other information elements
            Element mOtherInfo = doc.createElement(DB_PwdAccountTable.KEY_OTHER_INFO);// Element
            mOtherInfo.appendChild(doc.createTextNode(mPwdAccount.getOtherInfo())); // Value
            mAccount.appendChild(mOtherInfo);

            // Last Update elements
            Element mLastUpdate = doc.createElement(DB_PwdAccountTable.KEY_LAST_UPDATE);// Element
            mLastUpdate.appendChild(doc.createTextNode(mPwdAccount.getLastUpdate())); // Value
            mAccount.appendChild(mLastUpdate);
            // *******************************************************************************


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            if (isXmlDataFileExist()) {
                FileUtils.deleteQuietly(new File(getXmlBackUpName()));
            }
            StreamResult result = new StreamResult(new File(getXmlBackUpName()));

            transformer.transform(source, result);
        }
    }

    /**
     * Function to read data from the XML file.
     * Also save it into DataBase.
     *
     * @param context : Activity context
     * @throws Exception
     */
    private static void readXmlFile(Context context) throws Exception {

        // Step 1 : get an instance of the DocumentBuilderFactory class
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();

        // Step 2 : Create a document builder
        final DocumentBuilder builder = factory.newDocumentBuilder();

        // Step 3 : Create the file holder
        final Document document = builder.parse(new File(getXmlBackUpName()));

        // Step 4 : get the root element
        final Element racine = document.getDocumentElement();

        // Step 5 : get the first child
        final NodeList mRootNod = racine.getChildNodes();

        for (int i = 0; i < mRootNod.getLength(); i++) {

            if (mRootNod.item(i).getNodeType() == Node.ELEMENT_NODE) {
                // Xml element
                final Element element = (Element) mRootNod.item(i);

                // Password Account holder
                PwdAccountModel mPwdAccount = new PwdAccountModel();

                // Set the Data into the Pwd Account holder
                String dataToEncrypt;

                // WebSite element
                dataToEncrypt = element.getElementsByTagName(
                        DB_PwdAccountTable.KEY_WEB_SITE
                ).item(0).getTextContent();
                mPwdAccount.setWebSite(dataToEncrypt);

                // Email/Username elements
                dataToEncrypt = element.getElementsByTagName(
                        DB_PwdAccountTable.KEY_USERNAME
                ).item(0).getTextContent();
                dataToEncrypt = DataEncryption.encryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        dataToEncrypt
                );
                mPwdAccount.setEmail(dataToEncrypt);

                // Password elements
                dataToEncrypt = element.getElementsByTagName(
                        DB_PwdAccountTable.KEY_PASSWORD
                ).item(0).getTextContent();
                dataToEncrypt = DataEncryption.encryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        dataToEncrypt
                );
                mPwdAccount.setPassword(dataToEncrypt);

                // Other information elements
                dataToEncrypt = element.getElementsByTagName(
                        DB_PwdAccountTable.KEY_OTHER_INFO
                ).item(0).getTextContent();
                dataToEncrypt = DataEncryption.encryptData(
                        AppConfig.getInstance().getCurrentPassword(),
                        dataToEncrypt
                );
                mPwdAccount.setOtherInfo(dataToEncrypt);

                // Last Update elements
                dataToEncrypt = element.getElementsByTagName(
                        DB_PwdAccountTable.KEY_LAST_UPDATE
                ).item(0).getTextContent();
                mPwdAccount.setOtherInfo(dataToEncrypt);

                // - Add the Data holder into DataBase
                Service_PwdAccount.saveNewData(context, mPwdAccount);
            }
        }

    }

    /**
     * Function to get the name of the DataBase file for (Today)
     */
    private static String getDataBaseName() {
        return (BACKUP_FOLDER_NAME + "/" + DATABASE_NAME + ".db.bak");
    }

    /**
     * Function to get the name of the XML file for (Today)
     */
    private static String getXmlBackUpName() {
        return (BACKUP_FOLDER_NAME + "/" + DATABASE_NAME + ".xml");
    }

    /**
     * Create the BackUp folder in the external storage
     *
     * @return : True or false depending on the result of the execution
     */
    private static Boolean setBackUpFolder() {
        File backUpFolder = new File(BACKUP_FOLDER_NAME);
        if (!backUpFolder.exists()) {
            return backUpFolder.mkdir();
        } else {
            return true;
        }
    }


    /**
     * Test if a DataBase file with the same name exist
     *
     * @return True if exist, false otherwise
     */
    private static Boolean isDataBaseFileExist() {
        return new File(getDataBaseName()).exists();
    }

    /**
     * Test if an XML file with the same name exist
     *
     * @return True if exist, false otherwise
     */
    private static Boolean isXmlDataFileExist() {
        return new File(getXmlBackUpName()).exists();
    }

}
