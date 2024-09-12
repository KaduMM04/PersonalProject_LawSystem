document.addEventListener("DOMContentLoaded", function() {
    var token = localStorage.getItem('authToken'); 
    var email = localStorage.getItem('userEmail'); 

    // Função de logout
    function handleLogout(event) {
        event.preventDefault(); // Evita o comportamento padrão do link
        localStorage.removeItem('authToken'); // Remove o token do localStorage
        localStorage.removeItem('userEmail'); // Remove o email do localStorage
        window.location.href = '/Login/login.html'; 
    }

    var logoutButton = document.getElementById("logout");
    if (logoutButton) {
        logoutButton.addEventListener('click', handleLogout);
    }

    if (!token) {
        console.error('Token  of autentication not found');
        window.location.href = '/Login/login.html'; 
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
            throw new Error('Erro ao buscar dados do cliente: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
        var welcomeElement = document.querySelector('.welcome h1');
        if (welcomeElement) {
            welcomeElement.textContent = "Bem-vindo, " + data.name;
            clientCpf = data.cpf;
            fetchCases(clientCpf); // Chama a função para carregar os casos
        } else {
            console.error('Elemento de saudação não encontrado');
        }
    })
    .catch(error => {
        console.error('Erro ao carregar dados do cliente:', error);
    });

    function fetchCases(cpf) {
        fetch('http://localhost:8080/client/' + cpf + '/cases', { 
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar casos: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            var casesContainer = document.getElementById('cases-container');
            casesContainer.innerHTML = ''; // Limpa o conteúdo anterior

            if (data.length === 0) {
                casesContainer.innerHTML = '<p>Não há casos para exibir.</p>';
            } else {
                data.forEach(caseItem => {
                    var card = document.createElement('div');
                    card.classList.add('case-card'); // Adiciona uma classe ao card

                    card.innerHTML = `
                        <h3>Case ID: ${caseItem.id}</h3>
                        <p><strong>Tipo:</strong> ${caseItem.typeCase}</p>
                        <p><strong>Advogado:</strong> Dr(a). ${caseItem.lawyer.name}</p>
                        <p><strong>Descrição:</strong> ${caseItem.description}</p>
                    `;

                    casesContainer.appendChild(card); // Adiciona o card ao container
                });
            }
        })
        .catch(error => {
            console.error('Erro ao carregar casos:', error);
            var casesContainer = document.getElementById('cases-container');
            casesContainer.innerHTML = '<p>Erro ao carregar casos.</p>';
        });
    }
});
