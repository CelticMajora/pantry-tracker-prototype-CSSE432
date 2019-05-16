/**
 * 
 */

const URL = 'http://127.0.0.1:8080/';

const app = {
		
		//defined at bottom of page
		init(selectors){
			this.user = {name:'default',
					id:'1'};
			
			this.loginForm = document.querySelector(selectors.loginFormSelector);
			if(this.loginForm !== null){
				this.loginForm.addEventListener('submit', this.handleLogin.bind(this));
				this.loginForm.querySelector(".delete").addEventListener('click', this.deleteUser.bind(this));
				this.loginForm.querySelector(".add").addEventListener('click', this.addUser.bind(this));
			}else{
				console.log('loginForm not found');
			}
			
			this.ingredients = document.querySelector(selectors.ingredientsSelector);
			
			this.friends = document.querySelector(selectors.friendsSelector);
			this.friendRequests = document.querySelector(selectors.friendRequestSelector);
			
			this.addIngredientForm = document.querySelector("form.ingredient");
			this.addIngredientForm.addEventListener('submit', this.addIngredient.bind(this));
			
			this.addFriendForm = document.querySelector("form.friend");
			this.addFriendForm.addEventListener('submit',this.sendFriendRequest.bind(this));
		},
		
		//event ev 
		handleLogin(ev){
			//get userID from form
			//fill out the user object
			ev.preventDefault();
			console.log(ev.target.querySelector('.id').value);
			fetch(URL+'user?id='+ev.target.querySelector('.id').value,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					}).then((obj)=>obj.json())
					.then(this.renderUser.bind(this))
					.catch(function(error){
						console.log(error);
					});
			
		},
		
		//event ev
		sendFriendRequest(ev){
			ev.preventDefault();
			
			fetch(URL+'friend_requests?friendId='+ev.target.querySelector(".id").value+'&userID='+this.user.id,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					method:'POST',
					})
					.catch(function(error){
						console.log(error);
					});
		},
		
		addIngredient(ev){
			ev.preventDefault();
			
			fetch(URL+'ingredient?name='+ev.target.querySelector(".name").value
					+'&ownerId='+this.user.id+'&expirationYear='+ev.target.querySelector(".year").value
					+'&expirationMonth='+ev.target.querySelector(".month").value
					+'&expirationDayOfMonth='+ev.target.querySelector(".day").value,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					method:'POST',
					})
					.catch(function(error){
						console.log(error);
					});
			
		},
		
		
		deleteUser(ev){
			ev.preventDefault();
			const id = ev.target.parentElement.querySelector(".id").value;
			
			fetch(URL+'user?id='+id,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					method:'DELETE',
					})
					.catch(function(error){
						console.log(error);
					});
		},
		
		
		//event ev
		addUser(ev){
			ev.preventDefault();
			fetch(URL+'user/?name='+ev.target.parentElement.querySelector('.id').value ,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					method:'POST',
					}).then((res)=> res.json()).then(this.newUser.bind(this))
					.catch(function(error){
						console.log(error);
					});
			
			
		},
		
		newUser(response){
			const user = {
					id:response.id,
					name:response.name,
			}
			this.renderUser(user);
		},
		
		//event ev
		acceptFriendRequest(ev){
			ev.preventDefault();
			
			fetch(URL+'friend_requests/accept?friendRequestId='+ev.target.parentElement.querySelector(".id").textContent,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					method:'POST',
					}).catch(function(error){
						console.log(error);
					});
			
			renderUser(this.user);
		},
		
		//event ev
		rejectFriendRequest(ev){
			ev.preventDefault();
			
			fetch(URL+'friend_requests/reject?friendRequestId='+ev.target.parentElement.querySelector(".id").textContent,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					method:'POST',
					}).catch(function(error){
						console.log(error);
					});
			
			renderUser(this.user);
		},
		
		//handles filling in the page for that users data
		//User user
		renderUser(user){
			//display user info
			this.loginForm.querySelector(".name").textContent = 'Name:'+user.name;
			this.user = user;
			//get friends
			console.log(user);
			console.log('id:'+user.id);
			console.log('name:'+user.name);
			
			this.loginForm.querySelector('.id').value = user.id;
			
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
			
			this.friendRequests.insertBefore(item, this.friendRequests.firstChild);
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
			item.querySelector('.id').textContent = ingredient.id;
			item.querySelector('.date').textContent = ingredient. expirationDate
			item.querySelector('.delete').addEventListener('click', this.deleteIngredient.bind(this));
			
			this.ingredients.insertBefore(item, this.ingredients.firstChild);
		},
		
		deleteIngredient(ev){
			ev.preventDefault();
			
			fetch(URL+'ingredient?id='+ev.target.parentElement.querySelector(".id").textContent,
					{headers:new Headers({
						'Access-Control-Allow-Origin': '*',
					}),
					method:'DELETE',
					}).catch(function(error){
						console.log(error);
					});
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
			
			this.friends.insertBefore(item, this.friends.firstChild);
		},
		
		
}

app.init({
	loginFormSelector: "form.login",
	ingredientsSelector: "#ingredientList li",
	friendsSelector: "#friendsList li",
	friendRequestSelector: "#friendRequest li",
})