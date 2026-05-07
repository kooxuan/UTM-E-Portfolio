import java.util.*;

class CampusFacilityBookingSystem {
    private final BookingManager bookingManager; // Composition
    private final FacilityManager facilityManager; // Composition
    private final UserManager userManager; // Composition
    
    public CampusFacilityBookingSystem() {
        this.bookingManager = new BookingManager();
        this.facilityManager = new FacilityManager();
        this.userManager = new UserManager();
    }
    
    // Polymorphism - method overloading
    public void displayFacilities() {
        System.out.println("\n=== All Facilities ===");
        for (Facility facility : facilityManager.getAllFacilities()) {
            System.out.println(facility.getName() + " (" + facility.getFacilityId() + ") - Capacity: " + 
                             facility.getCapacity() + ", Hours: " + facility.getOperatingHours());
        }
    }
    
    public void displayFacilities(String timeSlot) {
        System.out.println("\n=== Available Facilities at " + timeSlot + " ===");
        for (Facility facility : facilityManager.getAvailableFacilities(timeSlot)) {
            System.out.println(facility.getName() + " (" + facility.getFacilityId() + ") - Available");
        }
    }
    
    public boolean makeBooking(String userId, String facilityId, String timeSlot, int duration) {
        try {
            User user = userManager.authenticateUser(userId);
            Facility facility = facilityManager.getFacilityById(facilityId);
            
            if (facility == null) {
                System.out.println("Facility not found: " + facilityId);
                return false;
            }
            
            return bookingManager.createBooking(user, facility, timeSlot, duration);
            
        } catch (InvalidUserException e) {
            System.out.println("User error: " + e.getMessage());
            return false;
        }catch (BookingException e) {
            System.out.println("Booking error: " + e.getMessage());
            return false;
        }
    }
    
    public void cancelBooking(String bookingId) {
        try {
            bookingManager.cancelBooking(bookingId);
            System.out.println("BOOKING CANCELLED SUCCESSFULLY!!");
        } catch (BookingNotFoundException e) {
            System.out.println("Cancellation error: " + e.getMessage());
        }
    }
    
    public void displayUserBookings(String userId) {
        try {
            User user = userManager.authenticateUser(userId);
            List<Booking> userBookings = bookingManager.getUserBookings(user);
            
            System.out.println("\n=== Bookings for " + user.getName() + " ===");
            if (userBookings.isEmpty()) {
                System.out.println("NO BOOKING FOUND.");
            } else {
                for (Booking booking : userBookings) {
                    if (booking.getStatus().equals("CONFIRMED")) {
                        System.out.println("Booking ID: " + booking.getBookingId() + 
                                         ", Facility: " + booking.getFacility().getName() + 
                                         ", Time: " + booking.getTimeSlot() + 
                                         ", Status: " + booking.getStatus());
                    }
                }                
            }
        } catch (InvalidUserException e) {
            System.out.println("User error: " + e.getMessage());
        }
    }
        
    public void runInteractiveSystem() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("\n\n=== WELCOME TO CAMPUS FACILITY BOOKING SYSTEM ===");
        
        while (running) {
            displayMenu();
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        handleAddNewUser(scanner);
                        break;
                    case 2:
                        handleViewAllUsers();
                        break;
                    case 3:
                        handleViewAllFacilities();
                        break;
                    case 4:
                        handleMakeBooking(scanner);
                        break;
                    case 5:
                        handleCancelBooking(scanner);
                        break;
                    case 6:
                        handleViewUserBookings(scanner);
                        break;
                    case 7:
                        System.out.println("\nThank you for using Campus Facility Booking System!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        scanner.close();
    }
    
    private void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           CAMPUS BOOKING SYSTEM MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Add New User");
        System.out.println("2. View All Users");
        System.out.println("3. View All Facilities");
        System.out.println("4. Make a Booking");
        System.out.println("5. Cancel a Booking");
        System.out.println("6. View User Bookings");
        System.out.println("7. Exit");
        System.out.println("=".repeat(50));
    }
    
    private void handleViewAllFacilities() {
        displayFacilities();
    }
    
    private void handleMakeBooking(Scanner scanner) {
        System.out.println("\n=== Make a New Booking ===");
        
        // Show available users first
        System.out.println("Available Users:");
        for (User user : userManager.getAllUsers()) {
            System.out.println("- " + user.getId() + " (" + user.getName() + " - " + user.getRole() + ")");
        }
        
        System.out.print("Enter your Matric No. / Staff ID : ");
        String userId = scanner.nextLine();
        
        // Authenticate user immediately
        try {
            userManager.authenticateUser(userId);
        } catch (InvalidUserException e) {
            System.out.println("Invalid user: " + e.getMessage());
            return;  // Stop here and don't proceed if invalid
        }

        // Show available facilities
        System.out.println("\nAvailable Facilities:");
        for (Facility facility : facilityManager.getAllFacilities()) {
            System.out.println("- " + facility.getFacilityId() + " (" + facility.getName() + 
                             ") - Capacity: " + facility.getCapacity() + 
                             ", Hours: " + facility.getOperatingHours());
        }
        
        System.out.print("Enter Facility ID: ");
        String facilityId = scanner.nextLine();
        
        System.out.print("Enter time slot (HH:MM format): ");
        String timeSlot = scanner.nextLine();
        
        if (!isValidTimeFormat(timeSlot)) {
            System.out.println("Invalid time format. Please use HH:MM format.");
            return;
        }
        
        System.out.print("Enter duration in hours: ");
        try {
            int duration = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            boolean success = makeBooking(userId, facilityId, timeSlot, duration);
            if (success) {
                System.out.println("BOOKING SUCCESSFULLY!");
            } else {
                System.out.println("Booking failed. Please check the details and try again.");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Invalid duration. Please enter a number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    
    private void handleCancelBooking(Scanner scanner) {
        System.out.println("\n=== Cancel a Booking ===");
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine();
        
        if (bookingId.trim().isEmpty()) {
            System.out.println("Booking ID cannot be empty.");
            return;
        }
        
        cancelBooking(bookingId);
    }
    
    private void handleViewUserBookings(Scanner scanner) {
        System.out.println("\n=== View User Bookings ===");
        
        // Show available users
        System.out.println("Available Users:");
        for (User user : userManager.getAllUsers()) {
            System.out.println("- " + user.getId() + " (" + user.getName() + ")");
        }
        
        System.out.print("Enter Matric No. / Staff ID : ");
        String userId = scanner.nextLine();
        
        displayUserBookings(userId);
    }
    
    private void handleAddNewUser(Scanner scanner) {
        System.out.println("\n=== Add New User ===");
        
        System.out.print("Enter Matric No. / Staff ID : ");
        String userId = scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter User Type (STUDENT/STAFF): ");
        String userType = scanner.nextLine().toUpperCase();
        
        if (!userType.equals("STUDENT") && !userType.equals("STAFF")) {
            System.out.println("Invalid user type. Please enter STUDENT or STAFF.");
            return;
        }
        
        // Check if user already exists
        try {
            userManager.authenticateUser(userId);
            System.out.println("User with Matric No. / Staff ID " + userId + " already exists.");
            return;
        } catch (InvalidUserException e) {
            // User doesn't exist, we can add them
        }
        
        User newUser = new User(userId, name, email, phone, userType);
        userManager.addUser(newUser);
        System.out.println("\nUSER ADDED SUCCESSFULLY!\n");
        System.out.println("User Details: " + name + " (" + userId + ") - " + userType);
    }
    
    private void handleViewAllUsers() {
        System.out.println("\n=== All Registered Users ===");
        java.util.List<User> users = userManager.getAllUsers();
        
        if (users.isEmpty()) {
            System.out.println("No users registered.");
        } else {
            int count = 1;
            for (User user : users) {
                System.out.println(count + ". Matric No.  / Staff ID: " + user.getId() + 
                                   ", Name: " + user.getName() + 
                                   ", Email: " + user.getEmail() + 
                                   ", Type: " + user.getRole() + 
                                   ", Phone: " + user.getPhoneNumber());
                count++;
            }
        }
    }
    
    private boolean isValidTimeFormat(String timeSlot) {
        if (timeSlot == null || timeSlot.trim().isEmpty()) {
            return false;
        }
        
        String[] parts = timeSlot.split(":");
        if (parts.length != 2) {
            return false;
        }
        
        try {
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        CampusFacilityBookingSystem system = new CampusFacilityBookingSystem();
        
        // Run the interactive system
        system.runInteractiveSystem();
    }
    
}