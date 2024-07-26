document.getElementById('clientForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const cpf = document.getElementById('cpf').value;
    const cep = document.getElementById('cep').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    console.log('Name:', name);
    console.log("CEP:", cep);
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
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição');
        }
        return response.json();
    })
    .then(data => {
        console.log('Cadastro realizado com sucesso:', data);
    })
    .catch(error => {
        console.error('Erro ao cadastrar:', error);
    });
});