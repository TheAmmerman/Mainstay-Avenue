<template>
    <div id="recipe" v-for="recipe in recipes" :key="recipe.id">
        <div class="picture">
            <img :src="recipe.image" class="photo">
        </div>
        <div id="info">
            <h3>Servings: {{ recipe.servings }}</h3>
            <h3>Duration: {{ recipe.duration }} mins </h3>
        </div>
        <h2>{{ truncate(recipe.title, 18) }}</h2>
        <div class="recipeOptions ">
            <div class="option" id="moreInfo">
                <div class="change">
                    <router-link :to= "{name : 'recipeDetails', params: { id : recipe.recipeId}}" ><button class="addBtn" id="infoButton" @click="getInfo()"
                            @mouseover="makeBeat($event)" @mouseout="killBeat($event)">
                            <fa icon="book" class="addRecipe" id="icon2"></fa>
                        </button>
                    </router-link>
                </div>
                <p class="words">More Info</p>
            </div>
            <div class="option" id="add">
                <div class="change">
                    <button class="addBtn" id="addButton" @click="addToMyLib(recipe)" @mouseover="makeBounce($event)"
                        @mouseout="killBounce($event)">
                        <fa icon="utensils" class="addRecipe" id="icon"></fa>
                    </button>
                </div>
                <p class="words">Add to Library</p>
            </div>
        </div>
    </div>
</template>

<script>
import authService from '../services/AuthService';

export default {
    data(){
        return{
            successTimer : null,
        }
    },
    props: ['recipes'],
    methods: {
        addToMyLib(recipe) {
            authService.addToMyRecipe(recipe).then(response => {
                if (response.status == 201) {
                    alert("Recipe added successfully")
                }
            })
        }, 
        test() {
            alert("Recipe added successfully", 4000)
        },
        makeBounce(event) {
            if (event.target.id === "addButton") {
                event.target.classList.add("fa-bounce");
            }
            else if (event.target.id === "icon") {
                event.target.parentElement.classList.add("fa-bounce");
            }
        },
        killBounce(event) {
            event.target.classList.remove("fa-bounce");
        },
        makeBeat(event) {
            if (event.target.id === "infoButton") {
                event.target.classList.add("fa-bounce");
            }
            else if (event.target.id === "icon2") {
                event.target.parentElement.classList.add("fa-bounce");
            }
        },
        killBeat(event) {
            event.target.classList.remove("fa-bounce");
        },
        truncate(input, num) {
            if (input.length > num) {
                let title = "";
                for (let i = 0; i < num; i++) {
                    title += input[i];
                }

                title += "..."
                return title;
            } else {
                return input;
            }

        }
    }
};
</script>

<style>
#info {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    font-size: 25px;
    color: grey;
}

.picture {
    display: flex;
    justify-content: center;
    align-items: center;
}

.photo {
    width: 70%;
    height: auto;
    border-radius: 8px;
    margin: 10px 0px 10px 0px;
}

h2 {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 35px;
    margin: 5px 0px 15px 0px;
    font-family: Merienda;
}

h3{
    font-family: Varela Round;
    font-size: 20px;
    margin: 5px 15px 10px 15px;
}

#recipe {
    display: inline-block;
    background: white;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 10px 0px;
    margin: 20px;
    flex: 1 30%;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    max-width: 420px;
    max-height: 500px;
}

.recipeOptions {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.change :hover {
    cursor: pointer;
    background-color: #00b35c;
    color: white;
}

.addBtn {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40px;
    height: 40px;
    background-color: #369cdb;
}

.addRecipe {
    height: 20px;
    width: 20px;
}

.option {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 0px 10px 0px 10px;
}

.words {
    font-size: 15px;
    color: black;
    margin: 3px;
    cursor: default;
}
</style>