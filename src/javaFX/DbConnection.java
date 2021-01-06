package javaFX;


import java.sql.*;
import java.util.ArrayList;

public class DbConnection {
    private Connection connection = null;
    private String dbName = "restaurants_dbs";
    private String url = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String username = "root";
    private String password = "Idan2702";

    public DbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void disconnect(){
        try {
            this.connection.close();
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
                    resultSet.getString(2), resultSet.getString(11),
                    resultSet.getString(10));
            data.add(dataObj);
        }
        return data;
    }

    public ArrayList<GetExtraInformation.likedRestData> AskDataBaseQueryforExtraInformation(String sqlQuery) throws Exception {
        ArrayList<GetExtraInformation.likedRestData> data = new ArrayList<GetExtraInformation.likedRestData>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            GetExtraInformation.likedRestData dataObj = new GetExtraInformation.likedRestData(resultSet.getString(1), resultSet.getInt(2));
            data.add(dataObj);
        }
        return data;
    }

    public ArrayList<GetExtraInformation.priceRestData> AskDataBaseQueryforPriceExtraInformation(String sqlQuery) throws Exception {
        ArrayList<GetExtraInformation.priceRestData> data = new ArrayList<GetExtraInformation.priceRestData>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            GetExtraInformation.priceRestData dataObj = new GetExtraInformation.priceRestData(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3),resultSet.getString(4));
            data.add(dataObj);
        }
        return data;
    }

    public ArrayList<LikedRestObj> AskDataBaseLikedRestQuery(String userName) throws Exception {
        ArrayList<LikedRestObj> data = new ArrayList<LikedRestObj>();
        Statement statement = this.connection.createStatement();
        String sqlQuery = "SELECT * FROM restaurants_dbs.likedrest WHERE userName = "+"\'"+userName+"\';";
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()) {
            LikedRestObj likedRestObj = new LikedRestObj(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            data.add(likedRestObj);
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
        PreparedStatement ps = this.connection.prepareStatement(sqlQuery);
        int status = ps.executeUpdate();
        if (status != 0) {
            isSuccess = true;
        }
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
