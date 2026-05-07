import java.util.*;

public class WaitlistEntry {
    private final String waitlistId;
    private final User user; // Association with User
    private final Facility facility; // Association with Facility
    private final String timeSlot;
    private final Date requestDate;
    
    public WaitlistEntry(String waitlistId, User user, Facility facility, String timeSlot) {
        this.waitlistId = waitlistId;
        this.user = user;
        this.facility = facility;
        this.timeSlot = timeSlot;
        this.requestDate = new Date();
    }
    
    // Getters (Encapsulation)
    public String getWaitlistId() { return waitlistId; }
    public User getUser() { return user; }
    public Facility getFacility() { return facility; }
    public String getTimeSlot() { return timeSlot; }
    public java.util.Date getRequestDate() { return requestDate; }
}
