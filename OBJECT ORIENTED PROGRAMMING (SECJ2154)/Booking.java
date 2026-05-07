import java.util.*;

public class Booking {
    private final String bookingId;
    private final User user; // Association with User
    private final Facility facility; // Association with Facility
    private final String timeSlot;
    private final int duration;
    private String status;
    private final Date bookingDate;
    
    public Booking(String bookingId, User user, Facility facility, String timeSlot, int duration) {
        this.bookingId = bookingId;
        this.user = user;
        this.facility = facility;
        this.timeSlot = timeSlot;
        this.duration = duration;
        this.status = "CONFIRMED";
        this.bookingDate = new Date();
    }
    
    // Getters and setters (Encapsulation)
    public String getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Facility getFacility() { return facility; }
    public String getTimeSlot() { return timeSlot; }
    public int getDuration() { return duration; }
    public String getStatus() { return status; }
    public java.util.Date getBookingDate() { return bookingDate; }
    
    public void setStatus(String status) { this.status = status; }
    
    public void cancelBooking() {
        this.status = "CANCELLED";
        facility.cancelBooking(bookingId);
    }
}


