package se.lexicon.robin;

import se.lexicon.robin.dao.CityDaoJDBC;
import se.lexicon.robin.model.City;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class App
{
    public static void main( String[] args )
    {

        CityDaoJDBC test = new CityDaoJDBC();

        System.out.println(test.findByID(5));
        System.out.println(test.findByName("am"));
        System.out.println(test.findByCode("swe"));

    }
}
