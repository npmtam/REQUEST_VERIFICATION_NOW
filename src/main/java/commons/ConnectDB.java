package commons;

import org.apache.commons.math3.analysis.function.Constant;

import java.sql.*;

public class ConnectDB {

    public static Connection getSQLServerConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //URL Connection for SQL Server
        String connectionURL = "jdbc:sqlserver://" + Constants.HOST_NAME + ":1433" + ";instance=" + Constants.INSTANCE_NAME + ";databaseName=" + Constants.DATABASE;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionURL, Constants.DB_USERNAME, Constants.DB_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public void selectIDFromDB(String id) throws SQLException, ClassNotFoundException {
        Connection conn = getSQLServerConnection();

        Statement stmt = conn.createStatement();
        String selectQuery = "SELECT * FROM REQUESTID WHERE RequestID='" + id + "'";
        ResultSet rs = stmt.executeQuery(selectQuery);

        if (!rs.isBeforeFirst()) {
            System.out.println("This ID is not exist in the Database");
            Constants.isTheIDExisted = false;
            Constants.REQUEST_STATUS = "Need Confirmation";
        } else {
            System.out.println("This ID has been existed");
            Constants.isTheIDExisted = true;
            Constants.REQUEST_STATUS = "DUPLICATED";
        }
        conn.close();
        System.out.println("Done");
    }

    public void insertRecordToDB(String id, String requestStatus) {
        Connection conn = getSQLServerConnection();

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String insertQuery = "INSERT INTO REQUESTID (RequestID, RequestStatus)" +
//                " OUTPUT inserted.id" +
                " VALUES ('" + id + "', '" + requestStatus + "')";

        System.out.println(insertQuery);
        try {
            stmt.executeUpdate(insertQuery);
        } catch (SQLException throwables) {
            System.out.println("Got an error while inserting");
            throwables.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException throwables) {
            System.out.println("Error while close connection");
            throwables.printStackTrace();
        }
        System.out.println();

    }
}
