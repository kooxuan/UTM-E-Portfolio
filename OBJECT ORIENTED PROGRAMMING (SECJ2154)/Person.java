public abstract class Person implements Notifiable {
    protected String id;
    protected String name;
    protected String email;
    protected String phoneNumber;
    
    public Person(String id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract String getRole();
    
    // Getters and setters (Encapsulation)
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    @Override
    public void sendNotification(String message) {
        System.out.println("Notification to " + name + " (" + email + "): " + message);
    }
}