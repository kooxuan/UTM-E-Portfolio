<?php
// delete-event.php
session_start();

// Database configuration
$host = "sql203.infinityfree.com";
$dbname = "if0_39238044_utm_events"; 
$username = "if0_39238044";   
$password = "group4DEBUGGERS";

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch(PDOException $e) {
    die("Connection failed: " . $e->getMessage());
}

if (isset($_GET['id']) && is_numeric($_GET['id'])) {
    $event_id = $_GET['id'];
    
    try {
        // First, get the poster filename to delete the file
        $stmt = $pdo->prepare("SELECT poster FROM events WHERE id = ?");
        $stmt->execute([$event_id]);
        $event = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if ($event) {
            // Delete the poster file if it exists
            if ($event['poster'] && file_exists('uploads/' . $event['poster'])) {
                unlink('uploads/' . $event['poster']);
            }
            
            // Delete the event from database
            $stmt = $pdo->prepare("DELETE FROM events WHERE id = ?");
            $stmt->execute([$event_id]);
            
            $message = "Event deleted successfully!";
            $messageType = "success";
        } else {
            $message = "Event not found!";
            $messageType = "error";
        }
    } catch(PDOException $e) {
        $message = "Error deleting event: " . $e->getMessage();
        $messageType = "error";
    }
} else {
    $message = "Invalid event ID!";
    $messageType = "error";
}

// Redirect back to event view with message
header("Location: event-view.html?message=" . urlencode($message) . "&type=" . $messageType);
exit();
?>