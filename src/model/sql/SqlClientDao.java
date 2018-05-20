package model.sql;

import model.Client;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlClientDao {

    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String name = "artiom";
    private String password = "artiom";
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static Connection connection = null;

    public SqlClientDao() {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client getByID(int id) throws SQLException {
        String sql = "SELECT * FROM Client WHERE ID = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        Client client = fillingClient(statement);
        return client;
    }

    public Client getByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM Client WHERE Login = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);

        Client client = fillingClient(statement);
        return client;
    }

    public List<Client> getByName(String name) throws SQLException {
        String sql = "SELECT * FROM Call WHERE Name = ?";

        connection = DriverManager.getConnection(url, this.name, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);

        List<Client> clients = fillingClientList(statement);
        return clients;
    }

    public List<Client> getByBirthday(Date birthday) throws SQLException {
        String sql = "SELECT * FROM Call WHERE BornDate = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, birthday);

        List<Client> clients = fillingClientList(statement);
        return clients;
    }

    public Client getByPhoneNumber(int phoneNumber) throws SQLException {
        String sql = "SELECT * FROM Client WHERE PhoneNumber = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, phoneNumber);

        Client client = fillingClient(statement);
        return client;
    }

    public int getPriorityLevelById(int id) throws SQLException {
        String sql = "SELECT * FROM Client WHERE ID = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        Client client = fillingClient(statement);
        int priorityLevel = client.getPriorityLevel();
        return priorityLevel;
    }

    public List<Client> getAll() throws SQLException {
        String sql = "SELECT * FROM Client";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Client> clients = fillingClientList(statement);
        return clients;
    }

    public void create(String login, String password, String name, Date birthday, long phoneNumber, int priorityLevel) {
        String sql = "INSERT INTO Client (Login, Password, Name, BornDate, PhoneNumber, priorityLevel) VALUES (?,?,?,?,?,?)";

        try {
            connection = DriverManager.getConnection(url, this.name, this.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setDate(4, birthday);
            statement.setLong(5, phoneNumber);
            statement.setInt(6, priorityLevel);
            statement.executeUpdate();
            statement.close();

            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Client WHERE ID = ?";

        try {
            connection = DriverManager.getConnection(url, name, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();

            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(int id, String login, String password, String name, Date birthday, long phoneNumber, int priorityLevel) {
        String sql = "UPDATE Client SET Login = ?, Password = ?, Name = ?, BornDate = ?, PhoneNumber = ?, PriorityLevel = ? WHERE ID = ?";

        try {
            connection = DriverManager.getConnection(url, this.name, this.password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setDate(4, birthday);
            statement.setLong(5, phoneNumber);
            statement.setInt(6, priorityLevel);
            statement.setInt(7, id);
            statement.executeUpdate();
            statement.close();

            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Client fillingClient(PreparedStatement statement) throws SQLException {
        Client client = null;
        ResultSet result = statement.executeQuery();
        while (result.next() == true) {
            client = new Client();
            client.setId(result.getInt("ID"));
            client.setLogin(result.getString("Login"));
            client.setPassword(result.getString("Password"));
            client.setName(result.getString("Name"));
            client.setBirthday(result.getDate("BornDate"));
            client.setPhoneNumber(result.getLong("PhoneNumber"));
            client.setPriorityLevel(result.getInt("PriorityLevel"));
        }
        connection.close();
        return client;
    }

    public List<Client> fillingClientList(PreparedStatement statement) throws SQLException {
        Client client;
        List<Client> clients = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while (result.next() == true) {
            client = new Client();
            client.setId(result.getInt("ID"));
            client.setLogin(result.getString("Login"));
            client.setPassword(result.getString("Password"));
            client.setName(result.getString("Name"));
            client.setBirthday(result.getDate("BornDate"));
            client.setPhoneNumber(result.getLong("PhoneNumber"));
            client.setPriorityLevel(result.getInt("PriorityLevel"));
            clients.add(client);
        }
        connection.close();
        return clients;
    }
}