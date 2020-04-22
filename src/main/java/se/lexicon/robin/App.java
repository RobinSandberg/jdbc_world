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

        CityDaoJDBC cityDao = new CityDaoJDBC();

        System.out.println(cityDao.findByID(5));
        cityDao.findByName("amster").forEach(System.out::println);
        cityDao.findByCode("swe").forEach(System.out::println);
        City city = new City("Rottne", "SWE", "Småland", 3000);
        cityDao.add(city);

    }
}
