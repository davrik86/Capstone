package Utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    static String dbUrl = ConfigurationReader.getPropertyValue("DBURL");
    static String userName = ConfigurationReader.getPropertyValue("DBuserName");
    static String password = ConfigurationReader.getPropertyValue("DBpasword");


    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(dbUrl, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  static List<List<String>> executeQuery(String query){
        List<List<String>> dataSet = new ArrayList<>();
        try {

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colCount = rsmd.getColumnCount();
            while(resultSet.next()){
                //System.out.println(resultSet.getString("invoice_number"));
                List<String> row = new ArrayList<>();
                for (int i = 1; i <colCount ; i++) {
                    //System.out.print(resultSet.getString(i) + "|| ");
                    row.add(resultSet.getString(i));
                }
                dataSet.add(row);
                //System.out.println();
            }
            System.out.println(dataSet);
            //close all connections
            resultSet.close();
            statement.close();
            connection.close();
            return dataSet;
        } catch (SQLException e) {
            System.out.println("Connection not successful");
            e.printStackTrace();
        }
        return dataSet;
    }


    //Select query that will return the first record as a list of strings
    public  static List<String> selectRecord(String query){
        List<String> dataSet = new ArrayList<>();
        try {

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colCount = rsmd.getColumnCount();
            if(resultSet.next()){
                for (int i = 1; i < colCount; i++) {
                    dataSet.add(resultSet.getString(i));
                }
            }
            System.out.println(dataSet);
            //close all connections
            resultSet.close();
            statement.close();
            connection.close();
            return dataSet;
        } catch (SQLException e) {
            System.out.println("Connection not successful");
            e.printStackTrace();
        }
        return dataSet;
    }


    //Will select the first record and return back the data for that specific column
    public  static String selectRecord(String query, String colName){
        String dataSet= null;

        try {

            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colCount = rsmd.getColumnCount();
            if(resultSet.next()){
                dataSet= resultSet.getString(colName);
            }
            System.out.println(dataSet);
            //close all connections
            resultSet.close();
            statement.close();
            connection.close();
            return dataSet;
        } catch (SQLException e) {
            System.out.println("Connection not successful");
            e.printStackTrace();
        }
        return dataSet;
    }
  }








