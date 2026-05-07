public interface Bookable {
    boolean isAvailable(String timeSlot);
    boolean makeBooking(User user, String timeSlot, int duration);
    void cancelBooking(String bookingId);
    String getBookingDetails(String bookingId);
}


