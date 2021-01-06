package InterfacesAndAbstracts;
import javaFX.DbConnection;
import java.util.ArrayList;

abstract public class NewMember {
    protected boolean checkIfUserExist(String log_id, String log_pass) {
        boolean isExist = false;
        try {
            String query = "SELECT * FROM restaurants_dbs.users WHERE Username = '"
                    + log_id + "' AND Password = '" + log_pass + "';";
            DbConnection dbConnection = new DbConnection();
            ArrayList<String> ans = dbConnection.isExist(query);
            if(ans.size() > 0){
                isExist = true;
            }
            return isExist;

        }catch (Exception e){
            return isExist;
        }
    }
}
