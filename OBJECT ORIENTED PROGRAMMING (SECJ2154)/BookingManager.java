import java.util.*;

public class BookingManager {

    private final List<Booking> bookings; // Composition - BookingManager contains Bookings
    private final List<WaitlistEntry> waitlist; // Composition - BookingManager contains WaitlistEntries
    
   private final static Map<String, Integer> bookingCounters = new HashMap<>();

    public BookingManager() {
        this.bookings = new ArrayList<>();
        this.waitlist = new ArrayList<>();
    }

    private static synchronized String generateBookingId(Facility facility) {
        String facilityPrefix = "";
        if (facility instanceof Gymnasium) {
            facilityPrefix = "GYM";
        } else if (facility instanceof SwimmingPool) {
            facilityPrefix = "POOL";
        } else if (facility instanceof SportsComplex) {
            facilityPrefix = "SPORTS";
        } else if (facility instanceof Clinic) {
            facilityPrefix = "CLINIC";
        }
        
        int currentCount = bookingCounters.getOrDefault(facilityPrefix, 0) + 1;
        bookingCounters.put(facilityPrefix, currentCount);
        return facilityPrefix + String.format("%05d", currentCount);
    }

    
    public boolean createBooking(User user, Facility facility, String timeSlot, int duration) throws BookingException {
        if (user == null) {
            throw new InvalidUserException("null");
        }
        
        if (!facility.isAvailable(timeSlot)) {
            // Add to waitlist
            String waitlistId = "WAIT-" + System.currentTimeMillis();
            WaitlistEntry entry = new WaitlistEntry(waitlistId, user, facility, timeSlot);
            waitlist.add(entry);
            user.sendNotification("Added to waitlist for " + facility.getName() + " at " + timeSlot);
            throw new FacilityNotAvailableException(facility.getName());
        }
        
        String bookingId = generateBookingId(facility);
    
        Booking booking = new Booking(bookingId, user, facility, timeSlot, duration);
        bookings.add(booking);
        
        // Make the actual booking
        boolean success = facility.makeBooking(user, timeSlot, duration);
        if (success) {
            user.sendNotification("Booking confirmed for " + facility.getName() + " at " + timeSlot);
            return true;
        }
        return false;
    }
    
    public void cancelBooking(String bookingId) throws BookingNotFoundException {
        Booking bookingToCancel = null;
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                bookingToCancel = booking;
                break;
            }
        }
        
        if (bookingToCancel == null) {
            throw new BookingNotFoundException(bookingId);
        }
        
        bookingToCancel.cancelBooking();
        bookingToCancel.getUser().sendNotification("Your booking " + bookingId + " has been cancelled.");
        
        // Check waitlist for this facility and time slot
        processWaitlist(bookingToCancel.getFacility(), bookingToCancel.getTimeSlot());
    }
    
    private void processWaitlist(Facility facility, String timeSlot) {
        for (int i = 0; i < waitlist.size(); i++) {
            WaitlistEntry entry = waitlist.get(i);
            if (entry.getFacility().equals(facility) && entry.getTimeSlot().equals(timeSlot)) {
                User waitlistUser = entry.getUser();
                waitlistUser.sendNotification("A slot is now available for " + facility.getName() + " at " + timeSlot + 
                                            ". Reply to confirm booking within 30 minutes.");
                waitlist.remove(i);
                break;
            }
        }
    }
    
    public List<Booking> getUserBookings(User user) {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getUser().equals(user)) {
                userBookings.add(booking);
            }
        }
        return userBookings;
    }
    
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings); // Return copy to maintain encapsulation
    }
}