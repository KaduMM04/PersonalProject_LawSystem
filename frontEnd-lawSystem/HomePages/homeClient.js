document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:8080/lawyer/2444')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log(data); // Verifique o conteúdo da resposta
            var welcomeElement = document.querySelector('.welcome h1');
            if (welcomeElement) {
                welcomeElement.textContent = "Bem-vindo, Dr(a)." + data.name;
            } else {
                console.error('Elemento não encontrado');
            }
        })
        .catch(error => console.error('Error:', error));

    var modal = document.getElementById("cases-modal");
    var btn = document.getElementById("show-cases");
    var span = document.getElementsByClassName("close")[0];

    btn.onclick = function() {
        fetchCases();
        modal.style.display = "block";
    }

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    function fetchCases() {
        fetch('http://localhost:8080/lawyer/2444/cases') // Altere para o endpoint correto
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                var casesList = document.getElementById('cases-list');
                casesList.innerHTML = ''; // Limpa o conteúdo existente
                
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
