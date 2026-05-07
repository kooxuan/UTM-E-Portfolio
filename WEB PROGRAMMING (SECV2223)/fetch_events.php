<?php
// fetch_events.php
require_once 'config.php';

header('Content-Type: application/json');

// Fetch all events from the database
$stmt = $pdo->prepare("SELECT id, title, location, datetime, description, poster FROM events ORDER BY datetime DESC");
$stmt->execute();

$events = $stmt->fetchAll(PDO::FETCH_ASSOC);
echo json_encode($events);
?>
