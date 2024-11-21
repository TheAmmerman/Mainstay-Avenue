import axios from 'axios';

export default {
  login(user) {
    return axios.post('/login', user);
  },

  register(user) {
    return axios.post('/register', user)
  },
  
  getRecipes(keyword){
    return axios.get(`/recipes/${keyword}`)
  },

  getRecipeById(id){
    return axios.get(`/recipes/${id}/information`)
  },

  getMyRecipes(){
    return axios.get('/recipes/library')
  },

  getMyPlans(user){
    return axios.get(`/plans/${user.id}`)
  },

  addToMyRecipe(recipe){
    return axios.post('/recipes/library', recipe)
  },

  deleteMyRecipe(id){
    return axios.delete(`/recipes/library/${id}`)
  }
}