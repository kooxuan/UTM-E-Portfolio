public class InvalidUserException extends BookingException {
    public InvalidUserException(String userId) {
        super("User with Matric No. / Staff ID " + userId + " is not valid or not found.");
    }
}


