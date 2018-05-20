package controller;

import model.Call;
import model.Client;
import model.sql.SqlCallDao;
import model.sql.SqlClientDao;
import model.sql.SqlTaxiDao;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ClassController {

    Client currentClient;
    SqlClientDao clientDao = new SqlClientDao();
    SqlCallDao callDao = new SqlCallDao();
    SqlTaxiDao taxiDao = new SqlTaxiDao();

    @GetMapping(value = "logging")
    public ModelAndView client() {
        return new ModelAndView("logging", "command", new Client());
    }

    @PostMapping(value = "/logging")
    public ModelAndView checkingClient(@ModelAttribute("dispatcher") Client client, ModelMap model) throws Exception {
        Client clientFound;
        clientFound = clientDao.getByLogin(client.getLogin());
        if (clientFound != null && clientFound.getPassword().equals(client.getPassword())) {
            model.addAttribute("name", clientFound.getName());

            String tableData = UpdateTrainHistory(clientFound);
            model.addAttribute("tableData", tableData);

            currentClient = clientFound;
            return new ModelAndView("personalArea");
        } else {
            System.out.println("No Such User!");
            return new ModelAndView("logging", "command", new Client());
        }
    }

    @GetMapping(value = "registration")
    public ModelAndView newClient() {
        Client defaultClient = new Client();

        //При создании даты возникают проблемы, поэтому здесь устанавливаем её дефолтное значение (костыль)
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
        defaultClient.setBirthday(java.sql.Date.valueOf(formatForDateNow.format(dateNow)));
        return new ModelAndView("registration", "command", defaultClient);
    }

    @PostMapping(value = "/registration")
    public ModelAndView createClient(@ModelAttribute("dispatcher") Client client, ModelMap model) throws Exception {
        Client newClient;
        newClient = clientDao.getByLogin(client.getLogin());
        if (newClient == null && client.getLogin() != null && client.getPassword() != null) {

            clientDao.create(client.getLogin(), client.getPassword(), client.getName(), client.getBirthday(), client.getPhoneNumber());

            //Задаём дефолтное имя, если его не ввели
            newClient = clientDao.getByLogin(client.getLogin());
            if (newClient.getName() == null) {
                newClient.setName("Unknown");
            }

            model.addAttribute("name", newClient.getName());

            String tableData = "<tr><td>Travel Date</td><td>Start Point</td><td>Destination</td></tr>";
            model.addAttribute("tableData", tableData);
            currentClient = newClient;
            return new ModelAndView("personalArea");
        } else {
            System.out.println("Something is Wrong!");
            return new ModelAndView("registration", "command", new Client());
        }
    }

    @GetMapping(value = "settings")
    public ModelAndView settings() {
        return new ModelAndView("settings", "command", currentClient);
    }

    @PostMapping(value = "/settings")
    public ModelAndView changeSettings(@ModelAttribute("dispatcher") Client client, ModelMap model) throws Exception {
        Client clientFound = currentClient;
        clientDao.update(clientFound.getId(), client.getLogin(), client.getPassword(), client.getName(), client.getBirthday(), client.getPhoneNumber());

        if (client.getLogin() != null && client.getPassword() != null) {

            //Задаём дефолтное имя, если его не ввели
            if (client.getName() == null) {
                client.setName("Unknown");
            }

            model.addAttribute("name", client.getName());

            String tableData = UpdateTrainHistory(clientFound);
            model.addAttribute("tableData", tableData);

            client.setId(currentClient.getId());
            currentClient = client;
            return new ModelAndView("personalArea");
        } else {
            System.out.println("Something is realy Wrong!");
            return new ModelAndView("settings", "command", currentClient);
        }
    }

    @GetMapping(value = "route")
    public ModelAndView route() {
        return new ModelAndView("route", "command", new Call());
    }

    @PostMapping(value = "/route")
    public ModelAndView makingCall(@ModelAttribute("dispatcher") Call call, ModelMap model) throws Exception {

        if (call.getStartPoint() != null && call.getDestination() != null && call.getDate() != null) {
            callDao.create(currentClient.getId(), taxiDao.getLeastLoadId(), call.getStartPoint(), call.getDestination(), call.getDate(), call.getPayment());
            model.addAttribute("name", currentClient.getName());

            String tableData = UpdateTrainHistory(currentClient);
            model.addAttribute("tableData", tableData);

            return new ModelAndView("personalArea");
        } else {
            System.out.println("Not Enough Data");
            return new ModelAndView("personalArea", "command", currentClient);
        }
    }

    @GetMapping(value = "personalArea")
    public ModelAndView personalArea() {
        return new ModelAndView("route", "command", new Call());
    }

    public String UpdateTrainHistory(Client currentClient) throws Exception
    {
        List<Call> calls = callDao.getByClientId(currentClient.getId());
        String tableData = "<tr><td>Travel Date</td><td>Start Point</td><td>Destination</td></tr>";
        for (int i = 0; i < calls.size(); i++) {
            tableData = tableData + "<tr><td>" + calls.get(i).getDate() + "</td><td>" + calls.get(i).getStartPoint() + "</td><td>" + calls.get(i).getDestination() + "</td></tr>";
        }
        return tableData;

    }

    @Bean
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("artiom");
        dataSource.setPassword("artiom");
        dataSource.setURL("jdbc:oracle:thin:@localhost:1521:XE");
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }
}