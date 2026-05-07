import java.util.*;

public class User extends Person {
    private final String userType; // "STUDENT" or "STAFF"
    private final List<String> bookingHistory;
    
    public User(String id, String name, String email, String phoneNumber, String userType) {
        super(id, name, email, phoneNumber);
        this.userType = userType;
        this.bookingHistory = new ArrayList<>();
    }
    
    @Override
    public String getRole() {
        return userType;
    }
    
    public void addToBookingHistory(String bookingId) {
        bookingHistory.add(bookingId);
    }
    
    public List<String> getBookingHistory() {
        return new ArrayList<>(bookingHistory); // Return copy to maintain encapsulation
    }
}
