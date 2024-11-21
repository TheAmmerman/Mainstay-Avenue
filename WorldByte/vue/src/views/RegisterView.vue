<template>
  <div id="register" class="text-center">
    <form v-on:submit.prevent="register" @keyup.enter="register" class="register-form">
      <h1 class="form-title">Create Account <img src="../photos/walter_smiling.png"/></h1>
      <div role="alert" v-if="registrationErrors" class="alert alert-danger">
        {{ registrationErrorMsg }}
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <div class="form-input-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
      </div>
      <button type="submit" class="btn btn-primary">Create Account</button>
      <p>
        <router-link v-bind:to="{ name: 'login' }">Already have an account? Log in.</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Merienda:wght@600&family=Varela+Round&display=swap');

#register {
  background-color: #369cdb;
  color: black;
  font-family: 'Varela Round', sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.register-form {
  max-width: 400px;
  width: 100%;
  background: #fff;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-title {
  font-family: 'Merienda', cursive;
  color: #00b35c;
  margin-bottom: 1rem;
}

.form-input-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

img{
  height: 132px;
  margin-left: 30px;
}

.btn {
  display: inline-block;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  background-color: #00b35c;
  color: #fff;
  font-family: 'Varela Round', sans-serif;
  cursor: pointer;
}

.btn-primary {
  background-color: #00b35c;
}

.btn-primary:hover {
  background-color: #007f4b;
}

.alert {
  margin-bottom: 1rem;
  padding: 0.5rem;
  border-radius: 4px;
}

.alert-danger {
  background-color: #f8d7da;
  color: #721c24;
}

a {
  color: #00b35c;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
