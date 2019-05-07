/**
 * 
 */
const app = {
		
		//defined at bottom of page
		init(selectors){
			this.user = -1;
			
			this.loginForm = document.querySelector(seletors.loginFormSelector);
			this.loginForm.addEventListener('submit', this.handleLogin.bind(this));
			
			this.ingredients = document.querySelector(selectors.ingredientsSelector);
			
			this.friends = document.querySelector(selectors.friendsSelector);
		},
		
		//form.querySelector
		//event ev 
		handleLogin(form, ev){
			//get userID from form
			//fill out the user object
		},
		
		//User user
		renderUser(user){
			
		},
		
		//Ingredient ingredient
		renderIngredient(ingredient){
			
		},
		
		//User user
		renderFriend(user){
			
		},
		
		
}

app.init({
	loginFormSelector: "",
	ingredientsSelector: "",
	friendsSelector: "",
})