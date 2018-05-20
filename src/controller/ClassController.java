package controller;

import model.Call;
import model.Client;
import model.Taxi;
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
            String tableData;
            int priorityLevel = clientDao.getPriorityLevelById(clientFound.getId());
            switch (priorityLevel){
                case 0:
                    model.addAttribute("name", clientFound.getName());

                    tableData = UpdateTrainHistory(clientFound);
                    model.addAttribute("tableData", tableData);

                    currentClient = clientFound;
                    return new ModelAndView("personalArea");
                case 1:
                    currentClient = clientFound;
                    return new ModelAndView("personalAreaModerator");
                case 2:
                    currentClient = clientFound;
                    return new ModelAndView("personalAreaSuperUser");
            }
                System.out.println("Priority Problems!");
                return new ModelAndView("logging", "command", new Client());
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

            clientDao.create(client.getLogin(), client.getPassword(), client.getName(), client.getBirthday(), client.getPhoneNumber(), 0);

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
        clientDao.update(clientFound.getId(), client.getLogin(), client.getPassword(), client.getName(), client.getBirthday(), client.getPhoneNumber(), 0);

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

    @PostMapping(value = "/personalArea")
    public ModelAndView personalArea(@ModelAttribute("dispatcher") Client client, ModelMap model) throws Exception {

        model.addAttribute("name", currentClient.getName());

        String tableData = UpdateTrainHistory(currentClient);
        model.addAttribute("tableData", tableData);

        return new ModelAndView("personalArea");
    }

    @GetMapping(value = "userAdministrationSuperUser")
    public ModelAndView userAdministrationSuperUser(ModelMap model) throws SQLException {
        String message = "<tr><td align=\"center\">ID</td><td align=\"center\">Login</td><td align=\"center\">Password</td><td align=\"center\">Name</td>" +
                "<td align=\"center\">Birthday</td><td>PhoneNumber</td><td nowrap align=\"center\">Priority Level</td><td align=\"center\">Status</td></tr>";
        Client client;
        List<Client> clients = clientDao.getAll();
        for (int i = 0; i < clients.size(); i++) {
            client = clients.get(i);
            message = message + "<tr><td align=\"center\">" + client.getId() + "</td><td align=\"center\">" + client.getLogin() + "</td><td align=\"center\">" +
                    client.getPassword() + "</td><td align=\"center\">" + client.getName() + "</td><td nowrap align=\"center\">" + client.getBirthday() + "</td><td align=\"center\">" +
                    client.getPhoneNumber() + "</td><td align=\"center\">" + client.getPriorityLevel() + "</td><td align=\"center\"><input id=\"status\" type=\"checkbox\"></td></tr>\n";
        }
        model.addAttribute("tableData", message);
        return new ModelAndView("userAdministrationSuperUser");
    }

    @GetMapping(value = "callAdministration")
    public ModelAndView callAdministration(ModelMap model) throws SQLException {
        String message = "<tr><td align=\"center\">ID</td><td align=\"center\">Date</td><td nowrap align=\"center\">Client ID</td><td align=\"center\">Taxi</td>" +
                "<td align=\"center\">Start Point</td><td align=\"center\">Destination</td><td nowrap align=\"center\">Payment</td><td align=\"center\">Status</td></tr>";
        Call call;
        List<Call> calls = callDao.getAll();
        for (int i = 0; i < calls.size(); i++) {
            call = calls.get(i);
            message = message + "<tr><td align=\"center\">" + call.getId() + "</td><td nowrap align=\"center\">" + call.getDate() + "</td><td align=\"center\">" +
                    call.getClientID() + "</td><td align=\"center\">" + call.getTaxiID() + "</td><td nowrap align=\"center\">" + call.getStartPoint() + "</td><td nowrap align=\"center\">" +
                    call.getDestination() + "</td><td align=\"center\">" + call.getPayment() + "</td><td align=\"center\"><input id=\"status\" type=\"checkbox\"></td></tr>\n";
        }
        model.addAttribute("tableData", message);
        return new ModelAndView("callAdministration");
    }

    @GetMapping(value = "taxiAdministration")
    public ModelAndView taxiAdministration(ModelMap model) throws SQLException {
        String message = "<tr><td align=\"center\">ID</td><td nowrap align=\"center\">Driver Name</td>" +
                "<td nowrap align=\"center\">Car Number</td><td nowrap align=\"center\">Car Model</td><td align=\"center\">Status</td></tr>";
        Taxi taxi;
        List<Taxi> taxis = taxiDao.getAll();
        for (int i = 0; i < taxis.size(); i++) {
            taxi = taxis.get(i);
            message = message + "<tr><td align=\"center\">" + taxi.getId() + "</td><td nowrap align=\"center\">" + taxi.getDriverName() + "</td><td align=\"center\">" +
                    taxi.getCarNumber() + "</td><td nowrap align=\"center\">" + taxi.getCarModel() + "</td><td align=\"center\"><input id=\"status\" type=\"checkbox\"></td></tr>\n";
        }
        model.addAttribute("tableData", message);
        return new ModelAndView("taxiAdministration");
    }

    @GetMapping(value = "personalAreaSuperUser")
    public ModelAndView personalAreaSuperUser(ModelMap model) throws SQLException {
        return new ModelAndView("personalAreaSuperUser");
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