package lk.iit.level6.concurrent.assignment;

import java.util.Random;

public class TicketPaperTechnician implements Runnable {
    private final static int NUMBER_OF_REFILLS = 3;
    private String name;
    private ServiceTicketMachine machine;

    public TicketPaperTechnician(String name, ServiceTicketMachine machine) {
        this.name = name;
        this.machine = machine;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_REFILLS; i++) {
            machine.refillTicketPaper();

            try {
                // Sleep for a random time.
                Thread.sleep(random.nextInt(100) + 1);
            } catch (InterruptedException e) {
                throw new RuntimeException("Ticket Paper Technician " + name + " was interrupted during sleeping time", e);
            }
        }
    }
}
