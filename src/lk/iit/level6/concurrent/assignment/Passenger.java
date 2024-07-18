package lk.iit.level6.concurrent.assignment;

import java.math.BigDecimal;
import java.util.Random;

public class Passenger implements Runnable {
    private PassengerInfo passengerInfo;
    private final TicketMachine ticketMachine;

    public Passenger(PassengerInfo passengerInfo, TicketMachine ticketMachine) {
        this.passengerInfo = passengerInfo;
        this.ticketMachine = ticketMachine;
    }

    private void getTicket(int ticketNumber, TravelInfo travelInfo, BigDecimal price) {
        Ticket ticket = new Ticket(ticketNumber, price, passengerInfo, travelInfo);
        ticketMachine.printTicket(ticket);
    }

    @Override
    public void run() {
        Random random = new Random();

        // Simulate 5 ticket printing requests.
        for (int i = 1; i <= 5; i++) {
            // Generate random travel info.
            String startingPoint = "Location_" + (random.nextInt(10) + 1);
            String destination = "Location_" + (random.nextInt(10) + 1);
            String startDate = "01/01/2022";
            String endDate = "01/02/2022";
            TravelInfo travelInfo = new TravelInfo(startingPoint, destination, startDate, endDate);
            // Generate random price.
            BigDecimal price = new BigDecimal(random.nextInt(1000) + 100);

            getTicket(i, travelInfo, price);

            try {
                // Sleep for a random time.
                Thread.sleep(random.nextInt(100) + 1);
            } catch (InterruptedException e) {
                throw new RuntimeException("Passenger " + passengerInfo.getPassengerName() +
                        " was interrupted during sleeping time ", e);
            }
        }
    }
}

