package javaFX;



import java.sql.*;
import java.util.ArrayList;

public class DbConnection {
    private Connection connection = null;
    private String dbName = "restaurants_dbs";
    private String url = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String username = "root";
    //todo: remove password...
    private String password = "Idan2702";

    public DbConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<String> AskDataBaseQuery(String sqlQuery)throws Exception{
        //"SELECT Url FROM restaurants_dbs.restaurants where Name = 'Kilian Stuba';"
        ArrayList<String> returnVal =  new ArrayList<String>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);
        while (resultSet.next()){
            returnVal.add(resultSet.getString(1));
        }
        return returnVal;
    }
    public boolean AddtoDataBase(String sqlQuery)throws Exception{
        boolean isSuccess = false;
        PreparedStatement ps = this.connection.prepareStatement(sqlQuery);
        int status = ps.executeUpdate();
        if(status != 0){
            isSuccess = true;
        }
        return isSuccess;
    }
    public boolean setValInDataBase(String sqlQuery)throws Exception{
        boolean isSuccess = false;
        return isSuccess;
    }
    public boolean deleteValInDataBase(String sqlQuery)throws Exception{
        boolean isSuccess = false;
        return isSuccess;
    }
}
