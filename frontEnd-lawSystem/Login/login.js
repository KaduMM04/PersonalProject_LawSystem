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

            // Faz uma requisição para obter informações sobre o usuário
            return fetch('http://localhost:8080/user/' + login, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${data.token}`, // Correção no uso de crase
                }
            });
        } else {
            alert("Invalid credentials");
            return Promise.reject('Invalid credentials');
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to fetch user information');
        }
        return response.json();
    })
    .then(userData => {
        // Redireciona para a página correta com base no tipo de usuário
        if (userData.role === 'ADMIN') {
            window.location.href = '/HomePages/HomeLawyer/indexHomeLawyer.html';
        } else if (userData.role === 'USERCLIENT') {
            window.location.href = '/HomePages/HomeClient/indexHomeClient.html';
        } else {
            alert('Unknown user role');
        }    
    })
    .catch(error => {
        console.error('Error:', error);
        alert("An error occurred. Please try again.");
    });
});
