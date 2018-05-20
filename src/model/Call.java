package model;

import java.sql.Date;

public class Call {

    private int id;
    private int clientID;
    private int taxiID;
    private Date date;
    private String startPoint;
    private String destination;
    private int payment;


    public Call() {
        this.id = id;
        this.clientID = clientID;
        this.taxiID = taxiID;
        this.date = date;
        this.startPoint = startPoint;
        this.destination = destination;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getTaxiID() {
        return taxiID;
    }

    public void setTaxiID(int driverID) {
        this.taxiID = driverID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}