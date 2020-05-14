/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.AdminDAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author turco
 */
public class AdminDAO_TestSuite {
    AdminDAO dao;
    public AdminDAO_TestSuite() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dao = new AdminDAO();
    }
    
    @After
    public void tearDown() {
    }
    
    //Black-box
    @Test
    public void tryLogin_passwordErrata_test(){
        assertEquals( dao.tryLogin("admin", null), false);
    }
    @Test
    public void tryLogin_usernameErrato_test(){
        assertEquals( dao.tryLogin(null, "admin"), false);
    }
     @Test
    public void tryLogin_credenzialiCorrette_test(){
        assertEquals( dao.tryLogin("admin", "admin"), true);
    } 
    
    @Test
    public void tryLogin_EntrambeCredenzialiErrate_test(){
        assertEquals( dao.tryLogin(null, null), false);
    }
    
    @Test
    public void tryLogin_passwordVuota_test(){
        assertEquals( dao.tryLogin("admin", ""), false);
    }
     @Test
    public void tryLogin_usernameVuoto_test(){
        assertEquals( dao.tryLogin("", "admin"), false);
    }
}
