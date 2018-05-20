package model.sql;

import model.Taxi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqlTaxiDao {

    private String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private String name = "artiom";
    private String password = "artiom";
    private static String driver = "oracle.jdbc.driver.OracleDriver";

    private static Connection connection = null;

    public SqlTaxiDao() {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Taxi getByID(int id) throws SQLException {
        String sql = "SELECT * FROM Taxi WHERE ID = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        Taxi taxi = fillingTaxi(statement);
        return taxi;
    }

    public List<Taxi> getByDriverName(String driverName) throws SQLException {
        String sql = "SELECT * FROM Taxi WHERE DriverName = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Taxi> taxis = fillingTaxiList(statement);
        return taxis;
    }

    public int getLeastLoadId() throws SQLException {
        int taxi = 0;
        String sql = "Select TaxiId from (Select TaxiId, count(id) as callsCount " +
                "From Call " +
                "Group by TaxiID " +
                "Order by callsCount ASC) " +
                "where rownum <=1";

        connection = DriverManager.getConnection(url, name, password);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet result = statement.executeQuery();

            if (result.next() == true) {
                taxi = result.getInt(1);
            }
            connection.close();
            return taxi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Taxi getByCarNumber(String carNumber) throws SQLException {
        String sql = "SELECT * FROM Taxi WHERE CarNumber = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        Taxi taxi = fillingTaxi(statement);
        return taxi;
    }

    public List<Taxi> getByCarModel(String carModel) throws SQLException {
        String sql = "SELECT * FROM Taxi WHERE CarModel = ?";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Taxi> taxis = fillingTaxiList(statement);
        return taxis;
    }

    public List<Taxi> getAll() throws SQLException {
        String sql = "SELECT * FROM Taxi";

        connection = DriverManager.getConnection(url, name, password);
        PreparedStatement statement = connection.prepareStatement(sql);

        List<Taxi> taxis = fillingTaxiList(statement);
        return taxis;
    }

    public void create(String driverName, String carNumber, String carModel) throws SQLException {
        String sql = "INSERT INTO Taxi (DriverName, CarNumber, CarModel) VALUES (?,?,?)";

        connection = DriverManager.getConnection(url, name, password);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, driverName);
            statement.setString(2, carNumber);
            statement.setString(3, carModel);
            statement.executeUpdate();
            statement.close();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Taxi WHERE ID = ?";

        connection = DriverManager.getConnection(url, name, password);
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(int id, String driverName, String carNumber, String carModel) throws SQLException {
        String sql = "UPDATE Taxi SET DriverName = ?, CarNumber = ?, CarModel = ? WHERE ID = ?";

        connection = DriverManager.getConnection(url, name, password);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, driverName);
            statement.setString(2, carNumber);
            statement.setString(3, carModel);
            statement.setInt(4, id);
            statement.executeUpdate();
            statement.close();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Taxi fillingTaxi(PreparedStatement statement) throws SQLException {
        Taxi taxi = null;
        ResultSet result = statement.executeQuery();
        while (result.next() == true) {
            taxi = new Taxi();
            taxi.setId(result.getInt("ID"));
            taxi.setDriverName(result.getString("DriverName"));
            taxi.setCarNumber(result.getString("CarNumber"));
            taxi.setCarModel(result.getString("CarModel"));
        }
        connection.close();
        return taxi;
    }

    public List<Taxi> fillingTaxiList(PreparedStatement statement) throws SQLException {
        Taxi taxi;
        List<Taxi> taxis = new ArrayList<>();
        ResultSet result = statement.executeQuery();
        while (result.next() == true) {
            taxi = new Taxi();
            taxi.setId(result.getInt("ID"));
            taxi.setDriverName(result.getString("DriverName"));
            taxi.setCarNumber(result.getString("CarNumber"));
            taxi.setCarModel(result.getString("CarModel"));
            taxis.add(taxi);
        }
        connection.close();
        return taxis;
    }
}