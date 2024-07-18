package lk.iit.level6.concurrent.assignment;

public class PassengerInfo {
    private String passengerName;
    private String passengerAddress;
    private String passengerContactNumber;

    public PassengerInfo(String passengerName, String passengerAddress, String passengerContactNumber) {
        this.passengerName = passengerName;
        this.passengerAddress = passengerAddress;
        this.passengerContactNumber = passengerContactNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerAddress() {
        return passengerAddress;
    }

    public void setPassengerAddress(String passengerAddress) {
        this.passengerAddress = passengerAddress;
    }

    public String getPassengerContactNumber() {
        return passengerContactNumber;
    }

    public void setPassengerContactNumber(String passengerContactNumber) {
        this.passengerContactNumber = passengerContactNumber;
    }
}
