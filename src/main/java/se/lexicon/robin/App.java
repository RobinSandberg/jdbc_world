package se.lexicon.robin;

import se.lexicon.robin.dao.CityDaoJDBC;
import se.lexicon.robin.model.City;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class App
{
    public static void main( String[] args )
    {

        CityDaoJDBC cityDao = new CityDaoJDBC();

        System.out.println(cityDao.findByID(5));
        cityDao.findByName("amster").forEach(System.out::println);
        List<City> foundCities = cityDao.findByCode("swe");
        foundCities.forEach(System.out::println);
        System.out.println(foundCities.size());
        List<City> allCities = cityDao.findAll();
        System.out.println(allCities.size());
        City city = new City("Rottne", "SWE", "Småland", 3000);
        System.out.println(cityDao.add(city));
        City updateCity = cityDao.findCityByName("Rottne");
        updateCity.setPopulation(5480);
        City newUpdatedCity = cityDao.update(updateCity);
        System.out.println(newUpdatedCity.toString());
        System.out.println(cityDao.delete(cityDao.findCityByName("Rottne")));
    }
}
