package se.lexicon.robin.dao;

import org.junit.Before;
import org.junit.Test;
import se.lexicon.robin.model.City;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CityDaoJDBCTest {

    private CityDaoJDBC cityDao;
    private List<City> foundCities;
    private City testCity;
    @Before
    public void setUp() {
        cityDao = new CityDaoJDBC();
        foundCities = new ArrayList<>();
        testCity = new City(3056,"Helsingborg", "SWE","SkÃ¥ne lÃ¤n", 117737);

    }

    @Test
    public void findByID() {
        int id = 3056;

        City foundCity = cityDao.findByID(id);

        assertEquals(testCity.getId(), foundCity.getId());
        assertEquals(testCity.getName(), foundCity.getName());
        assertEquals(testCity.getCountryCode(), foundCity.getCountryCode());
        assertEquals(testCity.getDistrict(), foundCity.getDistrict());
        assertEquals(testCity.getPopulation(), foundCity.getPopulation());
    }

    @Test
    public void find_By_Code_Successfully() {
        int expectedCount = 15;
        String code = "swe";
        foundCities = cityDao.findByCode(code);

        assertEquals(expectedCount, foundCities.size());
        assertTrue(foundCities.contains(testCity));
    }

    @Test
    public void find_By_Name_Successfully() {
        int expectedCount = 1;
        String name = "stockholm";
        foundCities = cityDao.findByName(name);

        assertEquals(expectedCount, foundCities.size());
        assertTrue(foundCities.get(0).getName().equalsIgnoreCase(name));
    }

    @Test
    public void find_All_Successfully() {
        int expected = 4079;

        assertEquals(expected, cityDao.findAll().size());
    }

    @Test
    public void add() {
        City newCity = new City("Rottne", "SWE", "Småland", 3000);

        City addedCity = cityDao.add(newCity);

        assertNotNull(addedCity);
        assertEquals(newCity.getName(),addedCity.getName());
        assertEquals(newCity.getCountryCode(),addedCity.getCountryCode());
        assertEquals(newCity.getDistrict(),addedCity.getDistrict());
        assertEquals(newCity.getPopulation(),addedCity.getPopulation());

        cityDao.delete(addedCity);
    }

    @Test
    public void update() {

        City newTestCity =  new City(3056,"Helsingborg", "SWE","SkÃ¥ne lÃ¤n", 2);

        cityDao.update(newTestCity);

        City updatedCity = cityDao.findByID(3056);

        assertEquals(newTestCity.getPopulation(), updatedCity.getPopulation());
        assertNotEquals(testCity.getPopulation(),updatedCity.getPopulation());

        cityDao.update(new City(3056,"Helsingborg", "SWE","SkÃ¥ne lÃ¤n", 117737));
    }

    @Test
    public void delete() {
        int expected = 1;
        City newCity = new City("Rottne", "SWE", "Småland", 3000);

        City addedCity = cityDao.add(newCity);

        int result = cityDao.delete(addedCity);
        assertEquals(expected,result);
    }

}