document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault(); // Previne o comportamento padrão do form

    var login = document.getElementById('login').value;
    var password = document.getElementById('password').value;

    // Verifica se os campos de login e senha estão preenchidos
    if (login === '' || password === '') {
        alert('Please, enter all fields.');
        return;
    }

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email: login, password: password }),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json(); // Converte a resposta em JSON
    })
    .then(data => {
        // Verifica se o token está presente na resposta

        
        if (data.token) {
            // Armazena o token e o email do usuário no localStorage
            localStorage.setItem('authToken', data.token);
            localStorage.setItem('userEmail', login); 

            // Redireciona para a página do advogado
            window.location.href = '/HomePages/HomeLawyer/indexHomeLawyer.html';
        } else {
            alert("Invalid credentials");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("An error occurred. Please try again.");
    });
});
