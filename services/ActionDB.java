package services;

import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import module.*;

public class ActionDB {
    private Connection currentConnectionDB;
    public ActionDB(){
        currentConnectionDB = getNewConnection();
    }
    public List<Animals> getAll(){
        List<Animals> pet = new ArrayList<>();
        try{
            if(currentConnectionDB == null)
                throw new SQLException("Нет данных");
            String query = "SELECT gen_id, id, name_animal, bday FROM pet_list ORDER BY id";
            Statement statement = currentConnectionDB.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Animals animals = new AnimalBuild().getAnimal(AnimalType.values()[rs.getInt(1)-1]);
                animals.setAnimalId(rs.getInt(2));
                animals.setAnimalName(rs.getString(3));
                animals.setAnimalBDay(rs.getDate(4).toLocalDate());
                pet.add(animals);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return pet;
    }
    public void addAnimals(Animals animal){
        int rows;
        try{
            if(currentConnectionDB == null)
                throw new SQLException("Данные не будут записаны");
            String query = "INSERT INTO pet_list (name_animal, bday, gen_id) SELECT ?, ?, (SELECT Id FROM gen_list WHERE gen_name = ?)";
            PreparedStatement prepSt = currentConnectionDB.prepareStatement(query);
            prepSt.setString(1, animal.getAnimalName());
            prepSt.setDate(2, Date.valueOf(animal.getAnimalBDay())); 
            prepSt.setString(3, animal.getClass().getSimpleName());
            rows = prepSt.executeUpdate();
            System.out.println("Записано строк: " + rows);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public Animals getById(int idAnimal){
        Animals animal = null;
        System.out.println(idAnimal);
        try{
            if(currentConnectionDB == null)
                throw new SQLException("Нет данных");
            String query = "SELECT gen_id, id, name_animal, bday FROM pet_list WHERE id = ?";
            PreparedStatement prepSt = currentConnectionDB.prepareStatement(query);
            prepSt.setInt(1, idAnimal);
            ResultSet rs = prepSt.executeQuery();
            if (rs.next()){
                animal = new AnimalBuild().getAnimal(AnimalType.values()[rs.getInt(1)-1]);
                animal.setAnimalId(rs.getInt(2));
                animal.setAnimalName(rs.getString(3));
                animal.setAnimalBDay(rs.getDate(4).toLocalDate());
                System.out.println(animal);
                return animal;
            }else{
                throw new SQLException("Такой записи не существует");
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return animal;
    }
    public void updateAnimal(Animals animal){
        int rows;
         try{
            if(currentConnectionDB == null)
                throw new SQLException("Нет данных");
            String query = "UPDATE pet_list SET name_animal = ?, bday = ? WHERE id = ?";
            PreparedStatement prepSt = currentConnectionDB.prepareStatement(query);
            prepSt.setString(1, animal.getAnimalName());
            prepSt.setDate(2, Date.valueOf(animal.getAnimalBDay())); 
            prepSt.setInt(3, animal.getAnimalId());
            rows = prepSt.executeUpdate();
            System.out.println("Записано строк: " + rows);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public List<String> getCommands(int idAnimal){
        List<String> commands = new ArrayList<>();
        try{
            if(currentConnectionDB == null)
                throw new SQLException("Нет данных");
            String query = "select command_name from comands where id_animal = ?";
            PreparedStatement prepSt = currentConnectionDB.prepareStatement(query);
            prepSt.setInt(1, idAnimal);
            ResultSet rs = prepSt.executeQuery();
            int count = 0;
            while(rs.next()){
                commands.add(rs.getString(1));
                count++;
            }
            if(count == 0)
                throw new SQLException("Это животное не знает никаких команд");
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return commands;
    }
    public void setCommands(int idAnimal, String command){
        int rows;
        try{
            if(currentConnectionDB == null)
                throw new SQLException("Данные не будут записаны");
            String query = "insert into comands (id_animal,command_name) value(?, ?)";
            PreparedStatement prepSt = currentConnectionDB.prepareStatement(query);
            prepSt.setInt(1, idAnimal);
            prepSt.setString(2, command);
            rows = prepSt.executeUpdate();
            System.out.println("Записано строк: " + rows);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void deleteAnimal(int idAnimal){
        int rows;
        try{
            if(currentConnectionDB == null)
                throw new SQLException("Данные не будут записаны");
            String query = "delete from pet_list where id = ?";
            PreparedStatement prepSt = currentConnectionDB.prepareStatement(query);
            prepSt.setInt(1, idAnimal);
            rows = prepSt.executeUpdate();
            System.out.println("Записано строк: " + rows);
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    private Connection getNewConnection(){
        try(FileInputStream fis = new FileInputStream(".resourses/mysql_conn")){
            Properties props = new Properties();
            props.load(fis);
            return DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
        }catch(SQLException | IOException ex){
            System.out.println(ex.getMessage());
            System.out.println("Подключение к БД не установлено");
            return null;
        }
    }
}
