document.addEventListener("DOMContentLoaded", function() {
    var token = localStorage.getItem('authToken'); 
    var email = localStorage.getItem('userEmail'); 
    var lawyerOab = ""; 

    if (!token) {
        console.error('Token de autenticação não encontrado.');
        return;
    }

    fetch('http://localhost:8080/client/email/' + email, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        var welcomeElement = document.querySelector('.welcome h1');
        if (welcomeElement) {
            welcomeElement.textContent = "Bem-vindo, " + data.name;
            lawyerOab = data.oab; 
            fetchCases(lawyerOab); // Chama a função para carregar os casos
        } else {
            console.error('Elemento não encontrado');
        }
    })
    .catch(error => console.error('Error:', error));

    function fetchCases(oab) {
        fetch('http://localhost:8080/client/' + oab + '/cases', { 
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            var casesList = document.getElementById('cases-list');
            casesList.innerHTML = '';

            if (data.length === 0) {
                casesList.innerHTML = '<tr><td colspan="6">Não há casos para exibir.</td></tr>';
            } else {
                data.forEach(caseItem => {
                    var row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${caseItem.id}</td>
                        <td>${caseItem.typeCase}</td>
                        <td>${caseItem.description}</td>
                        <td>${caseItem.client.name}</td>
                        <td>${caseItem.client.cpf}</td>
                        <td>${caseItem.price}</td>
                    `;
                    casesList.appendChild(row);
                });
            }
        })
        .catch(error => {
            console.error('Error:', error);
            var casesList = document.getElementById('cases-list');
            casesList.innerHTML = '<tr><td colspan="6">Erro ao carregar casos.</td></tr>';
        });
    }
});
