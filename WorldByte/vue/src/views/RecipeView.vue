<template>
    <div id="recipeList" @keypress.enter.prevent="searchByKeyword()">
        <header id="navigation" class="header">
            <div>
                <span class="searchWords">Search Recipes By Keyword: </span>
                <input placeholder="Search recipes..." class="keyword" v-model="word" />
                <button class="searchButton" @click="searchByKeyword(word)">Search</button>
            </div>
            <nav>
                <router-link to="/">Home</router-link>
                <router-link to="/account">Account</router-link>
                <router-link to="/recipes">Recipes</router-link>
                <!-- <router-link to="/meal-plans">Meal Plans</router-link> -->
                <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
            </nav>
        </header>
        <h1 class="recipeTitle">Recipes</h1>
        <div class="nav">
            <div id="nameSearch">
                <label for="name">Recipe Name: </label>
                <input type:text id="name" v-model="filter.name" placeholder="Search by name...">
            </div>
            <div id="timeSearch">
                <label for="time">Time(in mins): </label>
                <select id="time" v-model="filter.duration">
                    <option value="0">0</option>
                    <option value="15">15</option>
                    <option value="30">30</option>
                    <option value="45">45</option>
                    <option value="60">60</option>
                </select>
            </div>
            <div id="occasion">
                <label for="occasion">Occasion: </label>
                <input type:text id="occasion" v-model="filter.category" placeholder="Search by category...">
            </div>
            <div id="dietary">
                <label for="dietary">Diet Type: </label>
                <input type:text id="dietary" v-model="filter.dietary" placeholder="Search by diet...">
            </div>
            <div>
                <button @click="clear" class="clearButton">Clear</button>
            </div>
        </div>
        <div class="main">
            <recipesDesign :recipes="filteredList" />
        </div>
        <footer>
            <p>&copy; 2024 Meal Planning App. All rights reserved. <img id="waltFooter"
                    src="../photos/walter_smiling.png" /></p>
            <div class="socials">
                <a href="https://facebook.com" target="_blank">Facebook
                    <fa :icon="['fab', 'facebook']" />
                </a>
                <a href="https://twitter.com" target="_blank">Twitter
                    <fa :icon="['fab', 'twitter']" />
                </a>
                <a href="https://instagram.com" target="_blank">Instagram
                    <fa :icon="['fab', 'instagram']" />
                </a>
            </div>
        </footer>
    </div>
</template>

<script>
import recipesDesign from '../components/recipe.vue';
import authService from '../services/AuthService';

export default {
    components: {
        recipesDesign
    },
    data() {
        return {
            recipes: [
                // {
                //     recipeId: 1,
                //     image: "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=556,505",
                //     title: "Chicken alfredo with some parmesan encrustated cauliflower",
                //     duration: 30,
                //     occasions: ['lunch', 'snack'],
                //     dietCategories: "none",
                //     servings: 2
                // },
                // {
                //     recipeId: 2,
                //     image: "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=556,505",
                //     title: "Gnocchi",
                //     duration: 60,
                //     occasions: ["Dinner", 'snack'],
                //     dietCategories: ["none"],
                //     servings: 4
                // },
                // {
                //     recipeId: 3,
                //     image: "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=556,505",
                //     title: "Pie",
                //     duration: 30,
                //     occasions: ["Dessert", 'snack'],
                //     dietCategories: ["Vegan"],
                //     servings: 2
                // },
                // {
                //     recipeId: 4,
                //     image: "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=556,505",
                //     title: "Chicken alfredo",
                //     duration: 30,
                //     occasions: ["Lunch", 'snack'],
                //     dietCategories: ["none"],
                //     servings: 2
                // },
                // {
                //     recipeId: 5,
                //     image: "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=556,505",
                //     title: "iced tea",
                //     duration: 60,
                //     occasions: ["Dinner", 'snack'],
                //     dietCategories: ["vegetarian"],
                //     servings: 4
                // },
                // {
                //     recipeId: 6,
                //     image: "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg?quality=90&resize=556,505",
                //     title: "Pie",
                //     duration: 30,
                //     occasions: ["Dessert", 'snack'],
                //     dietCategories: ["pescatarian"],
                //     servings: 2
                // }
            ],
            filter: {
                name: "",
                duration: "",
                category: "",
                dietary: ""
            },
            word: ""
        }
    },
    methods: {
        clear() {
            this.filter.name = "",
                this.filter.duration = "",
                this.filter.category = "",
                this.filter.dietary = ""
        },
        searchByKeyword(word) {
            authService.getRecipes(word).then(response => {
                this.recipes = response.data
                this.keyword = ""
            }).catch(error => {
                console.error('Error searching recipes:', error)
            });

        },
        sortThroughOccasions(object){
            let string = "";

            for(let i = 0; i < object.length; i++){
                string = object[i]
                if(string.toLowerCase().includes(this.filter.category.toLowerCase())){
                    return string;
                }
            }
        },
        sortThroughDiet(object){
            let string = "";

            for(let i = 0; i < object.length; i++){
                string = object[i]
                if(string.toLowerCase().includes(this.filter.dietary.toLowerCase())){
                    return string;
                }
            }
        }
    },
    created() {
        authService.getRecipes("chicken").then(response => {
            this.recipes = response.data;
        }).catch(error => {
            console.error('Error searching recipes:', error)
        })
    },
    computed: {
        filteredList() {
            let filterRecipe = this.recipes;
            if (this.filter.name != "") {
                filterRecipe = filterRecipe.filter(recipe =>
                    recipe.title
                        .toLowerCase()
                        .includes(this.filter.name.toLowerCase())
                );
            }
            if (this.filter.category != "") {
                filterRecipe = filterRecipe.filter(recipe => 
                    this.sortThroughOccasions(recipe.occasions)
                )

                
            }
            if (this.filter.duration != 0) {
                filterRecipe = filterRecipe.filter(recipe =>
                    recipe.duration <= this.filter.duration
                )
            }
            if (this.filter.dietary != "") {
                filterRecipe = filterRecipe.filter(recipe =>
                    this.sortThroughDiet(recipe.dietCategories)
                )
            }
            return filterRecipe;
        }
    }
}

</script>

<style>
#recipeList {
    background-color: #369cdb;
}

.searchButton {
    margin: 10px;
    padding: 5px;
    font-size: 18px;
    width: 100px;
    background-color: #00b35c;
    color: white;
    cursor: pointer;
}

.searchButton:hover {
    background-color: green;
}

.clearButton {
    margin: 10px;
    padding: 8px;
    font-size: 25px;
    width: 100px;
    height: 50px;
    cursor: pointer;
    font-family: Varela Round;
    border-radius: 5px;
}

.header {
    display: flex;
    justify-content: space-between;
}

.searchWords {
    margin: 0 20px;
    font-size: 30px;
    color: white;
    text-decoration: none;
}

.keyword {
    padding: 5px;
    font-size: 20px;
    width: 250px;
}

.nav {
    background-color: white;
    height: 125px;
    width: 100vw;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    font-family: Varela Round;
    font-size: 25px;
    font-weight: bold;
}

nav a {
    margin: 0 20px;
    font-size: 35px;
    color: white;
    text-decoration: none;
}

nav a:hover {
    text-decoration: underline;
}

.recipeTitle {
    font-family: Merienda;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 25px;
    font-size: 75px;
    margin-top: 0;
}

.main {
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
}

label {
    display: inline;
}

#nameSearch {
    width: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
}

#nameSearch label {
    margin: 10px;
}

#nameSearch input {
    padding: 5px;
    font-size: 20px;
    width: 190px;
}

#timeSearch {
    width: 250px;
    display: flex;
    justify-content: center;
    align-items: center;
}

#timeSearch label {
    margin: 10px;
    width: 165px;
}

select {
    width: 50px;
    height: 30px;
}


#occasion {
    width: 300px;
    display: flex;
    justify-content: center;
    align-items: center;
}

#occasion label {
    margin: 10px;
    width: 120px;
}

#occasion input {
    padding: 5px;
    font-size: 20px;
    width: 180px;
}

#dietary {
    width: 400px;
    display: flex;
    justify-content: center;
    align-items: center;
}

#dietary label {
    margin: 10px;
}

#dietary input {
    padding: 5px;
    font-size: 20px;
    width: 225px;
}

footer {
    padding: 20px;
    background-color: #369cdb;
    color: white;
    text-align: center;
    font-size: 25px;
}

footer p {
    margin-top: 0;
    margin-bottom: 10px;
}

footer .socials a {
    margin: 0 10px;
    color: white;
    text-decoration: none;
}

footer .socials a:hover {
    text-decoration: underline;
}

#waltFooter {
    height: 55px;
}</style>