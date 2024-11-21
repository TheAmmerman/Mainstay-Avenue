<template>
    <div id="mealPlanPage">
        <header id="navigation">
            <nav>
                <router-link to="/">Home</router-link>
                <router-link to="/account">Account</router-link>
                <router-link to="/recipes">Recipes</router-link>
                <router-link to="/meal-plans">Meal Plans</router-link>
                <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
            </nav>
        </header>
        <h1 class="mealPlanTitle">Meal Plans</h1>
        <footer>
      <p>&copy; 2024 Meal Planning App. All rights reserved. <img id="waltFooter" src="../photos/walter_smiling.png"/></p>
      <div class="socials">
                <a href="https://facebook.com" target="_blank">Facebook <fa :icon="['fab', 'facebook']" /></a>
                <a href="https://twitter.com" target="_blank">Twitter <fa :icon="['fab', 'twitter']" /></a>
                <a href="https://instagram.com" target="_blank">Instagram <fa :icon="['fab', 'instagram']" /></a>
            </div>
    </footer>
    </div>
</template>

<script>
</script>

<style>
#mealPlanPage{
    background-color: #369cdb;
}

.mealPlanTitle{
    font-family: Merienda;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 25px;
  font-size : 75px;
  margin-top : 0;
}
</style>