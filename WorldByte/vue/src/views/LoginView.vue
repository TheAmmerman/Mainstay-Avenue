<template>
  <div id="login">
    <form v-on:submit.prevent="login" @keypress.enter.prevent="login">
      <h1 class="loginTitle">Please Sign In <img src="../photos/walter_smiling.png" /></h1>

      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password:</label>
        <input v-if="showPassword" type="text" id="password" v-model="user.password" required />
        <input v-else type="password" id="password" v-model="user.password" required />
        <button class="seePassword" @click="toggleShow">
          <span>
            <fa v-if="showPassword" icon="eye"/>
            <fa v-else icon="eye-slash"/>
          </span>
        </button>
      </div>
      <div id="subBtn">
        <button class="submitBtn" type="submit" >Sign in</button>
      </div>
      <p>
        <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false,
      showPassword: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
    toggleShow() {
      this.showPassword = !this.showPassword;
    }
  }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Merienda:wght@600&family=Varela+Round&display=swap');

#login {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #369cdb;
  font-family: 'Varela Round', sans-serif;
  cursor: url('../photos/walter_smiling.png'), auto;

}

form {
  background-color: white;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-direction: column;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
  max-height: 398.4px;
  height: 100%;
}

.loginTitle {
  font-family: Merienda;
  display: flex;
  color: #00b35c;
  margin-top: 0;
  align-items: center;
}

img {
  height: 132px;
  margin-left: 30px;
}

label {
  display: block;
  margin-bottom: 0.5rem;
}

input {
  border-radius: 4px;
  border-width: .5px;
  max-width: 300px;
  width: 100%;
  border: 1px solid #ccc;
  padding: 8px;
  box-sizing: border-box;
  display: inline-block;
  font-size: 18px;
}

input:hover {
  background-color: #fcf9f9;
}

#subBtn {
  display: flex;
  justify-content: center;
}

.submitBtn {
  border-radius: 4px;
  border: none;
  background-color: #00b35c;
  color: white;
  padding-top: 9px;
  padding-bottom: 9px;
  padding-left: 16px;
  padding-right: 16px;
  margin-top: 4px;
  cursor: pointer;
}

.submitBtn:hover {
  background-color: #007f4b;
}

.form-input-group {
  margin-bottom: 1rem;
  width: 100%;
}

p {
  padding-top: 4px;
}

a,
:visited {
  color: #00b35c;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

.seePassword{
  width: 39px;
  height: 39px;
  padding: 0px 6px;
}

</style>