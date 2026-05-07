<?php
session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>What's Happening UTM - Debuggers</title>
  <link rel="stylesheet" href="style.css" />
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;500;700&display=swap" rel="stylesheet" />
  <script defer src="script.js"></script>
</head>
<body>
  <header>
    <div class="navbar">
      <div class="logo">
        <img src="logo.jpg" alt="Debuggers Logo" />
        <h1>What's Happening UTM</h1>
      </div>
      <nav>
        <ul>
          <li><a href="login.html">Login</a></li>
          <li><a href="profile.html">Profile</a></li>
          <li><a href="event-view.html">Events</a></li>
        </ul>
      </nav>
    </div>
  </header>

  <main>
    <section class="hero">
      <h2>Discover Events Around UTM</h2>
      <p>Find club activities, food fairs, tech talks and more — all in real-time!</p>
      <div class="buttons">
        <a href="post-event.html" class="btn-primary">Post an Event</a>
        <a href="#search-section" class="btn-secondary">Browse Events</a>
      </div>
    </section>

    <section id="search-section" class="search-filter">
      <div class="search-wrapper">
        <input type="text" id="search-input" placeholder="Search for events..." />
        <ul id="search-suggestions" class="suggestions-list"></ul>
      </div>
      <select id="category-select">
        <option value="all">All Categories</option>
        <option value="food">Food</option>
        <option value="club">Club</option>
        <option value="talks">Talks</option>
        <option value="sports">Sports</option>
      </select>
    </section>

    <section class="events">
      <h3>Recent Events</h3>
      <div class="event-grid" id="event-grid">
        <a href="event-view.html" class="event-card" data-category="food">
          <img src="food.jpeg" alt="Food Fair" />
          <div class="card-info">
            <h4>UTM Food Fiesta</h4>
            <p>Enjoy local food & drinks in Scholar's Square!</p>
          </div>
        </a>
        <a href="event-view.html" class="event-card" data-category="talks">
          <img src="robotic.jpg" alt="AI and Robotics Festival" />
          <div class="card-info">
            <h4>AI & Robotics Festival</h4>
            <p>Explore the future of technology with AIRFEST 2025.</p>
          </div>
        </a>
        <a href="event-view.html" class="event-card" data-category="talks">
          <img src="spikem.jpg" alt="SPIKEM 2025" />
          <div class="card-info">
            <h4>SPIKEM 2025</h4>
            <p>Islamic civilization seminar promoting sustainable development.</p>
          </div>
        </a>
        <a href="event-view.html" class="event-card" data-category="talks">
          <img src="stem.jpeg" alt="Wacana Intelektual Madani" />
          <div class="card-info">
            <h4>Wacana Intelektual Madani</h4>
            <p>STEM Madani: Driving innovation for a sustainable future.</p>
          </div>
        </a>
        <a href="event-view.html" class="event-card" data-category="talks">
          <img src="fcri.webp" alt="Faculty of Computing Research and Innovation Day" />
          <div class="card-info">
            <h4>FCRI Day 2025</h4>
            <p>Discover cutting-edge research from the Faculty of Computing.</p>
          </div>
        </a>
      </div>

      <div style="text-align:center; margin-top: 20px;">
        <a href="event-view.html" class="btn-primary">View All Events</a>
      </div>
    </section>
  </main>

  <footer>
    <div class="footer-content">
      <p>&copy; 2025 Debuggers | What's Happening UTM</p>
      <p>Contact: hello@debuggers.com | (601) 234-5678</p>
    </div>
  </footer>
</body>
</html>
