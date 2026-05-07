<?php
// profile_data.php - Get user profile data (FIXED VERSION)
require_once 'config.php';

header('Content-Type: application/json');

// Check if user is logged in
if (!isLoggedIn()) {
    echo json_encode(['error' => 'Please log in to view your profile']);
    exit;
}

// Get user data from database
$stmt = $pdo->prepare("SELECT id, username, email, created_at FROM users WHERE id = ?");
$stmt->execute([$_SESSION['user_id']]);

if ($stmt->rowCount() === 1) {
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
    echo json_encode($user);
} else {
    echo json_encode(['error' => 'User not found']);
}
?>