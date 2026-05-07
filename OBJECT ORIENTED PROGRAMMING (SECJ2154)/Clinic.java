public class Clinic extends Facility {
    private final String clinicType;
    private static int bookingCounter = 1; // Static counter for booking IDs
    
    public Clinic(String facilityId, String name, int capacity, String operatingHours, String clinicType) {
        super(facilityId, name, capacity, operatingHours);
        this.clinicType = clinicType;
    }
    
    @Override
    public boolean checkAvailabilityRules(String timeSlot) {
        int hour = Integer.parseInt(timeSlot.split(":")[0]);
        return hour >= 9 && hour <= 17; // Open 9 AM to 5 PM
    }
    
    @Override
    public boolean makeBooking(User user, String timeSlot, int duration) {
        if (isAvailable(timeSlot)) {
            String bookingId = generateClinicBookingId();
            user.addToBookingHistory(bookingId);
            System.out.println("Clinic appointment booked successfully. Booking ID: " + bookingId);
            return true;
        }
        return false;
    }

    private static synchronized String generateClinicBookingId() {
        String formattedNumber = String.format("%05d", bookingCounter);
        bookingCounter++;
        return "CLINIC" + formattedNumber;
    }
    
    @Override
    public void cancelBooking(String bookingId) {
        System.out.println("Appointment " + bookingId + " cancelled for " + name);
    }
    
    @Override
    public String getBookingDetails(String bookingId) {
        return "Clinic appointment details for " + bookingId + " - Type: " + clinicType;
    }
    
    public String getClinicType() {
        return clinicType;
    }
}