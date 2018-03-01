package DataGenerator;

import java.io.FileNotFoundException;

import DataGeneration.FamilyTree;
import Model.Person;

/**
 * Created by GrantRowberry on 3/7/17.
 */

public class FamilyTreeTest {


    @org.junit.Test
    public void addGenerations() throws FileNotFoundException {
        Person p = new Person();
        p.setBirthYear(1995);
        p.setFirstName("Grant");
        p.setPersonID("12345");
        p.setLastName("Rowberry");
        p.setGender("Male");
        p.setDescendant("grantrow");

        FamilyTree ft = new FamilyTree();
        ft.addGenerations(p);
        ft.getEvents();
        System.out.println(ft.toString());

    }
}
