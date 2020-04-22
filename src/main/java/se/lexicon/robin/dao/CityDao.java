package se.lexicon.robin.dao;

import se.lexicon.robin.model.City;

import java.util.List;

public interface CityDao {
    City findByID(int id);
    List<City> findByCode(String code);
    List<City> findByName(String name);
    List<City> findAll();
    City add(City city);
    City update(City city);
    int delete(City city);
}
