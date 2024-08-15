document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Previne o comportamento padrão do form

    var login = document.getElementById('login').value;
    var password = document.getElementById('password').value;

    if (login === '' || password === '') {
        alert('Please, enter all fields.');
        return;
    }

    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ login: login, password: password }),
    })
    .then(response => response.text())
    .then(page => {
        if (page.includes("home")) {
            window.location.href = `/${page}`;
        } else {
            alert("Invalid credentials");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("An error occurred. Please try again.");
    });
});
