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
		
		//event ev 
		handleLogin(ev){
			//get userID from form
			//fill out the user object
			ev.preventDefault();
			console.log(ev.target.querySelector('.id').value);
			console.log('run');
			fetch(URL+'user?id='+ev.target.querySelector('.id').value,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					}).then((obj)=>obj.json())
					.then(this.renderUser.bind(this))
					.catch(function(error){
						console.log(error);
					});
			
			//ev.preventDefault();
			//use fetch to do API calls
			
		},
		
		//handles filling in the page for that users data
		//User user
		renderUser(user){
			//display user info?
			
			//get friends
			console.log(user);
			console.log('id:'+user.id);
			console.log('name'+user.name);
			
			//render all friends
			fetch(URL+'user/friends?id='+user.id,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					}).then((obj)=>obj.json())
					.then(this.renderAllFriends.bind(this))
					.catch(function(error){
						console.log(error);
					});
			
			//get ingredients
			//render all ingredients
			fetch(URL+'user/ingredient?userId='+user.id,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					}).then((obj)=>obj.json())
					.then(this.renderAllIngredients.bind(this))
					.catch(function(error){
						console.log(error);
					});
		},
		
		//JSON list of ingredient objects
		renderAllIngredients(ingredients){
			for(const ingr of ingredients){
				this.renderIngredient(ingr);
			}
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
		
		//JSON list of user objects
		renderAllFriends(users){
			for(const usr of users){
				this.renderFriend(usr);
			}
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