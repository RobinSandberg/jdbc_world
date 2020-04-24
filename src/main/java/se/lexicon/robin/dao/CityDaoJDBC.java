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

    private static final String FIND_ALL_STRING = "SELECT * FROM city";
    private static final String FIND_BY_ID_STRING = "SELECT * FROM city WHERE id = ?";
    private static final String FIND_BY_NAME_STRING = "SELECT * FROM city WHERE name LIKE ?";
    private static final String FIND_BY_CODE_STRING = "SELECT * FROM city WHERE countryCode LIKE ?";
    private static final String CREATE_CITY_STRING = "INSERT INTO city (name, CountryCode,District,Population) VALUES (?,?,?,?)";
    private static final String UPDATE_CITY_STRING = "UPDATE city SET name = ? , countryCode = ? , district = ? , population = ? WHERE id = ?";
    private static final String DELETE_CITY_STRING = "DELETE FROM city WHERE id = ?";
    private static ResultSet resultSet = null;

    @Override
    public City findByID(int id){
        City city = null;
        try (Connection connection = getConnection();PreparedStatement findById = connection.prepareStatement(FIND_BY_ID_STRING)) {
            findById.setInt(1, id);
            resultSet = findById.executeQuery();
            while(resultSet.next()){
                city = new City(resultSet.getInt("ID"),resultSet.getString("Name"),resultSet.getString("CountryCode"),
                        resultSet.getString("District"),resultSet.getInt("Population"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return city;
    }

    @Override
    public List<City> findByCode(String code){
        List<City> foundCitiesByCode = new ArrayList<>();
        try (Connection connection = getConnection();PreparedStatement findByCode = connection.prepareStatement(FIND_BY_CODE_STRING)){
            findByCode.setString(1, code+"%");
            resultSet = findByCode.executeQuery();
            while(resultSet.next()){
                foundCitiesByCode.add(new City(resultSet.getInt("ID"),resultSet.getString("Name"),resultSet.getString("CountryCode"),
                        resultSet.getString("District"),resultSet.getInt("Population")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return foundCitiesByCode;
    }

    @Override
    public List<City> findByName(String name){
        List<City> foundCitiesByName = new ArrayList<>();
            try (Connection connection = getConnection();PreparedStatement findByName = connection.prepareStatement(FIND_BY_NAME_STRING)){
                findByName.setString(1,name+"%");
            resultSet = findByName.executeQuery();
            while(resultSet.next()){
                foundCitiesByName.add(new City(resultSet.getInt("ID"),resultSet.getString("Name"),resultSet.getString("CountryCode"),
                        resultSet.getString("District"),resultSet.getInt("Population")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return foundCitiesByName;
    }

    @Override
    public List<City> findAll(){
        List<City> allCities = new ArrayList<>();
        try  (Connection connection = getConnection(); Statement statement = connection.createStatement()){
           resultSet = statement.executeQuery(FIND_ALL_STRING);
           while (resultSet.next()){
               allCities.add(new City(resultSet.getInt("ID"),resultSet.getString("Name"),resultSet.getString("CountryCode"),
                       resultSet.getString("District"),resultSet.getInt("Population")));
           }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return allCities;
    }

    @Override
    public City add(City city){
        City foundCity = null;
        City addedCity = null;
        try (Connection connection = getConnection();PreparedStatement createCity = connection.prepareStatement(CREATE_CITY_STRING);
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_STRING)){
            statement.setString(1,city.getName());
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                foundCity = new City(resultSet.getInt("ID"),resultSet.getString("Name"),resultSet.getString("CountryCode"),
                        resultSet.getString("District"),resultSet.getInt("Population"));
            }
            if(foundCity == null) {
                createCity.setString(1, city.getName());
                createCity.setString(2, city.getCountryCode());
                createCity.setString(3, city.getDistrict());
                createCity.setInt(4, city.getPopulation());
                createCity.executeUpdate();
                addedCity = findCityByName(city.getName());
            }else{
                System.out.println("City already exist");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return addedCity;
    }

    public City findCityByName(String name){
        City foundCity = null;
        try (Connection connection = getConnection();PreparedStatement findByName = connection.prepareStatement(FIND_BY_NAME_STRING)){
            findByName.setString(1,name);
            resultSet = findByName.executeQuery();
            while(resultSet.next()){
                foundCity = new City(resultSet.getInt("ID"),resultSet.getString("Name"),resultSet.getString("CountryCode"),
                        resultSet.getString("District"),resultSet.getInt("Population"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){
                    resultSet.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return foundCity;
    }

    @Override
    public City update(City city){
        City  updatedCity = null;
        try (Connection connection = getConnection();PreparedStatement updateCity = connection.prepareStatement(UPDATE_CITY_STRING)){
            updateCity.setString(1,city.getName());
            updateCity.setString(2,city.getCountryCode());
            updateCity.setString(3,city.getDistrict());
            updateCity.setInt(4,city.getPopulation());
            updateCity.setInt(5,city.getId());
            updateCity.executeUpdate();
            updatedCity = findByID(city.getId());
        }catch(SQLException e){
            e.printStackTrace();
        }
        return updatedCity;
    }

    @Override
    public int delete(City city){
        int rowsAffected = 0;
        try (Connection connection = getConnection();PreparedStatement deleteByID = connection.prepareStatement(DELETE_CITY_STRING)){
            deleteByID.setInt(1,city.getId());
            rowsAffected = deleteByID.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rowsAffected;
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
        return DriverManager.getConnection(host, login, password);
    }
}
