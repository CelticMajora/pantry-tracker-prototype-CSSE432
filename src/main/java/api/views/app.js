/**
 * 
 */

const URL = 'http://127.0.0.1:8080/';

const app = {
		
		//defined at bottom of page
		init(selectors){
			this.user = -1;
			
			this.loginForm = document.querySelector(selectors.loginFormSelector);
			if(this.loginForm !== null){
				this.loginForm.addEventListener('submit', this.handleLogin.bind(this));
			}else{
				console.log('loginForm not found');
			}
			
			this.ingredients = document.querySelector(selectors.ingredientsSelector);
			
			this.friends = document.querySelector(selectors.friendsSelector);
		},
		
		//form.querySelector
		//event ev 
		handleLogin(ev){
			//get userID from form
			//fill out the user object
			console.log(ev.target.querySelector('.id').value);
			fetch(URL+'user?id='+ev.target.querySelector('.id').value,
					{mode: "no-cors",
					redirect:"follow",
					}).then((response) => console.log(response.text())).then((data) => console.log(data));
			
			//ev.preventDefault();
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
	loginFormSelector: "form.login",
	ingredientsSelector: "#contents li",
	friendsSelector: "#friends li",
})