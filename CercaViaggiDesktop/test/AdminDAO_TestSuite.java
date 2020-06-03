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
    
    //Black-box SECT
    //1-5
    @Test
    public void tryLogin_EntrambeCredenzialiNull_test(){
        assertEquals( dao.tryLogin(null, null), false);
    }
    
    //1-6
    @Test
    public void tryLogin_usernameNull_passwordVuota_test(){
        assertEquals( dao.tryLogin(null, ""), false);
    }
    
    //1-7
    @Test
    public void tryLogin_usernameNull_passwordErrata_test(){
        assertEquals( dao.tryLogin(null, "sbagliata"), false);
    }
    //1-8
    @Test
    public void tryLogin_usernameNull_passwordCorretta_test(){
        assertEquals( dao.tryLogin(null, "admin"), false);
    }
    
    //2-5
    @Test
    public void tryLogin_usernameVuoto_passwordNull_test(){
        assertEquals( dao.tryLogin("", null), false);
    }
    
    //2-6
    @Test
    public void tryLogin_usernameVuoto_passwordVuota_test(){
        assertEquals( dao.tryLogin("", ""), false);
    }
    
    //2-7
    @Test
    public void tryLogin_usernameVuoto_passwordErrata_test(){
        assertEquals( dao.tryLogin("admin", "sbagliata"), false);
    }
    //2-8
    @Test
    public void tryLogin_usernameVuoto_passwordCorretta_test(){
        assertEquals( dao.tryLogin(null, "admin"), false);
    }
    //3-5
    @Test
    public void tryLogin_usernameSbagliato_passwordNull_test(){
        assertEquals( dao.tryLogin("sbagliato!!!", null), false);
    }
    
    //3-6
    @Test
    public void tryLogin_usernameSbagliato_passwordVuota_test(){
        assertEquals( dao.tryLogin("sbagliato!!!", ""), false);
    }
    
    //3-7
    @Test
    public void tryLogin_usernameSbagliato_passwordErrata_test(){
        assertEquals( dao.tryLogin("sbagliato!!!", "sbagliata!"), false);
    }
    //3-8
    @Test
    public void tryLogin_usernameSbagliato_passwordCorretta_test(){
        assertEquals( dao.tryLogin("sbagliato!!!", "admin"), false);
    }
    //4-5
    @Test
    public void tryLogin_usernameCorretto_passwordNull_test(){
        assertEquals( dao.tryLogin("admin", null), false);
    }
    
    //4-6
    @Test
    public void tryLogin_usernameCorretto_passwordVuota_test(){
        assertEquals( dao.tryLogin("admin", ""), false);
    }
    
    //4-7
    @Test
    public void tryLogin_usernameCorretto_passwordErrata_test(){
        assertEquals( dao.tryLogin("admin", "sbagliata!"), false);
    }
    //4-8
    @Test
    public void tryLogin_usernameCorretto_passwordCorretta_test(){
        assertEquals( dao.tryLogin("admin", "admin"), true);
    }
    
}
