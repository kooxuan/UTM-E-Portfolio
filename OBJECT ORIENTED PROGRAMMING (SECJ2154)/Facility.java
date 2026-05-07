public abstract class Facility implements Bookable {
    protected String facilityId;
    protected String name;
    protected int capacity;
    protected String operatingHours;
    protected boolean isOperational;
    
    public Facility(String facilityId, String name, int capacity, String operatingHours) {
        this.facilityId = facilityId;
        this.name = name;
        this.capacity = capacity;
        this.operatingHours = operatingHours;
        this.isOperational = true;
    }
    
    // Abstract method for facility-specific rules
    public abstract boolean checkAvailabilityRules(String timeSlot);
    
    // Getters (Encapsulation)
    public String getFacilityId() { return facilityId; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public String getOperatingHours() { return operatingHours; }
    public boolean isOperational() { return isOperational; }
    
    public void setOperational(boolean operational) { this.isOperational = operational; }
    
    @Override
    public boolean isAvailable(String timeSlot) {
        return isOperational && checkAvailabilityRules(timeSlot);
    }
}