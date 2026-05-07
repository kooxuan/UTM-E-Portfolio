<?php
// login_process.php - Handle user login
require_once 'config.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = trim($_POST['username']);
    $password = $_POST['password'];
    
    // Basic validation
    if (empty($username) || empty($password)) {
        echo "Please fill in all fields!";
        exit;
    }
    
    // Check user credentials
	$stmt = $pdo->prepare("SELECT id, username, email, password FROM users WHERE username = ? OR email = ?");
	$stmt->execute([$username, $username]);
    
    if ($stmt->rowCount() === 1) {
        $user = $stmt->fetch();
        
        // Verify password
        if (password_verify($password, $user['password'])) {
            // Set session variables
            $_SESSION['user_id'] = $user['id'];
            $_SESSION['username'] = $user['username'];
            $_SESSION['email'] = $user['email'];
            
            echo "success";
        } else {
            echo "Invalid password!";
        }
    } else {
        echo "User not found!";
    }
}
?>