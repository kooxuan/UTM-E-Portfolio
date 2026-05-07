<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

$host = "sql203.infinityfree.com";
$dbname = "if0_39238044_utm_events"; 
$username = "if0_39238044";   
$password = "group4DEBUGGERS";

try {
    $conn = new mysqli($host, $username, $password, $dbname);

    if ($conn->connect_error) {
        throw new Exception("Connection failed: " . $conn->connect_error);
    }
    
    // Get form data
    $title = $_POST['title'] ?? '';
    $location = $_POST['location'] ?? '';
    //$datetime = $_POST['datetime'] ?? '';
	$datetime = $_POST['datetime'] ?? '';
	if ($datetime !== '') {
    $datetime = str_replace('T', ' ', $datetime) . ':00';
	}
    $description = $_POST['description'] ?? '';
    
    // Handle image upload
    $targetDir = "uploads/";

    if (!is_dir($targetDir)) {
        if (!mkdir($targetDir, 0755, true)) {
            throw new Exception("Failed to create uploads directory.");
        }
    }
    
     if (!isset($_FILES["poster"]) || $_FILES["poster"]["error"] !== UPLOAD_ERR_OK) {
        throw new Exception("File upload error");
    }
    
    $imageName = basename($_FILES["poster"]["name"]);
    $imageFileType = strtolower(pathinfo($imageName, PATHINFO_EXTENSION));
    
    $allowedTypes = ['jpg', 'jpeg', 'png', 'gif'];
    if (!in_array($imageFileType, $allowedTypes)) {
        throw new Exception("Only JPG, PNG, or GIF images are allowed.");
    }
    
    if ($_FILES["poster"]["size"] > 5000000) {
        throw new Exception("File is too large. Maximum size is 5MB.");
    }
    
    $uniqueName = time() . '_' . $imageName;
    $targetFile = $targetDir . $uniqueName;
    
    if (move_uploaded_file($_FILES["poster"]["tmp_name"], $targetFile)) {
        // Insert into the correct columns based on your database schema
        $sql = "INSERT INTO events (title, location, `datetime`, description, poster) VALUES (?, ?, ?, ?, ?)";
        
        $stmt = $conn->prepare($sql);
        
        if (!$stmt) {
            throw new Exception("Prepare failed: " . $conn->error);
        }
        
        $stmt->bind_param("sssss", $title, $location, $datetime, $description, $uniqueName);
        
        if ($stmt->execute()) {
            echo "✅ Event posted successfully!";
        } else {
            throw new Exception("Error saving to database: " . $stmt->error);
        }
        
        $stmt->close();
    } else {
        throw new Exception("Failed to upload image.");
    }
    
} catch (Exception $e) {
    echo "❌ Error: " . $e->getMessage();
} finally {
    if (isset($conn)) {
        $conn->close();
    }
}
?>