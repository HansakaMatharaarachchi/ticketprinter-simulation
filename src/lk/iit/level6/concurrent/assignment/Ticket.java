package lk.iit.level6.concurrent.assignment;

import java.math.BigDecimal;

public class Ticket {
    private final int ticketNumber;
    private BigDecimal ticketPrice;
    private PassengerInfo passengerInfo;
    private TravelInfo travelInfo;

    public Ticket(int ticketNumber, BigDecimal ticketPrice, PassengerInfo passengerInfo, TravelInfo travelInfo) {
        this.ticketNumber = ticketNumber;
        this.ticketPrice = ticketPrice;

        this.passengerInfo = passengerInfo;
        this.travelInfo = travelInfo;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public PassengerInfo getPassengerInfo() {
        return passengerInfo;
    }

    public void setPassengerInfo(PassengerInfo passengerInfo) {
        this.passengerInfo = passengerInfo;
    }

    public TravelInfo getTravelInfo() {
        return travelInfo;
    }

    public void setTravelInfo(TravelInfo travelInfo) {
        this.travelInfo = travelInfo;
    }

    public String toString() {
        return "Ticket ID: " + ticketNumber +
                ", Ticket Name: " + passengerInfo.getPassengerName() +
                ", Ticket Price: " + ticketPrice + ", Ticket Travel Info: " + travelInfo;
    }
}
