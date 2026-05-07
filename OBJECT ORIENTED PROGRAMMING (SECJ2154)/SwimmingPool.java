public class SwimmingPool extends Facility {
    private final int numberOfLanes;
    private static int bookingCounter = 1;
    
    public SwimmingPool(String facilityId, String name, int capacity, String operatingHours, int numberOfLanes) {
        super(facilityId, name, capacity, operatingHours);
        this.numberOfLanes = numberOfLanes;
    }
    
    @Override
    public boolean checkAvailabilityRules(String timeSlot) {
        // Specific rules for swimming pool
        int hour = Integer.parseInt(timeSlot.split(":")[0]);
        return hour >= 8 && hour <= 17; // Open 7 AM to 9 PM
    }
    
    @Override
    public boolean makeBooking(User user, String timeSlot, int duration) {
        if (isAvailable(timeSlot)) {
            String bookingId = generateSwimmingPoolBookingId();
            user.addToBookingHistory(bookingId);
            System.out.println("Swimming pool booked successfully. Booking ID: " + bookingId);
            return true;
        }
        return false;
    }

    private static synchronized String generateSwimmingPoolBookingId() {
        String formattedNumber = String.format("%05d", bookingCounter);
        bookingCounter++;
        return "POOL" + formattedNumber;
    }
    
    @Override
    public void cancelBooking(String bookingId) {
        System.out.println("Booking " + bookingId + " cancelled for " + name);
    }
    
    @Override
    public String getBookingDetails(String bookingId) {
        return "Swimming pool booking details for " + bookingId + " - Lanes available: " + numberOfLanes;
    }
    
    public int getNumberOfLanes() {
        return numberOfLanes;
    }
}