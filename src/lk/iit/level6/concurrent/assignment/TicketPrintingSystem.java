package lk.iit.level6.concurrent.assignment;

public class TicketPrintingSystem {
    public static void main(String[] args) {
        // Create thread groups.
        ThreadGroup passengerGroup = new ThreadGroup("PassengerGroup");
        ThreadGroup ticketTechnicianGroup = new ThreadGroup("TicketTechnicianGroup");

        int initialNoOfTicketsPrinted = 3;

        // Create a new ticket machine.
        TicketMachine ticketMachine = new Machine(TicketMachine.SHEETS_PER_PACK, TicketMachine.FULL_TONER_LEVEL, initialNoOfTicketsPrinted, passengerGroup);
        System.out.println("Ticket machine created with initial paper level: " + TicketMachine.SHEETS_PER_PACK +
                ", initial toner level: " + TicketMachine.FULL_TONER_LEVEL + ", and initial no. of tickets printed: " + initialNoOfTicketsPrinted + ".");

        // Create ticket technicians.
        TicketTonerTechnician ticketTonerTechnician = new TicketTonerTechnician("John V. Mann", (ServiceTicketMachine) ticketMachine);
        TicketPaperTechnician ticketPaperTechnician = new TicketPaperTechnician("Gabriel G. Cade", (ServiceTicketMachine) ticketMachine);

        // Create ticket technician threads.
        Thread ticketTonerTechnicianThread = new Thread(ticketTechnicianGroup, ticketTonerTechnician, "Ticket Toner Technician");
        Thread ticketPaperTechnicianThread = new Thread(ticketTechnicianGroup, ticketPaperTechnician, "Ticket Paper Technician");

        // Create passengers.
        Passenger passenger = new Passenger(new PassengerInfo("John Doe", "No. 123, Galle Road, Colombo 03", "0771234567"), ticketMachine);
        Passenger passenger2 = new Passenger(new PassengerInfo("Jane Doe", "No. 456, Galle Road, Colombo 03", "0777654321"), ticketMachine);
        Passenger passenger3 = new Passenger(new PassengerInfo("John Smith", "No. 789, Galle Road, Colombo 03", "0771122334"), ticketMachine);
        Passenger passenger4 = new Passenger(new PassengerInfo("Jane Smith", "No. 987, Galle Road, Colombo 03", "0774433221"), ticketMachine);

        // Create passenger threads.
        Thread passengerThread = new Thread(passengerGroup, passenger, "Passenger 1");
        Thread passengerThread2 = new Thread(passengerGroup, passenger2, "Passenger 2");
        Thread passengerThread3 = new Thread(passengerGroup, passenger3, "Passenger 3");
        Thread passengerThread4 = new Thread(passengerGroup, passenger4, "Passenger 4");

        // Start passenger threads.
        passengerThread.start();
        passengerThread2.start();
        passengerThread3.start();
        passengerThread4.start();

        // Start ticket technician threads.
        ticketTonerTechnicianThread.start();
        ticketPaperTechnicianThread.start();

        // Wait for passenger threads to finish.
        try {
            passengerThread.join();
            passengerThread2.join();
            passengerThread3.join();
            passengerThread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Main thread was interrupted while waiting for passenger threads to finish", e);
        }
        System.out.println("All passenger threads finished execution.");

        // Wait for ticket technician threads to finish.
        try {
            ticketTonerTechnicianThread.join();
            ticketPaperTechnicianThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Main thread was interrupted while waiting for ticket technician threads to finish", e);
        }
        System.out.println("All ticket technician threads finished execution.");
        System.out.println("No. of tickets printed: " + ticketMachine.getNoOfTicketsPrinted() + ".");
    }
}
