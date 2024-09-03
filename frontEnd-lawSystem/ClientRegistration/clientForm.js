document.getElementById('clientForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var token = localStorage.getItem('authToken');

    if (!token) {
        console.error('Authenticated token not found.');
        return;
    }

    const name = document.getElementById('name').value;
    const cpf = document.getElementById('cpf').value;
    const cep = document.getElementById('cep').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const userRole = 'USERCLIENT';

    console.log('Name:', name);
    console.log('CPF:', cpf);
    console.log('CEP:', cep);
    console.log('Email:', email);
    console.log('Password:', password);

    const data = {
        name: name,
        cpf: Number(cpf),
        cep: cep,
        email: email,
        password: password,
        userRole: userRole
    };

    fetch('http://localhost:8080/client', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Request failed with status ' + response.status);
        }
        return response.json();
    })
    .then(clientData => {
        console.log('Client successfully registered:', clientData);
        alert('Client successfully registered.');

        const dataUser = {
            email: data.email,
            password: data.password,
            role: data.userRole
        };

        return fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dataUser)
        }).then(response => {
            // Store dataUser in the outer scope for later use
            return { response: response, dataUser: dataUser };
        });
    })
    .then(({ response, dataUser }) => { // Destructure the returned object
        console.log('Register response:', response);
        if (!response.ok) {
            throw new Error('Request failed with status ' + response.status);
        }

        return response.text(); // Use .text() first to debug
    })
    .then(userData => {
        console.log('User client successfully registered:', userData);
        alert('User client successfully registered.');
        document.getElementById('clientForm').reset(); 
    })
    .catch(error => {
        console.error('Failed to register client or user:', error);
        alert('Failed to register client or user. Please try again.');
        document.getElementById('clientForm').reset();
    });

});
