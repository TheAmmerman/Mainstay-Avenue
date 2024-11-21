import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({
    state: {
      token: currentToken || '',
      user: currentUser || {},
      success : false,
      myRecipes: []
      // mealPlans: [],
      // recipes: [],
      // groceryList: []
    },
    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        // state.mealPlans = [];
        // state.recipes = [];
        // state.groceryList = [];
        axios.defaults.headers.common = {};
      },
    //   SET_MEAL_PLANS(state, mealPlans) {
    //     state.mealPlans = mealPlans;
    //   },
    //   SET_RECIPES(state, recipes) {
    //     state.recipes = recipes;
    //   },
    //   SET_GROCERY_LIST(state, groceryList) {
    //     state.groceryList = groceryList;
    //   },
    //   ADD_MEAL_PLAN(state, mealPlan) {
    //     state.mealPlans.push(mealPlan);
    //   },
    //   UPDATE_MEAL_PLAN(state, updatedMealPlan) {
    //     const index = state.mealPlans.findIndex(plan => plan.id === updatedMealPlan.id);
    //     if (index !== -1) {
    //       state.mealPlans.splice(index, 1, updatedMealPlan);
    //     }
    //   },
    //   DELETE_MEAL_PLAN(state, mealPlanId) {
    //     state.mealPlans = state.mealPlans.filter(plan => plan.id !== mealPlanId);
    //   },
    //   ADD_RECIPE(state, recipe) {
    //     state.recipes.push(recipe);
    //   },
    //   UPDATE_RECIPE(state, updatedRecipe) {
    //     const index = state.recipes.findIndex(recipe => recipe.id === updatedRecipe.id);
    //     if (index !== -1) {
    //       state.recipes.splice(index, 1, updatedRecipe);
    //     }
    //   },
    //   DELETE_RECIPE(state, recipeId) {
    //     state.recipes = state.recipes.filter(recipe => recipe.id !== recipeId);
    //   },
    //   UPDATE_GROCERY_LIST(state, groceryList) {
    //     state.groceryList = groceryList;
    //   }
    // },
    // actions: {
    //   async fetchMealPlans({ commit }) {
    //     try {
    //       const response = await axios.get('/meal-plans');
    //       commit('SET_MEAL_PLANS', response.data);
    //     } catch (error) {
    //       console.error('Failed to fetch meal plans:', error);
    //     }
    //   },
    //   async fetchRecipes({ commit }) {
    //     try {
    //       const response = await axios.get('/recipes');
    //       commit('SET_RECIPES', response.data);
    //     } catch (error) {
    //       console.error('Failed to fetch recipes:', error);
    //     }
    //   },
    //   async fetchGroceryList({ commit }) {
    //     try {
    //       const response = await axios.get('/grocery-list');
    //       commit('SET_GROCERY_LIST', response.data);
    //     } catch (error) {
    //       console.error('Failed to fetch grocery list:', error);
    //     }
    //   },
    //   async createMealPlan({ commit }, mealPlan) {
    //     try {
    //       const response = await axios.post('/meal-plans', mealPlan);
    //       commit('ADD_MEAL_PLAN', response.data);
    //     } catch (error) {
    //       console.error('Failed to create meal plan:', error);
    //     }
    //   },
    //   async updateMealPlan({ commit }, mealPlan) {
    //     try {
    //       const response = await axios.put(`/meal-plans/${mealPlan.id}`, mealPlan);
    //       commit('UPDATE_MEAL_PLAN', response.data);
    //     } catch (error) {
    //       console.error('Failed to update meal plan:', error);
    //     }
    //   },
    //   async deleteMealPlan({ commit }, mealPlanId) {
    //     try {
    //       await axios.delete(`/meal-plans/${mealPlanId}`);
    //       commit('DELETE_MEAL_PLAN', mealPlanId);
    //     } catch (error) {
    //       console.error('Failed to delete meal plan:', error);
    //     }
    //   },
    //   async addRecipe({ commit }, recipe) {
    //     try {
    //       const response = await axios.post('/recipes', recipe);
    //       commit('ADD_RECIPE', response.data);
    //     } catch (error) {
    //       console.error('Failed to add recipe:', error);
    //     }
    //   },
    //   async updateRecipe({ commit }, recipe) {
    //     try {
    //       const response = await axios.put(`/recipes/${recipe.id}`, recipe);
    //       commit('UPDATE_RECIPE', response.data);
    //     } catch (error) {
    //       console.error('Failed to update recipe:', error);
    //     }
    //   },
    //   async deleteRecipe({ commit }, recipeId) {
    //     try {
    //       await axios.delete(`/recipes/${recipeId}`);
    //       commit('DELETE_RECIPE', recipeId);
    //     } catch (error) {
    //       console.error('Failed to delete recipe:', error);
    //     }
    //   },
    //   async updateGroceryList({ commit }, groceryList) {
    //     try {
    //       const response = await axios.put('/grocery-list', groceryList);
    //       commit('UPDATE_GROCERY_LIST', response.data);
    //     } catch (error) {
    //       console.error('Failed to update grocery list:', error);
    //     }
    //   }
    },
    getters: {
      isAuthenticated: state => !!state.token,
      // userMealPlans: state => state.mealPlans,
      // userRecipes: state => state.recipes,
      // userGroceryList: state => state.groceryList
    }
  });
  return store;
}
