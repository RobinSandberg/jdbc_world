package se.lexicon.robin.dao;

import se.lexicon.robin.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDaoJDBC implements CityDao{
    private Connection connection;
    private Statement statement;
    private PreparedStatement findByName;
    private PreparedStatement findById;
    private PreparedStatement findByCode;

    public CityDaoJDBC(){ //göra om med try med resources istället för constructor
        try{
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?" +
                           "&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin",
                            "root","1234");
           statement = connection.createStatement();
           findByName = connection.prepareStatement("SELECT * FROM city WHERE name LIKE ?");
           findById = connection.prepareStatement("SELECT * FROM city WHERE id = ?");
           findByCode = connection.prepareStatement("SELECT * FROM city WHERE countryCode LIKE ?");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public City findByID(int id){
        City city = null;
        try {
            findById.setInt(1, id);
            ResultSet rs = findById.executeQuery();
            while(rs.next()){
                city = new City(rs.getInt("ID"),rs.getString("Name"),rs.getString("CountryCode"),
                        rs.getString("District"),rs.getInt("Population"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    public List<City> findByCode(String code){
        List<City> foundCitiesByCode = new ArrayList<>();
        try {
            findByCode.setString(1, code);
            ResultSet rs = findByCode.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return foundCitiesByCode;
    }

    public List<City> findByName(String name){
        List<City> foundCitiesByName = new ArrayList<>();
        try {
            findByName.setString(1, name);
            ResultSet rs = findByName.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return foundCitiesByName;
    }

    public List<City> findAll(){
        List<City> allCities = new ArrayList<>();
        String findAllCities = "SELECT * FROM city";
        try {
           ResultSet rs = statement.executeQuery(findAllCities);
           while (rs.next()){
               allCities.add(new City(rs.getInt("ID"),rs.getString("Name"),rs.getString("CountryCode"),
                       rs.getString("District"),rs.getInt("Population")));
           }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allCities;
    }

    public City add(City city){
      /*  if(!cities.contains(city)){
            cities.add(city);
            return city;
        }*/
        return null;
    }

    public City update(City city){
        return null;
    }

    public City delete(City city){
        return null;
    }

    public Connection getConnection(){
        return this.connection;
    }

}
