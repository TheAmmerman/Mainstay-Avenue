BEGIN TRANSACTION;

DROP TABLE IF EXISTS recipes_users;
DROP TABLE IF EXISTS recipes_library;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- Saved recipes by users
CREATE TABLE recipes_library (
	recipe_id SERIAL,
	title text NOT NULL,
	ingredient_list text,               	-- holds all relevant ingredient IDs
	instructions text,
	summary text,
	duration numeric,                       -- time in minutes for recipe
	servings numeric,                       -- serving size
	diet_categories text,		        	-- will hold multiple categories, such as keto or gluten free
	occasions text, 		                -- will include stuff like holidays, time of day
	recipe_source_url varchar(200),
	image_path varchar(200),
	CONSTRAINT PK_recipe_id PRIMARY KEY (recipe_id)
);

CREATE TABLE recipes_users (
    user_id integer NOT NULL,
    recipe_id integer NOT NULL,
    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT FK_recipe_id FOREIGN KEY (recipe_id) REFERENCES recipes_library(recipe_id)
);

---- Ingredient list which users will be able to add to their pantry and/or shopping cart
--CREATE TABLE ingredients (
--	ingredient_id serial,
--  ingredient_name varchar(50),
--	CONSTRAINT PK_ingredient_id PRIMARY KEY (ingredient_id)
--);
--
---- Meal plan table to hold a meal plan idea
--CREATE TABLE meal_plans (
--	meal_plan_id serial,
--	title varchar(20) NOT NULL,				-- Title of the meal plan; required
--	recipe_list varchar(300),				-- List of recipe IDs used in the meal plan
--	description text,						-- Meal plan description
--	duration int, 							-- Duration of the meal plan; must be greater than 0
--	diet_categories varchar(100),			-- List of any diet categories, i.e. keto, etc.
--	dietary_restrictions varchar(100),		-- List of any dietary restrictions
--	CONSTRAINT PK_meal_plan_id PRIMARY KEY (meal_plan_id),
--	CONSTRAINT CK_duration CHECK (duration >= 0)
--);
--
---- Pantry table to hold the ingredients the user is currently in possession of
--CREATE TABLE pantry (
--	user_id int NOT NULL,
--	ingredient_id int NOT NULL,
--	CONSTRAINT user_pantry FOREIGN KEY (user_id) REFERENCES users(user_id),
--	CONSTRAINT pantry_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id)
--);
--
---- Shopping cart table to hold the ingredients that a user needs to purchase
--CREATE TABLE shopping_cart (
--	user_id int NOT NULL,
--	ingredient_id int NOT NULL,
--	CONSTRAINT user_shopping FOREIGN KEY (user_id) REFERENCES users(user_id),
--	CONSTRAINT shopping_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id)
--);
--
---- Join table between users and meal plans to log the meal plans saved for a user
--CREATE TABLE user_meal_plans (
--	user_id int NOT NULL,
--	meal_plan_id int NOT NULL,
--	CONSTRAINT user_meal_plans FOREIGN KEY (user_id) REFERENCES users(user_id),
--	CONSTRAINT meal_plans_user FOREIGN KEY (meal_plan_id) REFERENCES meal_plans(meal_plan_id)
--);

COMMIT TRANSACTION;
