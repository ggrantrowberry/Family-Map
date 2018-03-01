package DataGeneration;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;

/**
 * Created by GrantRowberry on 3/6/17.
 */


public class DataGenerator  {
    LocationData locData;
    MaleNamesData mData;
    FemaleNamesData fData;
    SurnamesData sData;

    public void generate() throws FileNotFoundException{
        generateLocationData();
        generateMaleNameData();
        generateFemaleNameData();
        generateSurnameData();
    }


    public void generateLocationData() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("/Users/GrantRowberry/Documents/School/Winter 2017/CS 240/FMServer/locations.json");
        locData = gson.fromJson(reader, LocationData.class);
        //locData.print();
    }

    public void generateMaleNameData() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("/Users/GrantRowberry/Documents/School/Winter 2017/CS 240/FMServer/mnames.json");
         mData = gson.fromJson(reader, MaleNamesData.class);
    }

    public void  generateFemaleNameData() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("/Users/GrantRowberry/Documents/School/Winter 2017/CS 240/FMServer/fnames.json");
        fData = gson.fromJson(reader, FemaleNamesData.class);
    }
    public void generateSurnameData() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("/Users/GrantRowberry/Documents/School/Winter 2017/CS 240/FMServer/snames.json");
        sData = gson.fromJson(reader, SurnamesData.class);
    }
}
