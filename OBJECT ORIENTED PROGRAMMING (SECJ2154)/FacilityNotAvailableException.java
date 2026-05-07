public class FacilityNotAvailableException extends BookingException {
    public FacilityNotAvailableException(String facilityName) {
        super("Facility " + facilityName + " is not available at the requested time.");
    }
}


