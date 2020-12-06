package javaFX;


import java.sql.*;
import java.util.ArrayList;

public class DbConnection {
    private ArrayList<DataObj> data = new ArrayList<DataObj>();
    private Connection connection = null;
    private String dbName = "restaurants_dbs";
    private String url = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String username = "root";
    //todo: remove password...
    private String password = "Idan2702";

    public DbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public ArrayList<DataObj> AskDataBaseQuery(String sqlQuery) throws Exception {
        ArrayList<DataObj> data = new ArrayList<DataObj>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            DataObj dataObj = new DataObj(resultSet.getDouble(3), resultSet.getDouble(4),
                    resultSet.getInt(6), resultSet.getString(5),
                    resultSet.getString(8), resultSet.getString(7),
                    resultSet.getString(1), resultSet.getString(11),
                    resultSet.getString(10));
            data.add(dataObj);
        }
        return data;
    }

    public ArrayList<String> isExist(String sqlQuery) throws Exception {
        ArrayList<String> returnVal = new ArrayList<String>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            returnVal.add(resultSet.getString(1));
        }
        return returnVal;
    }

    public boolean AddtoDataBase(String sqlQuery) throws Exception {
        boolean isSuccess = false;
        PreparedStatement ps = this.connection.prepareStatement(sqlQuery);
        int status = ps.executeUpdate();
        if (status != 0) {
            isSuccess = true;
        }
        return isSuccess;
    }

    public boolean setValInDataBase(String sqlQuery) throws Exception {
        boolean isSuccess = false;
        return isSuccess;
    }

    public boolean deleteValInDataBase(String sqlQuery) throws Exception {
        boolean isSuccess = false;
        PreparedStatement ps = this.connection.prepareStatement(sqlQuery);
        int status = ps.executeUpdate();
        if (status != 0) {
            isSuccess = true;
        }
        return isSuccess;
    }
}
