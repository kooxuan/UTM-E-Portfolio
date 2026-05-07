public class BookingNotFoundException extends Exception {
    public BookingNotFoundException(String bookingId) {
        super("Booking with ID " + bookingId + " was not found.");
    }
}


