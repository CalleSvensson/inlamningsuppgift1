package Server;

import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class GreenhouseDBDAO {
    private Properties p = new Properties();
    public GreenhouseDBDAO(){}

    public Greenhouse getMeasureNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String timeNow = df.format(new Date());
        Greenhouse measure= new Greenhouse();
        try{
            p.load(new FileInputStream("C:/Users/lordc/plugg/systemintegration/inlämningsuppgift1/src/main/java/Server/Settings.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try(Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from measure order by measureTime desc limit 1")){


            while(rs.next()){
                int  id = rs.getInt("id");
                float temperature = rs.getFloat("temperature");
                float humidity = rs.getFloat("humidity");
                float luminosity = rs.getFloat("luminosity");
                float electricityConsumption = rs.getFloat("electricityConsumption");
                LocalDate measureTime = rs.getDate("measureTime").toLocalDate();
                measure = new Greenhouse(id, temperature, humidity, luminosity, electricityConsumption, measureTime);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return measure;
    }

    public float getAVG(String measureType){
        float avg = (float) 0.0;
        try{
            p.load(new FileInputStream("C:/Users/lordc/plugg/systemintegration/inlämningsuppgift1/src/main/java/Server/Settings.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try(Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("select avg("+measureType+") from measure order by measureTime desc limit 7;")){
            ResultSet rs = stmt.executeQuery("SELECT avg("+measureType+") FROM (SELECT "+measureType+" FROM measure ORDER BY measureTime DESC LIMIT 7) AS subquery;")){

            while(rs.next()){
                avg = rs.getFloat("avg("+measureType+")");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return avg;
    }

    public float getCost(int cost){
        float totalConsumption = 0.0f;
        try{
            p.load(new FileInputStream("C:/Users/lordc/plugg/systemintegration/inlämningsuppgift1/src/main/java/Server/Settings.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try(Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT sum(electricityConsumption) FROM (SELECT electricityConsumption FROM measure ORDER BY measureTime DESC LIMIT 7) AS subquery;")){

            while(rs.next()){
                totalConsumption = rs.getFloat("sum(electricityConsumption)");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return totalConsumption * cost;
    }


    public ArrayList<Greenhouse> getRapport(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String timeNow = df.format(new Date());
        ArrayList<Greenhouse> rapportList = new ArrayList<Greenhouse>();
        try{
            p.load(new FileInputStream("C:/Users/lordc/plugg/systemintegration/inlämningsuppgift1/src/main/java/Server/Settings.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try(Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            //ResultSet rs = stmt.executeQuery("select * from measure where measureTime <= '"+timeNow+"' order by measureTime desc limit 7")){
            ResultSet rs = stmt.executeQuery("select * from measure  order by measureTime desc limit 7")){
            //ResultSet rs = stmt.executeQuery("select * from measure")){

            while(rs.next()){
                int  id = rs.getInt("id");
                float temperature = rs.getFloat("temperature");
                float humidity = rs.getFloat("humidity");
                float luminosity = rs.getFloat("luminosity");
                float electricityConsumption = rs.getFloat("electricityConsumption");
                LocalDate measureTime = rs.getDate("measureTime").toLocalDate();

                rapportList.add(new Greenhouse(id, temperature, humidity, luminosity, electricityConsumption, measureTime));

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return rapportList;
    }

    public void addMeasure(float temperature, float humidity, float luminosity, float electricityConsumption) {
        String query = "insert into measure (temperature, humidity, luminosity, electricityConsumption, manualUpdated)" +
                "values (?, ?, ?, ?, ?)";
        try{
            p.load(new FileInputStream("C:/Users/lordc/plugg/systemintegration/inlämningsuppgift1/src/main/java/Server/Settings.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query))
        {
            stmt.setFloat(1, temperature);
            stmt.setFloat(2, humidity);
            stmt.setFloat(3, luminosity);
            stmt.setFloat(4, electricityConsumption);
            stmt.setInt(5, 1);
            int numberOfUpdates = stmt.executeUpdate();
            System.out.println(numberOfUpdates);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateAll(float temperature, float humidity, float luminosity, float electricityConsumption) {
        String query = "UPDATE measure SET temperature = ?, humidity = ?, luminosity = ?, electricityConsumption = ?, manualUpdated = ? " +
                "order by measureTime desc limit 1";
        try{
            p.load(new FileInputStream("C:/Users/lordc/plugg/systemintegration/inlämningsuppgift1/src/main/java/Server/Settings.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection(p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query))
        {
            stmt.setFloat(1, temperature);
            stmt.setFloat(2, humidity);
            stmt.setFloat(3, luminosity);
            stmt.setFloat(4, electricityConsumption);
            stmt.setInt(5, 1);
            int numberOfUpdates = stmt.executeUpdate();
            System.out.println(numberOfUpdates);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}

