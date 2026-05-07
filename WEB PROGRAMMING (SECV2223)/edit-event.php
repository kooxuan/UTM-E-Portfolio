<?php
// edit-event.php
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

$event = null;
$event_id = null;

// Get event data if ID is provided
if (isset($_GET['id']) && is_numeric($_GET['id'])) {
    $event_id = $_GET['id'];
    
    try {
        $stmt = $pdo->prepare("SELECT * FROM events WHERE id = ?");
        $stmt->execute([$event_id]);
        $event = $stmt->fetch(PDO::FETCH_ASSOC);
        
        if (!$event) {
            $_SESSION['message'] = "Event not found!";
            $_SESSION['message_type'] = "error";
            header("Location: event-view.html");
            exit();
        }
    } catch(PDOException $e) {
        die("Error fetching event: " . $e->getMessage());
    }
}

// Handle form submission
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $title = trim($_POST['title']);
    $location = trim($_POST['location']);
    $datetime = $_POST['datetime'];
    $description = trim($_POST['description']);
    $event_id = $_POST['event_id'];
    
    $poster_filename = null;
    
    // Handle file upload
    if (isset($_FILES['poster']) && $_FILES['poster']['error'] === UPLOAD_ERR_OK) {
        $upload_dir = 'uploads/';
        
        // Create uploads directory if it doesn't exist
        if (!is_dir($upload_dir)) {
            mkdir($upload_dir, 0755, true);
        }
        
        $file_extension = strtolower(pathinfo($_FILES['poster']['name'], PATHINFO_EXTENSION));
        $allowed_extensions = ['jpg', 'jpeg', 'png', 'gif'];
        
        if (in_array($file_extension, $allowed_extensions)) {
            $poster_filename = uniqid() . '.' . $file_extension;
            $upload_path = $upload_dir . $poster_filename;
            
            if (move_uploaded_file($_FILES['poster']['tmp_name'], $upload_path)) {
                // Delete old poster if it exists
                $stmt = $pdo->prepare("SELECT poster FROM events WHERE id = ?");
                $stmt->execute([$event_id]);
                $old_event = $stmt->fetch(PDO::FETCH_ASSOC);
                
                if ($old_event && $old_event['poster'] && file_exists('uploads/' . $old_event['poster'])) {
                    unlink('uploads/' . $old_event['poster']);
                }
            }
        }
    }
    
    try {
        if ($poster_filename) {
            // Update with new poster
            $stmt = $pdo->prepare("UPDATE events SET title = ?, location = ?, datetime = ?, description = ?, poster = ? WHERE id = ?");
            $stmt->execute([$title, $location, $datetime, $description, $poster_filename, $event_id]);
        } else {
            // Update without changing poster
            $stmt = $pdo->prepare("UPDATE events SET title = ?, location = ?, datetime = ?, description = ? WHERE id = ?");
            $stmt->execute([$title, $location, $datetime, $description, $event_id]);
        }
        
        $_SESSION['message'] = "Event updated successfully!";
        $_SESSION['message_type'] = "success";
        header("Location: event-view.html");
        exit();
        
    } catch(PDOException $e) {
        $_SESSION['message'] = "Error updating event: " . $e->getMessage();
        $_SESSION['message_type'] = "error";
    }
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Event - What's Happening UTM</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f5f5f5;
        }

        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #333;
            padding: 10px 0;
            color: white;
        }

        .navbar .logo {
            display: flex;
            align-items: center;
            padding-left: 20px;
        }

        .navbar .logo img {
            width: 40px;
            height: 40px;
            margin-right: 10px;
            border-radius: 50%;
        }

        .navbar ul {
            list-style: none;
            display: flex;
            gap: 20px;
            margin: 0;
            padding: 0;
            padding-right: 20px;
        }

        .navbar ul li a {
            color: white;
            text-decoration: none;
            font-weight: 500;
        }

        .container {
            max-width: 800px;
            margin: 40px auto;
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            color: #800000;
            text-align: center;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
            color: #333;
        }

        input[type="text"],
        input[type="datetime-local"],
        textarea,
        input[type="file"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }

        textarea {
            height: 120px;
            resize: vertical;
        }

        .btn {
            display: inline-block;
            padding: 12px 30px;
            background-color: #800000;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-right: 10px;
        }

        .btn:hover {
            background-color: #600000;
        }

        .btn-secondary {
            background-color: #666;
        }

        .btn-secondary:hover {
            background-color: #555;
        }

        .current-poster {
            margin-top: 10px;
        }

        .current-poster img {
            max-width: 200px;
            border-radius: 5px;
        }

        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
        }

        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <header>
        <div class="navbar">
            <div class="logo">
                <img src="logo.jpg" alt="Debuggers Logo">
                <h1>What's Happening UTM</h1>
            </div>
            <nav>
                <ul>
                    <li><a href="index.php">Home</a></li>
                    <li><a href="profile.html">Profile</a></li>
                    <li><a href="post-event.html">Post Event</a></li>
                    <li><a href="event-view.html">View Events</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <div class="container">
        <h2>Edit Event</h2>
        
        <?php if (isset($_SESSION['message'])): ?>
            <div class="message <?php echo $_SESSION['message_type']; ?>">
                <?php 
                echo $_SESSION['message']; 
                unset($_SESSION['message']);
                unset($_SESSION['message_type']);
                ?>
            </div>
        <?php endif; ?>

        <?php if ($event): ?>
        <form method="POST" enctype="multipart/form-data">
            <input type="hidden" name="event_id" value="<?php echo $event['id']; ?>">
            
            <div class="form-group">
                <label for="title">Event Title</label>
                <input type="text" id="title" name="title" value="<?php echo htmlspecialchars($event['title']); ?>" required>
            </div>

            <div class="form-group">
                <label for="location">Location</label>
                <input type="text" id="location" name="location" value="<?php echo htmlspecialchars($event['location']); ?>" required>
            </div>

            <div class="form-group">
                <label for="datetime">Date & Time</label>
                <input type="datetime-local" id="datetime" name="datetime" value="<?php echo date('Y-m-d\TH:i', strtotime($event['datetime'])); ?>" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" required><?php echo htmlspecialchars($event['description']); ?></textarea>
            </div>

            <div class="form-group">
                <label for="poster">Event Poster (Leave empty to keep current poster)</label>
                <input type="file" id="poster" name="poster" accept="image/*">
                
                <?php if ($event['poster']): ?>
                <div class="current-poster">
                    <p><strong>Current Poster:</strong></p>
                    <img src="uploads/<?php echo $event['poster']; ?>" alt="Current Poster">
                </div>
                <?php endif; ?>
            </div>

            <button type="submit" class="btn">Update Event</button>
            <a href="event-view.html" class="btn btn-secondary">Cancel</a>
        </form>
        <?php else: ?>
        <p>Event not found or invalid ID provided.</p>
        <a href="event-view.html" class="btn">Back to Events</a>
        <?php endif; ?>
    </div>
</body>
</html>