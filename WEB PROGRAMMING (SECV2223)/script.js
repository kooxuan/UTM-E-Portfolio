document.addEventListener("DOMContentLoaded", () => {
  const searchInput = document.getElementById("search-input");
  const categorySelect = document.getElementById("category-select");
  const suggestionsList = document.getElementById("search-suggestions");
  const eventCards = document.querySelectorAll(".event-card");

  const events = [
    { title: "UTM Food Fiesta", category: "food" },
    { title: "AI & Innovation Night", category: "talks" },
    // Add more events here as needed
  ];

  // --- Original search suggestion logic ---
  searchInput.addEventListener("input", () => {
    const query = searchInput.value.toLowerCase();
    suggestionsList.innerHTML = "";

    if (query.length > 0) {
      const matches = events.filter(e => e.title.toLowerCase().includes(query));
      matches.forEach(match => {
        const li = document.createElement("li");
        li.textContent = match.title;
        li.addEventListener("click", () => {
          searchInput.value = match.title;
          filterEvents();
          suggestionsList.innerHTML = "";
          suggestionsList.style.display = "none"; // Hide after selection
        });
        suggestionsList.appendChild(li);
      });

      suggestionsList.style.display = matches.length > 0 ? "block" : "none";
    } else {
      suggestionsList.style.display = "none";
    }
  });

  // --- Added: Hide dropdown on outside click ---
  document.addEventListener("click", (e) => {
    if (!e.target.closest(".search-wrapper")) {
      suggestionsList.innerHTML = "";
      suggestionsList.style.display = "none";
    }
  });
  //x yh aku click luar search dia utup je suggestions

  // --- Original filter by category ---
  categorySelect.addEventListener("change", filterEvents);

  function filterEvents() {
    const searchQuery = searchInput.value.toLowerCase();
    const selectedCategory = categorySelect.value;

    eventCards.forEach(card => {
      const title = card.querySelector("h4").textContent.toLowerCase();
      const category = card.dataset.category;

      const matchesCategory = selectedCategory === "all" || selectedCategory === category;
      const matchesSearch = title.includes(searchQuery);

      card.style.display = (matchesCategory && matchesSearch) ? "block" : "none";
    });
  }
});

