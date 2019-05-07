/**
 * 
 */

const URL = '127.0.0.1:8080/';

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
			
			//ev.preventDefault();
			const id = form.querySelector('.userID').value;
			//use fetch to do API calls
			
		},
		
		//handles filling in the page for that users data
		//User user
		renderUser(user){
			//display user info?
			
			//get friends
			//render all friends
			
			//get ingredients
			//render all ingredients
		},
		
		//Ingredient ingredient (API object)
		renderIngredient(ingredient){
			const item = document.querySelector('#contents li.template').cloneNode(true);
			item.classList.remove('template');
			item.querySelector('.name').textContent = ingredient.name;
			
			item.id = `ingred-id-${ingredient.id}`;
			//querySelect buttons and add listeners later (see MichaelBaywatch)
			this.ingredients.insertBefore(item, this.ingredients.firstChild);
		},
		
		//User user (API object)
		renderFriend(user){
			const item = document.querySelector('#friend li.template').cloneNode(true);
			item.classList.remove('template');
			item.querySelector('.name').textContent = user.name;
			item.querySelector('.userID').textContent = user.ID;
			
			this.friends.insertBefore(item, this.friends.firstChild);
		},
		
		
}

app.init({
	loginFormSelector: "",
	ingredientsSelector: "#contents li",
	friendsSelector: "#friends li",
})