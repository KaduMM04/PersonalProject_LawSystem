document.addEventListener("DOMContentLoaded", function() {
    var token = localStorage.getItem('authToken'); 
    var email = localStorage.getItem('userEmail'); 
    var lawyerOab = ""; // Inicialize a variável global

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


    fetch('http://localhost:8080/lawyer/email/' + email, {
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
            welcomeElement.textContent = "Bem-vindo, Dr(a)." + data.name;
            lawyerOab = data.oab; // Atualize a variável global
        } else {
            console.error('Elemento não encontrado');
        }
    })
    .catch(error => console.error('Error:', error));

    var modal = document.getElementById("cases-modal");
    var btn = document.getElementById("show-cases");
    var span = document.getElementsByClassName("close")[0];

    btn.onclick = function() {
        if (modal) {
            fetchCases(); 
            modal.style.display = "block";
        } else {
            console.error('Modal não encontrado.');
        }
    }

    span.onclick = function() {
        if (modal) {
            modal.style.display = "none";
        }
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    function fetchCases() {
        if (!lawyerOab) {
            console.error('OAB do advogado não disponível.');
            return;
        }

        fetch('http://localhost:8080/lawyer/' + lawyerOab + '/cases', { 
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

    var cadastrosLink = document.getElementById('cadastros-link');
    var submenu = document.getElementById('cadastros-submenu');

    cadastrosLink.addEventListener('click', function(event) {
        event.preventDefault(); 
        submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
    });
});
