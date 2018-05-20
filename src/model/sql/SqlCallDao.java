package model.sql;

import model.Call;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlCallDao {

    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String name = "artiom";
    private String password = "artiom";
    private static String driver = "oracle.jdbc.driver.OracleDriver";

    private static Connection connection = null;

    public SqlCallDao(){
        try {
            Locale.setDefault(Locale.ENGLISH);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Call getByID(String id) {
        Call call = null;
        String sql = "SELECT * FROM Call WHERE ID = ?";

        try {
            connection = DriverManager.getConnection(url, name, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            while (result.next() == true) {
                call = new Call();
                call.setId(result.getInt("ID"));
                call.setDate(result.getDate("TravelDate"));
                call.setClientID(result.getInt("ClientId"));
                call.setTaxiID(result.getInt("TaxiId"));
                call.setStartPoint(result.getString("StartPoint"));
                call.setDestination(result.getString("Destination"));
            }
            connection.close();
            return call;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Call> getByDate(Date date) throws SQLException {
        String sql = "SELECT * FROM Call WHERE Date = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Call> calls = fillingCallList(statement);
        return calls;
    }

    public List<Call> getByClientId(int clientId) throws SQLException {
        String sql = "SELECT * FROM Call WHERE ClientId = ?";

        connection = DriverManager.getConnection(url, name, password);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, String.valueOf(clientId));

        List<Call> calls = fillingCallList(statement);
        return calls;
    }

    public List<Call> getByTaxiId(int taxiId) throws SQLException {
        String sql = "SELECT * FROM Call WHERE TaxiId = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Call> calls = fillingCallList(statement);
        return calls;
    }

    public List<Call> getByStartPoint(String startPoint) throws SQLException {
        String sql = "SELECT * FROM Call WHERE StartPoint = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        List<Call> calls = fillingCallList(statement);
        return calls;
    }

    public List<Call> getByDestination(String destination) throws SQLException {
        String sql = "SELECT * FROM Call WHERE Destination = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Call> calls = fillingCallList(statement);
        return calls;
    }

    public List<Call> getAll() throws SQLException {
        String sql = "SELECT * FROM Call";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Call> calls = fillingCallList(statement);
        return calls;
    }

    public void create(int clientID, int taxiID, String startPoint, String destination, Date travelDate, int payment) throws SQLException {
        //java.util.Date dateNow = new java.util.Date();
        //SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");

        String sql = "INSERT INTO Call (ClientID, TaxiID, StartPoint, Destination, TravelDate, Payment) VALUES (?,?,?,?,?,?)";
        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        try {
            statement.setInt(1, clientID);
            statement.setInt(2, taxiID);
            statement.setString(3, startPoint);
            statement.setString(4, destination);
            statement.setDate(5, travelDate);
            statement.setInt(6, payment);
            statement.executeUpdate();
            statement.close();

            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Call WHERE ID = ?";

        connection = DriverManager.getConnection(url, name, password);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();

            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(int id, int clientID, int taxiID, String startPoint, String destination, Date travelDate, int payment) {
        String sql = "UPDATE Call SET ClientID = ?, TaxiId = ?, StartPoint = ?, Destination = ?, TravelDate = ?, Payment = ? WHERE ID = ?";

        try {
            connection = DriverManager.getConnection(url, name, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clientID);
            statement.setInt(2, taxiID);
            statement.setString(3, startPoint);
            statement.setString(4, destination);
            statement.setDate(5, travelDate);
            statement.setInt(6, payment);
            statement.setInt(7, id);
            statement.executeUpdate();
            statement.close();

            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Call> fillingCallList(PreparedStatement statement) throws SQLException {
        Call call;
        List<Call> calls = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while (result.next() == true) {
            call = new Call();
            call.setId(result.getInt("ID"));
            call.setDate(result.getDate("TravelDate"));
            call.setClientID(result.getInt("ClientId"));
            call.setTaxiID(result.getInt("TaxiId"));
            call.setStartPoint(result.getString("StartPoint"));
            call.setDestination(result.getString("Destination"));
            call.setPayment(result.getInt("Payment"));
            calls.add(call);
        }
        connection.close();
        return calls;
    }
}