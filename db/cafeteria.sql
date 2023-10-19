CREATE TABLE "user" (
	"id"	INT NOT NULL,
	"name"	VARCHAR(20) NOT NULL,
	"user_name"	VARCHAR(20) NOT NULL UNIQUE,
	"password"	VARCHAR(20) NOT NULL,
	"role"	VARCHAR(20) NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE "food_menu" (
	"id"	SERIAL NOT NULL,
	"name"	VARCHAR(20) NOT NULL UNIQUE,
	"type"	VARCHAR(20) NOT NULL,
	"availabilityOn"	VARCHAR(20) NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE "food_item" (
	"id"	SERIAL NOT NULL,
	"name"	VARCHAR(20) NOT NULL,
	"cost"	DOUBLE PRECISION NOT NULL,
	"quantity"	INT NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE "food_menu_food_item_map" (
	"id"	SERIAL NOT NULL,
	"food_menu_id"	INT,
	"food_item_id"	INT,
	CONSTRAINT "fk_fooditem" FOREIGN KEY("food_item_id") REFERENCES "food_item"("id"),
	PRIMARY KEY("id"),
	CONSTRAINT "fk_foodmenu" FOREIGN KEY("food_menu_id") REFERENCES "food_menu"("id")
);
CREATE TABLE "order" (
	"id"	SERIAL NOT NULL,
	"user_id"	INT NOT NULL,
	"total_cost"	DOUBLE PRECISION NOT NULL,
	"email"	VARCHAR(20) NOT NULL,
	"phone_number"	INT NOT NULL,
	"order_location"	VARCHAR(20) NOT NULL,
	"status"	VARCHAR(20) NOT NULL,
	"order_on"	TIMESTAMP NOT NULL,
	"delivery_on"	TIMESTAMP NOT NULL,
	PRIMARY KEY("id"),
	CONSTRAINT "fk_user" FOREIGN KEY("user_id") REFERENCES "user"("id")
);
CREATE TABLE "order_item" (
	"id"	SERIAL NOT NULL,
	"order_id"	INT,
	"food_item_id"	INT,
	"quantity"	INT NOT NULL,
	"cost"	DOUBLE PRECISION NOT NULL,
	CONSTRAINT "fk_food_item" FOREIGN KEY("food_item_id") REFERENCES "food_item"("id"),
	PRIMARY KEY("id"),
	CONSTRAINT "fk_order" FOREIGN KEY("order_id") REFERENCES "order"("id")
);
