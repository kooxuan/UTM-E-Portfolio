// auth.js - JavaScript for authentication pages
function showMessage(message, type) {
    const messageDiv = document.getElementById('message');
    messageDiv.innerHTML = `<div class="alert alert-${type}">${message}</div>`;

    setTimeout(() => {
        messageDiv.innerHTML = '';
    }, 3000);
}

if (document.getElementById('registerForm')) {
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = new FormData(this);
        const password = formData.get('password');
        const confirmPassword = formData.get('confirm_password');

        if (password !== confirmPassword) {
            showMessage('Passwords do not match!', 'error');
            return;
        }

        if (password.length < 6) {
            showMessage('Password must be at least 6 characters long!', 'error');
            return;
        }

        const email = formData.get('email');
        if (!email.includes('@graduate.utm.my')) {
            showMessage('Please use a valid UTM email address!', 'error');
            return;
        }

        fetch('register_process.php', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(data => {
            if (data.includes('success')) {
                showMessage('Registration successful! Redirecting to login...', 'success');
                setTimeout(() => {
                    window.location.href = 'login.html';
                }, 2000);
            } else {
                showMessage(data, 'error');
            }
        })
        .catch(error => {
            showMessage('Registration failed. Please try again.', 'error');
        });
    });
}

if (document.getElementById('loginForm')) {
    document.getElementById('loginForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = new FormData(this);
        
        fetch('login_process.php', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(data => {
            if (data.includes('success')) {
                showMessage('Login successful! Redirecting...', 'success');
                setTimeout(() => {
                    window.location.href = 'profile.html';
                }, 1000);
            } else {
                showMessage(data, 'error');
            }
        })
        .catch(error => {
            showMessage('Login failed. Please try again.', 'error');
        });
    });
}

function loadProfile() {
    fetch('profile_data.php')
    .then(response => response.json())
    .then(data => {
        if (data.error) {
            showMessage(data.error, 'error');
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 2000);
        } else {
            document.getElementById('display-username').textContent = data.username;
            document.getElementById('display-email').textContent = data.email;
            document.getElementById('display-created').textContent = new Date(data.created_at).toLocaleDateString();
        }
    })
    .catch(error => {
        showMessage('Failed to load profile data.', 'error');
    });
}

function logout() {
    if (confirm('Are you sure you want to logout?')) {
        fetch('logout_process.php')
        .then(response => response.text())
        .then(data => {
            showMessage('Logged out successfully!', 'success');
            setTimeout(() => {
                window.location.href = 'login.html';
            }, 1000);
        })
        .catch(error => {
            showMessage('Logout failed.', 'error');
        });
    }
}