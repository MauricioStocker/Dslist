async function fetchGames() {
    try {
        const response = await fetch('/games'); // Faz a requisição à API no mesmo servidor
        const games = await response.json(); // Converte o JSON para objeto
        renderGames(games); // Renderiza os jogos na página
    } catch (error) {
        console.error('Erro ao carregar os jogos:', error);
    }
}

// Função para renderizar os jogos na tela em formato de cards
function renderGames(games) {
    const gameList = document.getElementById('game-list'); // Seleciona o container da lista
    gameList.innerHTML = ''; // Limpa a lista antes de renderizar

    games.forEach((game) => {
        const gameItem = document.createElement('div'); // Cria um container para o jogo
        gameItem.className = 'game-item'; // Classe CSS para estilo

        gameItem.innerHTML = `
            <img src="${game.imgUrl}" alt="${game.title}" class="game-image">
            <h3>${game.title}</h3>
            <p><strong>Ano:</strong> ${game.year}</p>
            <p>${game.shortDescription}</p>
            <a href="/games/${game.id}">Ver Detalhes</a>
        `;

        gameList.appendChild(gameItem); // Adiciona o jogo na lista
    });
}

// Carregar os jogos ao carregar a página
document.addEventListener('DOMContentLoaded', fetchGames);
