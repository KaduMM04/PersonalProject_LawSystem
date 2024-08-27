document.getElementById('clientForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const token = localStorage.getItem('authToken');

    if(!token) {
        console.error('Authenticated token not found.');
        return;
    }

    const name = document.getElementById('name').value;
    const cpf = document.getElementById('cpf').value;
    const cep = document.getElementById('cep').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

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
        password: password
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
    .then(data => {
        console.log('Client successfully registered:', data);
        alert('Client successfully registered.');
        // Optionally, reset the form after a successful registration
        document.getElementById('clientForm').reset();
    })
    .catch(error => {
        console.error('Failed to register client:', error);
        alert('Failed to register client. Please try again.');
    });
});
