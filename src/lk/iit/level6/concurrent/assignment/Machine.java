package lk.iit.level6.concurrent.assignment;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Machine implements ServiceTicketMachine {
    private final ThreadGroup passengers;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition tonerAvailable = lock.newCondition();
    private final Condition paperAvailable = lock.newCondition();
    private final Condition resourcesUnavailable = lock.newCondition();

    private int noOfTicketsPrinted;
    private int currentPaperLevel;
    private int currentTonerLevel;

    public Machine(int initialPaperLevel, int initialTonerLevel, int initialNoOfTicketsPrinted, ThreadGroup passengerGroup) {
        this.currentPaperLevel = initialPaperLevel;
        this.currentTonerLevel = initialTonerLevel;
        this.noOfTicketsPrinted = initialNoOfTicketsPrinted;
        this.passengers = passengerGroup;
    }

    public int getNoOfTicketsPrinted() {
        return noOfTicketsPrinted;
    }

    private boolean isResourceAvailable() {
        return (currentPaperLevel > 0 && currentTonerLevel >= MINIMUM_TONER_LEVEL);
    }

    @Override
    public void printTicket(Ticket ticket) {
        try {
            lock.lock();

            while (!isResourceAvailable()) {
                if (currentPaperLevel == 0) {
                    System.out.println("Insufficient paper for printing. " +
                            "Waiting for paper to be available.");
                }
                if (currentTonerLevel < MINIMUM_TONER_LEVEL) {
                    System.out.println("Insufficient toner for printing. " +
                            "Waiting for toner to be available.");
                }

                // Wait until there are enough resources to print a ticket.
                resourcesUnavailable.await();
            }

            // Deduct paper and toner levels for printing.
            this.currentPaperLevel--;
            this.currentTonerLevel--;

            // Increment number of tickets printed.
            this.noOfTicketsPrinted++;

            System.out.println("Ticket printed by : " + Thread.currentThread().getName() + " | Ticket: " + ticket);

            paperAvailable.signalAll();
            tonerAvailable.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread interrupted while printing ticket paper", e);
        } finally {
            lock.unlock();
        }
    }

    public void refillTicketPaper() {
        try {
            lock.lock();

            // Check if adding a new pack would exceed paper tray capacity. If true, no refill needed.
            while ((currentPaperLevel + SHEETS_PER_PACK) > FULL_PAPER_TRAY) {
                if (passengers.activeCount() > 0) {
                    System.out.println("Have paper, no need to refill. Current paper level is : " + currentPaperLevel + " sheets");
                    paperAvailable.await(5, TimeUnit.SECONDS);
                } else {
                    // No requests for printing tickets. so no need to refill.
                    System.out.println("No requests for printing tickets. so no need to refill paper.");
                    return;
                }
            }

            // Refill paper.
            currentPaperLevel += SHEETS_PER_PACK;
            System.out.println(Thread.currentThread().getName() +
                    " refilled the paper. Current paper level is : " + currentPaperLevel + " sheets");

            // Signal availability of paper.
            resourcesUnavailable.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread interrupted while refilling ticket paper", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void replaceTonerCartridge() {
        try {
            lock.lock();

            // Wait until toner level is below minimum level.
            while (currentTonerLevel >= MINIMUM_TONER_LEVEL) {
                if (passengers.activeCount() > 0) {
                    System.out.println("Have Toner, No need to replace the toner, Current toner Level is : " + currentTonerLevel);
                    tonerAvailable.await(5, TimeUnit.SECONDS);
                } else {
                    // No requests for printing tickets. so no need to replace toner.
                    System.out.println("No requests for printing tickets. so no need to replace toner.");
                    return;
                }
            }

            // Replace toner cartridge.
            currentTonerLevel = FULL_TONER_LEVEL;
            System.out.println(Thread.currentThread().getName() +
                    " replaced the toner cartridge. Toner level reset to : " + currentTonerLevel);

            // Signal availability of toner.
            resourcesUnavailable.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread interrupted while replacing toner", e);
        } finally {
            lock.unlock();
        }
    }
}
