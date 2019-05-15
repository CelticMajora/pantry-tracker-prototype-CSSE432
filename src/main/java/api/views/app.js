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
		
		//event ev
		sendFriendRequest(ev){
		},
		
		//event ev
		addUser(ev){
//			ev.preventDefault();
//			fetch(URL+'user/?name='+ev.target.querySelector('.name').value,
//					{headers:new Headers({
//						'Access-Control-Allow-Origin': '*',
//					}),
//					method:'post',
//					}).then((obj)=>obj.json())
//					.then(function(obj) = {
//						console.log(obj);
//					})
//					.catch(function(error){
//						console.log(error);
//					});
			
			const user = {
					id:'get id from post response',
					name:ev.target.querySelector('.name').value,
			}
			this.renderUser(user);
		},
		
		//event ev
		acceptFriendRequest(ev){
			ev.preventDefault();
			ev.target.parentElement;
		},
		
		//event ev
		rejectFriendRequest(ev){
			ev.preventDefault();
			ev.target.parentElement;
		},
		
		//handles filling in the page for that users data
		//User user
		renderUser(user){
			//display user info?
			this.user = user;
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
			
			//get friendRequests recieved
			//render all friendRequests
			fetch(URL+'friend_requests/received?userId='+user.id,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					}).then((obj)=>obj.json())
					.then(this.renderAllFriend_Requests.bind(this))
					.catch(function(error){
						console.log(error);
					});
		},
		
		renderAllFriend_Requests(requests){
			for(const rqst of requests){
				this.renderFriendRequest(rqst);
			}
		},
		
		renderFriendRequest(request){
			const item = document.querySelector('#contents li.template').cloneNode(true);
			item.classList.remove('template');
			
			item.querySelector('.userId').textContent = request.userId;
			item.querySelector('.id').textContent = request.id;
			item.querySelector('.accept').addEvenListener('click', this.acceptFriendRequest.bind(this));
			item.querySelector('.reject').addEvenListener('click', this.rejectFriendRequest.bind(this));
			
		},
		
		//JSON list of ingredient objects
		renderAllIngredients(ingredients){
			for(const ingr of ingredients){
				this.renderIngredient(ingr);
			}
		},
		
		//Ingredient ingredient (API object)
		renderIngredient(ingredient){
			const item = document.querySelector('#ingredientList li.template').cloneNode(true);
			item.classList.remove('template');
			item.querySelector('.name').textContent = ingredient.name;
			item.querySelector('.id') = ingredient.id;
			
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
			const item = document.querySelector('#friendsList li.template').cloneNode(true);
			item.classList.remove('template');
			item.querySelector('.name').textContent = user.name;
			item.querySelector('.userID').textContent = user.ID;
			//TODO
			//item.querySelector('.delete').addActionListener(this.deleteFriend.bind(this));
			
			this.friends.insertBefore(item, this.friends.firstChild);
		},
		
		
}

app.init({
	loginFormSelector: "form.login",
	ingredientsSelector: "#ingredientList li",
	friendsSelector: "#friendsList li",
})