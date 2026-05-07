import java.util.*;

public class SportsComplex extends Facility {
    private final List<String> availableCourts;
    private static int bookingCounter = 1;  // Facility-specific counter
    
    public SportsComplex(String facilityId, String name, int capacity, String operatingHours) {
        super(facilityId, name, capacity, operatingHours);
        this.availableCourts = new ArrayList<>();
        availableCourts.add("Basketball Court");
        availableCourts.add("Badminton Court");
        availableCourts.add("Tennis Court");
        availableCourts.add("Squash Court");
    }
    
    @Override
    public boolean checkAvailabilityRules(String timeSlot) {
        int hour = Integer.parseInt(timeSlot.split(":")[0]);
        return hour >= 8 && hour <= 20; // Open 8 AM to 8 PM
    }
    
    @Override
    public boolean makeBooking(User user, String timeSlot, int duration) {
        if (isAvailable(timeSlot)) {
            String bookingId = generateSportsBookingId();
            user.addToBookingHistory(bookingId);
            System.out.println("Sports complex booked successfully. Booking ID: " + bookingId);
            return true;
        }
        return false;
    }

    private static synchronized String generateSportsBookingId() {
        String formattedNumber = String.format("%05d", bookingCounter);
        bookingCounter++;
        return "SPORTS" + formattedNumber;
    }
    
    @Override
    public void cancelBooking(String bookingId) {
        System.out.println("Booking " + bookingId + " cancelled for " + name);
    }
    
    @Override
    public String getBookingDetails(String bookingId) {
        return "Sports complex booking details for " + bookingId;
    }
    
    public List<String> getAvailableCourts() {
        return new ArrayList<>(availableCourts);
    }
}