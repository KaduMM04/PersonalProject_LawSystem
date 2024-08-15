document.getElementById('lawyerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const name = document.getElementById('name').value;
    const oabNumber = document.getElementById('oabNumber').value;
    const area = document.getElementById('area').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    console.log('Name:', name);
    console.log('Email:', email);
    console.log('Password:', password);

    const data = {
        name: name,
        oab: Number(oabNumber),
        praticeArea: area,
        email: email,
        password: password
    };

    fetch('http://localhost:8080/lawyer', {
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
        alert('Advogado cadastrado com sucesso');
        document.getElementById('lawyerForm').reset();
    })
    .catch(error => {
        alert('Erro ao cadastrar o advogado. Tente novamente');
    });
});   