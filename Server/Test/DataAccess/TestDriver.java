package DataAccess;//import ServerProxy.ServerProxyTest;

public class TestDriver {

    public static void main(String[] args) {
//        org.junit.runner.JUnitCore.runClasses(
//                dataaccess.DatabaseTest.class,
//                spellcheck.URLFetcherTest.class,
//                spellcheck.WordExtractorTest.class,
//                spellcheck.DictionaryTest.class,
//                spellcheck.SpellingCheckerTest.class
//                );

        org.junit.runner.JUnitCore.main(
                "DataAccess.AuthTokenDaoTest",
                "DataAccess.EventDaoTest",
                "DataAccess.PersonDaoTest",
                "DataAccess.UserDaoTest",
                "ServerProxy.ServerProxyTest");

    }
}
