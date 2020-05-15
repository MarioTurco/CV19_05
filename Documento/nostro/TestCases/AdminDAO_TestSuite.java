public class AdminDAO_TestSuite {
       private static AdminDAO dao;
       
       @BeforeClass
       public static void setUpClass() {
            dao = new AdminDAO();
       }
       
       //Black-box SECT
       //TestCase 1 (EC1 - EC5)
       @Test
       public void tryLogin_EntrambeCredenzialiNull_test(){
           assertEquals( dao.tryLogin(null, null), false);
       }
       
       //TestCase 2 (EC1 - EC6)
       @Test
       public void tryLogin_usernameNull_passwordVuota_test(){
           assertEquals( dao.tryLogin(null, ""), false);
       }
       
       //TestCase 3 (EC1 - EC7)
       @Test
       public void tryLogin_usernameNull_passwordErrata_test(){
           assertEquals( dao.tryLogin(null, "sbagliata"), false);
       }
       //TestCase 4 (EC1 - EC8)
       @Test
       public void tryLogin_usernameNull_passwordCorretta_test(){
           assertEquals( dao.tryLogin(null, "admin"), false);
       }
       
       //TestCase 5 (EC2 - EC5)
       @Test
       public void tryLogin_usernameVuoto_passwordNull_test(){
           assertEquals( dao.tryLogin("", null), false);
       }
       
       //TestCase 6 (EC2 - EC6)
       @Test
       public void tryLogin_usernameVuoto_passwordVuota_test(){
           assertEquals( dao.tryLogin("", ""), false);
       }
       
       //TestCase 7 (EC2 - EC7)
       @Test
       public void tryLogin_usernameVuoto_passwordErrata_test(){
           assertEquals( dao.tryLogin("admin", "sbagliata"), false);
       }
       
       //TestCase 8 (EC2 - EC8)
       @Test
       public void tryLogin_usernameVuoto_passwordCorretta_test(){
           assertEquals( dao.tryLogin(null, "admin"), false);
       }
       
       //TestCase 9 (EC3 - EC5)
       @Test
       public void tryLogin_usernameSbagliato_passwordNull_test(){
           assertEquals( dao.tryLogin("sbagliato!!!", null), false);
       }
       
       //TestCase 10 (EC3 - EC6)
       @Test
       public void tryLogin_usernameSbagliato_passwordVuota_test(){
           assertEquals( dao.tryLogin("sbagliato!!!", ""), false);
       }
       
       //TestCase 11 (EC3 - EC7)
       @Test
       public void tryLogin_usernameSbagliato_passwordErrata_test(){
           assertEquals( dao.tryLogin("sbagliato!!!", "sbagliata!"), false);
       }
       //TestCase 12 (EC3 - EC8)
       @Test
       public void tryLogin_usernameSbagliato_passwordCorretta_test(){
           assertEquals( dao.tryLogin("sbagliato!!!", "admin"), false);
       }
       //TestCase 13 (EC4 - EC5)
       @Test
       public void tryLogin_usernameCorretto_passwordNull_test(){
           assertEquals( dao.tryLogin("admin", null), false);
       }
       
       //TestCase 14 (EC4 - EC6)
       @Test
       public void tryLogin_usernameCorretto_passwordVuota_test(){
           assertEquals( dao.tryLogin("admin", ""), false);
       }
       
       //TestCase 15 (EC4 - EC7)
       @Test
       public void tryLogin_usernameCorretto_passwordErrata_test(){
           assertEquals( dao.tryLogin("admin", "sbagliata!"), false);
       }
       
       //TestCase 16 (EC4 - EC8)
       @Test
       public void tryLogin_usernameCorretto_passwordCorretta_test(){
           assertEquals( dao.tryLogin("admin", "admin"), true);
       }
   }
   