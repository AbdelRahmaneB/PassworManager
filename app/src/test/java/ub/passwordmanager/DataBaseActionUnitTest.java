package ub.passwordmanager;

import android.content.Context;
import android.test.mock.MockContext;

import org.junit.Test;

import ub.passwordmanager.dataBase.DataBaseActions;

import static org.junit.Assert.assertEquals;

/**
 * Unit Text for the class {@link DataBaseActions}
 *
 * Created by UcefBen on 09/09/2016.
 */
public class DataBaseActionUnitTest {

    /**
     * Unit Test for Adding a UserAccount to the DataBase
     */
    @Test
    public void addingData_ToDataBase_UserAccount(){
        Context context = new MockContext();
        
        //DataBaseActions.newData(context,tablename,columns,values);
        assertEquals(true,true);
    }

    /**
     * Unit Test for Adding a UserAccount to the DataBase
     */
    @Test
    public void addingData_ToDataBase_UserAccount_NullValues(){

    }

    /**
     * Unit Test for Adding a PwdAccount to the DataBase
     */
    @Test
    public void addingData_ToDataBase_PwdAccount(){

    }

    /**
     * Unit Test for Adding a PwdAccount to the DataBase
     */
    @Test
    public void addingData_ToDataBase_PwdAccount_NullValues(){

    }




}
