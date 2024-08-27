document.addEventListener("DOMContentLoaded", function() {
    const lawyerSelect = document.getElementById('lawyerSelect');
    const clientSelect = document.getElementById('clientSelect');
    const caseForm = document.getElementById('caseForm');
    const token = localStorage.getItem('authToken'); // Recupera o token do localStorage

    if (!token) {
        console.error('Authenticated token not found.');
        return;
    }

    function fetchLawyers() {
        fetch('http://localhost:8080/lawyer', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token, // Adiciona o token ao cabeçalho
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao carregar advogados: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            data.forEach(lawyer => {
                const option = document.createElement('option');
                option.value = lawyer.oab;
                option.textContent = `${lawyer.name} (OAB: ${lawyer.oab})`;
                lawyerSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar advogados: ', error));
    }

    function fetchClients() {
        fetch('http://localhost:8080/client', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token, // Adiciona o token ao cabeçalho
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao carregar clientes: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            data.forEach(client => {
                const option = document.createElement('option');
                option.value = client.cpf;
                option.textContent = `${client.name} (CPF: ${client.cpf})`;
                clientSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao carregar clientes: ', error));
    }

    fetchLawyers();
    fetchClients();

    caseForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const newCase = {
            id: parseInt(document.getElementById('idCase').value, 10),
            typeCase: document.getElementById('area').value,
            description: document.getElementById('description').value,
            price: parseFloat(document.getElementById('price').value),
            lawyer: {
                oab: parseInt(document.getElementById('lawyerSelect').value, 10)
            },
            client: {
                cpf: document.getElementById('clientSelect').value
            }
        };

        fetch('http://localhost:8080/case', { 
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token, // Adiciona o token ao cabeçalho
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newCase)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na requisição: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            alert('Caso cadastrado com sucesso');
            caseForm.reset();
            lawyerSelect.selectedIndex = 0;
            clientSelect.selectedIndex = 0;
            console.log('Cadastro realizado com sucesso:', data);
        })
        .catch(error => {
            alert('Erro ao cadastrar o caso. Tente novamente.');
            console.error('Erro ao cadastrar:', error);
        });
    });
});
