import java.util.*;

public class FacilityManager {
    private final List<Facility> facilities; // Aggregation - FacilityManager uses Facilities
    
    public FacilityManager() {
        this.facilities = new ArrayList<>();
        initializeFacilities();
    }
    
    private void initializeFacilities() {
        facilities.add(new Gymnasium("GYM001", "Gym", 30, "08:00-22:00"));
        facilities.add(new SwimmingPool("POOL001", "Swimming Pool", 30, "09:00-17:00", 8));
        facilities.add(new SportsComplex("SPORTS001", "Sports Hall", 50, "08:00-21:00"));
        facilities.add(new Clinic("CLINICM", "PKU (Medical)", 20, "00:00-23:59", "General"));
        facilities.add(new Clinic("CLINICD", "PKU (Dental)", 3, "09:00-17:00", "Dental"));
    }
    
    public Facility getFacilityById(String facilityId) {
        for (Facility facility : facilities) {
            if (facility.getFacilityId().equals(facilityId)) {
                return facility;
            }
        }
        return null;
    }
    
    public List<Facility> getAllFacilities() {
        return new ArrayList<>(facilities); // Return copy to maintain encapsulation
    }
    
    public List<Facility> getAvailableFacilities(String timeSlot) {
        List<Facility> available = new ArrayList<>();
        for (Facility facility : facilities) {
            if (facility.isAvailable(timeSlot)) {
                available.add(facility);
            }
        }
        return available;
    }
}