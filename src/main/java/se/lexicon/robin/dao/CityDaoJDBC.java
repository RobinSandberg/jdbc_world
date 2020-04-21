package se.lexicon.robin.dao;

import se.lexicon.robin.model.City;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CityDaoJDBC implements CityDao{

    private static String findByIdString = "SELECT * FROM city WHERE id = ?";
    private static String findByNameString = "SELECT * FROM city WHERE name LIKE ?";
    private static String findByCodeString = "SELECT * FROM city WHERE countryCode LIKE ?";

    @Override
    public City findByID(int id){
        City city = null;
        try (Connection connection = getConnection();PreparedStatement findById = connection.prepareStatement(findByIdString)) {
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

    @Override
    public List<City> findByCode(String code){
        List<City> foundCitiesByCode = new ArrayList<>();
        try (Connection connection = getConnection();PreparedStatement findByCode = connection.prepareStatement(findByCodeString)){
            findByCode.setString(1, code+"%");
            ResultSet rs = findByCode.executeQuery();
            while(rs.next()){
                foundCitiesByCode.add(new City(rs.getInt("ID"),rs.getString("Name"),rs.getString("CountryCode"),
                        rs.getString("District"),rs.getInt("Population")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return foundCitiesByCode;
    }

    @Override
    public List<City> findByName(String name){
        List<City> foundCitiesByName = new ArrayList<>();
        try (Connection connection = getConnection();PreparedStatement findByName = connection.prepareStatement(findByNameString)){
            findByName.setString(1,name+"%");
            ResultSet rs = findByName.executeQuery();
            while(rs.next()){
                foundCitiesByName.add(new City(rs.getInt("ID"),rs.getString("Name"),rs.getString("CountryCode"),
                        rs.getString("District"),rs.getInt("Population")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return foundCitiesByName;
    }

    @Override
    public List<City> findAll(){
        List<City> allCities = new ArrayList<>();
        String findAllCities = "SELECT * FROM city";
        try  (Connection connection = getConnection(); Statement statement = connection.createStatement()){
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

    @Override
    public City add(City city){
      /*  if(!cities.contains(city)){
            cities.add(city);
            return city;
        }*/
        return null;
    }

    @Override
    public City update(City city){
        return null;
    }

    @Override
    public City delete(City city){
        return null;
    }

    public static Connection getConnection() throws SQLException {
        String host = null;
        String login = null;
        String password = null;
        File DBconfig = new File("src/main/resources/DBconfig.dat");
        try(FileInputStream DBstream = new FileInputStream(DBconfig)){
            Properties dbProperties = new Properties();
            dbProperties.load(DBstream);
            host = dbProperties.getProperty("db.host");
            login = dbProperties.getProperty("db.login");
            password = dbProperties.getProperty("db.password");
        }catch(IOException e){
            e.printStackTrace();
        }
        return DriverManager.getConnection(host,login,password);
    }
}
