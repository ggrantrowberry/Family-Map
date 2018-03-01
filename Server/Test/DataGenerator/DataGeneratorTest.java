package DataGenerator;

import java.io.FileNotFoundException;

import DataAccess.Transaction;
import DataGeneration.DataGenerator;

import static org.junit.Assert.*;

/**
 * Created by GrantRowberry on 3/6/17.
 */

public class DataGeneratorTest {
    DataGenerator dg = new DataGenerator();
    @org.junit.Before
    public void setUp() throws Exception {


    }
    @org.junit.After
    public void tearDown() throws Exception {
    }

    @org.junit.Test
    public void generate() throws FileNotFoundException {
        dg.generateLocationData();

    }

}
