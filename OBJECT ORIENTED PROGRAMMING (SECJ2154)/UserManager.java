import java.util.*;

public class UserManager {
    private final List<User> users; // Aggregation - UserManager uses Users
    
    public UserManager() {
        this.users = new ArrayList<>();
    }
        
    public User authenticateUser(String userId) throws InvalidUserException {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        throw new InvalidUserException(userId);
    }
    
    public void addUser(User user) {
        users.add(user);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Return copy to maintain encapsulation
    }
}