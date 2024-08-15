document.addEventListener("DOMContentLoaded", function() {
    const lawyerSelect = document.getElementById('lawyerSelect');
    const clientSelect = document.getElementById('clientSelect');
    const caseForm = document.getElementById('caseForm');

    function fetchLawyers() {
        fetch('http://localhost:8080/lawyer')
            .then(response => response.json())
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
        fetch('http://localhost:8080/client')
            .then(response => response.json())
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
                cpf: parseInt(document.getElementById('clientSelect').value, 10)
            }
        };

        fetch('http://localhost:8080/case', { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newCase)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro na requisição');
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
            alert('Erro ao cadastrar o case. Tente novamente');
            console.error('Erro ao cadastrar:', error);
        });
    });
});
