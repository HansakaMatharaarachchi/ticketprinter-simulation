package lk.iit.level6.concurrent.assignment;

public interface TicketMachine {
    int FULL_PAPER_TRAY = 20;
    int SHEETS_PER_PACK = 5;
    int FULL_TONER_LEVEL = 15;
    int MINIMUM_TONER_LEVEL = 10;

    int getNoOfTicketsPrinted();
    void printTicket(Ticket ticket);
}
