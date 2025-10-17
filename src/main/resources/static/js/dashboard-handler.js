async function openEditModal(petId) {
    try {
        const response = await fetch(`/api/pets/${petId}`);
        if (!response.ok) {
            throw new Error('Pet não encontrado.');
        }
        const pet = await response.json();

        const editModal = new bootstrap.Modal(document.getElementById('addPetModal'));
        const modalTitle = document.getElementById('addPetModalLabel');
        const form = document.querySelector('#addPetModal form');

        form.querySelector('#nome').value = pet.nome;
        form.querySelector('#especie').value = pet.especie;
        await carregarRacasESelecionar(pet.especie, pet.raca);
        form.querySelector('#dataNascimento').value = pet.dataNascimento;

        modalTitle.textContent = `Editar dados de ${pet.nome}`;
        form.action = `/pets/${pet.id}`;

        editModal.show();

    } catch (error) {
        alert('Não foi possível carregar os dados do pet.');
    }
}

async function carregarRacasESelecionar(especie, racaSelecionada) {
    const especieSelect = document.getElementById('especie');
    const racaSelect = document.getElementById('raca');

    especieSelect.value = especie;

    await document.getElementById('especie').dispatchEvent(new Event('change'));

    setTimeout(() => {
        racaSelect.value = racaSelecionada;
    }, 300);
}