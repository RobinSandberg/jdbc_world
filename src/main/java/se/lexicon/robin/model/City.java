package se.lexicon.robin.model;

import java.util.Objects;

public class City {
    private int id;
    private String name;
    private String countryCode;
    private String district;
    private int population;

    public City(String name, String countryCode , String district, int population ){
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    public City(int id,String name, String countryCode , String district, int population ){
     this.id = id;
     this.name = name;
     this.countryCode = countryCode;
     this.district = district;
     this.population = population;
    }

    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public String getCountryCode() {
        return countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public int getPopulation() {
        return population;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return id == city.id &&
                population == city.population &&
                Objects.equals(name, city.name) &&
                Objects.equals(countryCode, city.countryCode) &&
                Objects.equals(district, city.district);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryCode, district, population);
    }

    @Override
    public String toString() {
        return "City Id: " + id +
                " , Name: " + name +
                " , Country Code: " + countryCode +
                " , District: " + district +
                " , Population: " + population;
    }
}
