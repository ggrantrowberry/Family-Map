
    package DataAccess;

    import java.util.UUID;
    import java.util.concurrent.TimeUnit;

    import Model.AuthToken;

    import static org.junit.Assert.*;

    /**
     * Created by GrantRowberry on 2/28/17.
     */
    public class AuthTokenDaoTest {
        Transaction transaction;
        AuthToken a;
        AuthTokenDao ad;

        @org.junit.Before
        public void setUp() throws Exception {
            transaction = new Transaction();
            transaction.openConnection();
            transaction.createTables();
            ad = transaction.getAd();

        }
        @org.junit.After
        public void tearDown() throws Exception {
            transaction.closeConnection(true);
        }


        public AuthToken getAuthToken(String username) throws Exception {
            return ad.getAuthToken(username);

        }

        @org.junit.Test
        public void insertAuthToken() throws Exception {
            a = new AuthToken();
            a.setAuthToken("12345");
            a.setUsername("g$row");

            ad.insertAuthToken(a);
            AuthToken two = getAuthToken("g$row");
            assertEquals(a.getAuthToken(),two.getAuthToken());
            assertEquals(a.getUsername(),two.getUsername());

        }

        @org.junit.Test
        public void updateAuthTokenTime() throws Exception{
            a = new AuthToken();
            a.setAuthToken("12345");
            a.setUsername("g$row");

            ad.insertAuthToken(a);
            AuthToken one = ad.getAuthToken("g$row");
            TimeUnit.SECONDS.sleep(2);
            ad.updateAuthTokenTime(a);
            a = ad.getAuthToken("g$row");
            System.out.println(one.getTimestamp());
            System.out.println(a.getTimestamp());
            assertFalse(one.getTimestamp().equals(a.getTimestamp()));
        }

        @org.junit.Test
        public void authTokenExists() throws Exception{
            a = new AuthToken();
            String token = UUID.randomUUID().toString();
            a.setAuthToken(token);
            a.setUsername("g$row");

            ad.insertAuthToken(a);
            System.out.println(token);
            AuthToken test = ad.existsAuthToken(token);
            if(test == null){
                assertTrue(false);
            } else {
                System.out.println(test.getAuthToken());
                assertTrue(true);
            }


        }



    }

