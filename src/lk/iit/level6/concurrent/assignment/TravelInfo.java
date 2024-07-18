package lk.iit.level6.concurrent.assignment;

public class TravelInfo {
    private String startingPoint;
    private String destination;
    private String date;
    private String time;

    public TravelInfo(String startingPoint, String destination, String date, String time) {
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.date = date;
        this.time = time;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    @Override
    public String toString() {
        return "startingPoint: " + startingPoint +
                " destination: " + destination +
                ", date: " + date +
                ", time: " + time;
    }
}
