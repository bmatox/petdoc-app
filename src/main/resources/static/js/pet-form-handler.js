document.addEventListener('DOMContentLoaded', function () {
    console.log("-> Handler do formulário de pet iniciado.");

    const especieSelect = document.getElementById('especie');
    const racaSelect = document.getElementById('raca');
    const racaLabel = document.querySelector('label[for="raca"]');

    async function carregarRacas() {
        const especie = especieSelect.value;
        console.log(`-> Espécie selecionada: '${especie}'`);

        racaSelect.innerHTML = '<option value="">Carregando...</option>';
        racaSelect.disabled = true;
        racaLabel.style.opacity = '0.5';

        if (!especie) {
            console.log("-> Nenhuma espécie selecionada. Resetando dropdown de raças.");
            racaSelect.innerHTML = '<option value="">Selecione uma espécie primeiro</option>';
            // Não desabilita aqui, para o usuário poder clicar se mudar de ideia
            racaSelect.disabled = true;
            return;
        }

        try {
            console.log(`-> Fazendo chamada fetch para: /api/racas?especie=${especie}`);
            const response = await fetch(`/api/racas?especie=${especie}`);

            if (!response.ok) {
                console.error(`-> Erro na resposta da API: Status ${response.status}`);
                throw new Error('Erro ao buscar raças');
            }

            const racas = await response.json();
            console.log("-> Raças recebidas da API:", racas);

            racaSelect.innerHTML = '<option value="">Selecione a raça</option>';

            racas.forEach(raca => {
                const option = document.createElement('option');
                option.value = raca.name;
                option.textContent = raca.name;
                racaSelect.appendChild(option);
            });
            console.log(`-> ${racas.length} raças adicionadas ao dropdown.`);

        } catch (error) {
            console.error("-> Ocorreu um erro no bloco try-catch:", error);
            racaSelect.innerHTML = '<option value="">Não foi possível carregar as raças</option>';
        } finally {
            racaSelect.disabled = false;
            racaLabel.style.opacity = '1';
            console.log("-> Processo finalizado. Dropdown de raças habilitado.");
        }
    }

    // Adiciona o "ouvinte" de evento
    console.log("-> Adicionando 'event listener' para o evento 'change' no dropdown de espécie.");
    especieSelect.addEventListener('change', carregarRacas);

    // Verificação inicial para o modo de edição
    if (especieSelect.value) {
        console.log("-> Espécie já selecionada no carregamento da página (modo de edição). Carregando raças...");
        carregarRacas();
    }
});