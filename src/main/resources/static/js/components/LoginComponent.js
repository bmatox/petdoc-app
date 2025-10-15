// Componente Vue.js para Login Moderno
const LoginComponent = {
  name: 'LoginComponent',
  template: `
    <div class="login-vue-container">
      <!-- Carrossel de imagens lateral -->
      <div class="login-carousel-section">
        <div class="carousel-wrapper">
          <transition name="fade" mode="out-in">
            <img :key="currentImageIndex" :src="images[currentImageIndex]" alt="Pet" class="carousel-image">
          </transition>
          <div class="carousel-indicators">
            <button 
              v-for="(img, index) in images" 
              :key="index"
              @click="currentImageIndex = index"
              :class="['indicator', { active: currentImageIndex === index }]"
              :aria-label="'Imagem ' + (index + 1)"
            ></button>
          </div>
        </div>
      </div>

      <!-- Formulário de Login -->
      <div class="login-form-section">
        <div class="login-form-wrapper">
          <!-- Logo e Branding -->
          <div class="brand-header">
            <img src="/images/petdoc-logo.png" alt="Logo PetDoc" class="brand-logo">
            <h1 class="brand-name">PetDoc</h1>
            <p class="brand-tagline">A saúde do seu pet na palma da sua mão.</p>
          </div>

          <!-- Título -->
          <h2 class="form-title">Bem-vindo de volta!</h2>

          <!-- Alertas -->
          <transition name="slide-fade">
            <div v-if="alert.show" :class="['alert', alert.type]">
              <i :class="alert.icon"></i>
              <span>{{ alert.message }}</span>
            </div>
          </transition>

          <!-- Formulário -->
          <form @submit.prevent="handleLogin" class="login-form">
            <div class="form-group">
              <label for="email" class="form-label">
                <i class="fas fa-envelope"></i>
                E-mail
              </label>
              <input
                id="email"
                v-model="form.email"
                type="email"
                class="form-input"
                :class="{ 'input-error': errors.email }"
                placeholder="seu@email.com"
                required
                autocomplete="email"
                :disabled="loading"
              >
              <span v-if="errors.email" class="error-message">{{ errors.email }}</span>
            </div>

            <div class="form-group">
              <label for="password" class="form-label">
                <i class="fas fa-lock"></i>
                Senha
              </label>
              <div class="password-input-wrapper">
                <input
                  id="password"
                  v-model="form.password"
                  :type="showPassword ? 'text' : 'password'"
                  class="form-input"
                  :class="{ 'input-error': errors.password }"
                  placeholder="Digite sua senha"
                  required
                  autocomplete="current-password"
                  :disabled="loading"
                >
                <button
                  type="button"
                  @click="showPassword = !showPassword"
                  class="password-toggle"
                  :aria-label="showPassword ? 'Ocultar senha' : 'Mostrar senha'"
                >
                  <i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
                </button>
              </div>
              <span v-if="errors.password" class="error-message">{{ errors.password }}</span>
            </div>

            <button type="submit" class="btn-submit" :disabled="loading">
              <span v-if="!loading">
                <i class="fas fa-sign-in-alt"></i>
                Entrar
              </span>
              <span v-else class="loading-spinner">
                <i class="fas fa-spinner fa-spin"></i>
                Entrando...
              </span>
            </button>

            <div class="form-footer">
              <a href="/cadastro" class="link-cadastro">
                Não tem uma conta? <strong>Cadastre-se</strong>
              </a>
              <div class="classic-version-link">
                <a href="/login" class="link-classic">
                  <i class="fas fa-arrow-left"></i>
                  Versão clássica
                </a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  `,
  data() {
    return {
      form: {
        email: '',
        password: ''
      },
      errors: {
        email: '',
        password: ''
      },
      alert: {
        show: false,
        type: 'success',
        message: '',
        icon: ''
      },
      loading: false,
      showPassword: false,
      images: [
        '/images/image1.jpg',
        '/images/image2.jpg',
        '/images/image3.jpg',
        '/images/image4.jpg'
      ],
      currentImageIndex: 0,
      carouselInterval: null
    };
  },
  mounted() {
    this.startCarousel();
    this.checkForSuccessMessage();
  },
  beforeUnmount() {
    this.stopCarousel();
  },
  methods: {
    startCarousel() {
      this.carouselInterval = setInterval(() => {
        this.currentImageIndex = (this.currentImageIndex + 1) % this.images.length;
      }, 5000);
    },
    stopCarousel() {
      if (this.carouselInterval) {
        clearInterval(this.carouselInterval);
      }
    },
    checkForSuccessMessage() {
      const urlParams = new URLSearchParams(window.location.search);
      const sucessoMsg = urlParams.get('sucesso');
      if (sucessoMsg) {
        this.showAlert('success', sucessoMsg, 'fas fa-check-circle');
      }
    },
    validateForm() {
      let isValid = true;
      this.errors = { email: '', password: '' };

      // Validação de email
      if (!this.form.email) {
        this.errors.email = 'E-mail é obrigatório';
        isValid = false;
      } else if (!this.isValidEmail(this.form.email)) {
        this.errors.email = 'E-mail inválido';
        isValid = false;
      }

      // Validação de senha
      if (!this.form.password) {
        this.errors.password = 'Senha é obrigatória';
        isValid = false;
      } else if (this.form.password.length < 3) {
        this.errors.password = 'Senha muito curta';
        isValid = false;
      }

      return isValid;
    },
    isValidEmail(email) {
      const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return re.test(email);
    },
    async handleLogin() {
      if (!this.validateForm()) {
        return;
      }

      this.loading = true;
      this.alert.show = false;

      try {
        const response = await fetch('/api/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          credentials: 'include',
          body: JSON.stringify({
            email: this.form.email,
            senha: this.form.password
          })
        });

        const data = await response.json();

        if (response.ok && data.sucesso) {
          this.showAlert('success', data.mensagem, 'fas fa-check-circle');
          
          // Redireciona após 1 segundo
          setTimeout(() => {
            window.location.href = data.redirectUrl || '/dashboard';
          }, 1000);
        } else {
          const errorMessage = data.mensagem || 'E-mail ou senha inválidos';
          this.showAlert('error', errorMessage, 'fas fa-exclamation-circle');
          this.loading = false;
        }
      } catch (error) {
        console.error('Erro ao fazer login:', error);
        this.showAlert('error', 'Erro ao conectar com o servidor. Tente novamente.', 'fas fa-exclamation-triangle');
        this.loading = false;
      }
    },
    showAlert(type, message, icon) {
      this.alert = {
        show: true,
        type: type,
        message: message,
        icon: icon
      };

      // Auto-hide após 5 segundos
      setTimeout(() => {
        this.alert.show = false;
      }, 5000);
    }
  }
};
