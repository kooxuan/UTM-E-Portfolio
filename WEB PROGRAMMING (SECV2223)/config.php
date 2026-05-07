<?php
// config.php
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

function isLoggedIn() {
    return isset($_SESSION['user_id']);
}

function getCurrentUser() {
    if (isLoggedIn()) {
        return [
            'id' => $_SESSION['user_id'],
            'username' => $_SESSION['username'],
            'email' => $_SESSION['email'],
        ];
    }
    return null;
}

function requireLogin() {
    if (!isLoggedIn()) {
        header('Location: login.html');
        exit();
    }
}
?>