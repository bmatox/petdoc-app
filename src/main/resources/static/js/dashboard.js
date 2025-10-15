// PetDoc Dashboard - Vue.js Application
const { createApp } = Vue;

createApp({
    data() {
        return {
            pets: [],
            filteredPets: [],
            loading: true,
            searchQuery: '',
            speciesFilter: '',
            showAddModal: false,
            editingPet: null,
            petForm: {
                nome: '',
                especie: '',
                raca: '',
                dataNascimento: ''
            },
            formErrors: {},
            submitting: false,
            toast: {
                show: false,
                message: '',
                type: 'success'
            },
            statistics: {
                totalPets: 0,
                totalVacinas: 0,
                registrosSaude: 0,
                visitasMarcadas: 0
            }
        };
    },
    computed: {
        speciesList() {
            const especies = [...new Set(this.pets.map(p => p.especie).filter(Boolean))];
            return especies.sort();
        },
        hasPets() {
            return this.pets.length > 0;
        }
    },
    watch: {
        searchQuery() {
            this.filterPets();
        },
        speciesFilter() {
            this.filterPets();
        }
    },
    methods: {
        async loadPets() {
            this.loading = true;
            try {
                const response = await fetch('/api/pets', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                
                if (!response.ok) {
                    throw new Error('Erro ao carregar pets');
                }
                
                this.pets = await response.json();
                this.filteredPets = [...this.pets];
                this.updateStatistics();
            } catch (error) {
                console.error('Erro ao carregar pets:', error);
                this.showToast('Erro ao carregar pets', 'error');
            } finally {
                this.loading = false;
            }
        },
        
        filterPets() {
            let filtered = [...this.pets];
            
            // Filter by search query
            if (this.searchQuery) {
                const query = this.searchQuery.toLowerCase();
                filtered = filtered.filter(pet => 
                    pet.nome.toLowerCase().includes(query) ||
                    (pet.especie && pet.especie.toLowerCase().includes(query)) ||
                    (pet.raca && pet.raca.toLowerCase().includes(query))
                );
            }
            
            // Filter by species
            if (this.speciesFilter) {
                filtered = filtered.filter(pet => pet.especie === this.speciesFilter);
            }
            
            this.filteredPets = filtered;
        },
        
        updateStatistics() {
            this.statistics.totalPets = this.pets.length;
            // TODO: Update other statistics when endpoints are available
        },
        
        openAddModal() {
            this.editingPet = null;
            this.resetForm();
            this.showAddModal = true;
        },
        
        openEditModal(pet) {
            this.editingPet = pet;
            this.petForm = {
                nome: pet.nome,
                especie: pet.especie || '',
                raca: pet.raca || '',
                dataNascimento: pet.dataNascimento || ''
            };
            this.showAddModal = true;
        },
        
        closeModal() {
            this.showAddModal = false;
            this.resetForm();
        },
        
        resetForm() {
            this.petForm = {
                nome: '',
                especie: '',
                raca: '',
                dataNascimento: ''
            };
            this.formErrors = {};
        },
        
        validateForm() {
            this.formErrors = {};
            
            if (!this.petForm.nome || this.petForm.nome.trim() === '') {
                this.formErrors.nome = 'Nome é obrigatório';
            }
            
            if (!this.petForm.especie || this.petForm.especie.trim() === '') {
                this.formErrors.especie = 'Espécie é obrigatória';
            }
            
            return Object.keys(this.formErrors).length === 0;
        },
        
        async savePet() {
            if (!this.validateForm()) {
                return;
            }
            
            this.submitting = true;
            
            try {
                const url = this.editingPet 
                    ? `/api/pets/${this.editingPet.id}`
                    : '/api/pets';
                    
                const method = this.editingPet ? 'PUT' : 'POST';
                
                const response = await fetch(url, {
                    method: method,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.petForm)
                });
                
                const data = await response.json();
                
                if (data.success) {
                    this.showToast(data.message, 'success');
                    this.closeModal();
                    await this.loadPets();
                } else {
                    this.showToast(data.message, 'error');
                }
            } catch (error) {
                console.error('Erro ao salvar pet:', error);
                this.showToast('Erro ao salvar pet', 'error');
            } finally {
                this.submitting = false;
            }
        },
        
        async deletePet(pet) {
            if (!confirm(`Tem certeza que deseja excluir ${pet.nome}?`)) {
                return;
            }
            
            try {
                const response = await fetch(`/api/pets/${pet.id}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                
                const data = await response.json();
                
                if (data.success) {
                    this.showToast(data.message, 'success');
                    await this.loadPets();
                } else {
                    this.showToast(data.message, 'error');
                }
            } catch (error) {
                console.error('Erro ao excluir pet:', error);
                this.showToast('Erro ao excluir pet', 'error');
            }
        },
        
        calculateAge(birthDate) {
            if (!birthDate) return 'N/A';
            
            const birth = new Date(birthDate);
            const today = new Date();
            const years = today.getFullYear() - birth.getFullYear();
            const months = today.getMonth() - birth.getMonth();
            
            if (years === 0) {
                return `${months} meses`;
            }
            
            return `${years} ano${years > 1 ? 's' : ''}`;
        },
        
        showToast(message, type = 'success') {
            this.toast = {
                show: true,
                message,
                type
            };
            
            setTimeout(() => {
                this.toast.show = false;
            }, 3000);
        },
        
        clearFilters() {
            this.searchQuery = '';
            this.speciesFilter = '';
        }
    },
    mounted() {
        this.loadPets();
    }
}).mount('#dashboard-app');
