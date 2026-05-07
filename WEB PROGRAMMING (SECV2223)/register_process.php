<?php
// register_process.php - Handle user registration
require_once 'config.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = trim($_POST['username']);
    $email = trim($_POST['email']);
    $password = $_POST['password'];
    
    // Basic validation
    if (empty($username) || empty($email) || empty($password)) {
        echo "Please fill in all fields!";
        exit;
    }
    
    // Check if username or email already exists
    $stmt = $pdo->prepare("SELECT id FROM users WHERE username = ? OR email = ?");
    $stmt->execute([$username, $email]);
    
    if ($stmt->rowCount() > 0) {
        echo "Username or email already exists!";
        exit;
    }

    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);
    
    // Insert new user
    try {
        $stmt = $pdo->prepare("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
		$stmt->execute([$username, $email, $hashedPassword]);
        
        echo "success";
    } catch (PDOException $e) {
        echo "Registration failed: " . $e->getMessage();
    }
}
?>