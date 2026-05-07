import java.util.*;

public class Gymnasium extends Facility {
    private static int bookingCounter = 1;

    private final List<String> equipmentZones;
    
    public Gymnasium(String facilityId, String name, int capacity, String operatingHours) {
        super(facilityId, name, capacity, operatingHours);
        this.equipmentZones = new java.util.ArrayList<>();
        equipmentZones.add("Cardio Zone");
        equipmentZones.add("Weight Training Zone");
        equipmentZones.add("Functional Training Zone");
    }
    
    @Override
    public boolean checkAvailabilityRules(String timeSlot) {
        // Specific rules for gymnasium
        int hour = Integer.parseInt(timeSlot.split(":")[0]);
        return hour >= 6 && hour <= 22; // Open 6 AM to 10 PM
    }
    
    @Override
    public boolean makeBooking(User user, String timeSlot, int duration) {
        if (isAvailable(timeSlot)) {
            String bookingId = generateGymBookingId();
            user.addToBookingHistory(bookingId);
            System.out.println("Gymnasium booked successfully. Booking ID: " + bookingId);
            return true;
        }
        return false;
    }

    private static synchronized String generateGymBookingId() {
        String formattedNumber = String.format("%05d", bookingCounter);
        bookingCounter++;
        return "GYM" + formattedNumber;
    }
    
    @Override
    public void cancelBooking(String bookingId) {
        System.out.println("Booking " + bookingId + " cancelled for " + name);
    }
    
    @Override
    public String getBookingDetails(String bookingId) {
        return "Gymnasium booking details for " + bookingId;
    }
    
    public List<String> getEquipmentZones() {
        return new ArrayList<>(equipmentZones);
    }
}