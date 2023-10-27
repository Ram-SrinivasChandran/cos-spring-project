CREATE SCHEMA food_svc;
CREATE SEQUENCE food_svc.food_menu_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE food_svc.food_menu(
        "id" int8 NOT NULL default NEXTVAL('food_svc.food_menu_seq'),
        "name"  VARCHAR(20) NOT NULL,
        "type"  VARCHAR(20) NOT NULL,
        "created_on" TIMESTAMPTZ,
    	"modified_on" TIMESTAMPTZ,
        CONSTRAINT food_menu_pkey PRIMARY KEY (id)
);
select setval('food_svc.food_menu_seq', (select max(id)+1 from food_svc.food_menu), false);

CREATE SEQUENCE food_svc.availability_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE food_svc.availability(
        "id" int8 NOT NULL default NEXTVAL('food_svc.availability_seq'),
        "name"  VARCHAR(20) NOT NULL,
        "day"  VARCHAR(20) NOT NULL,
        "created_on" TIMESTAMPTZ,
    	"modified_on" TIMESTAMPTZ,
        CONSTRAINT availability_pkey PRIMARY KEY (id)
);
select setval('food_svc.availability_seq', (select max(id)+1 from food_svc.availability), false);

CREATE SEQUENCE food_svc.food_menu_availability_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE food_svc.food_menu_availability_map(
        "id" int8 NOT NULL default NEXTVAL('food_svc.food_menu_availability_map_seq'),
        "food_menu_id"  int8 NOT NULL,
        "availability_id"  int8 NOT NULL,
        "created_on" TIMESTAMPTZ,
    	"modified_on" TIMESTAMPTZ,
    	CONSTRAINT fk_food_menu_id
        FOREIGN KEY(food_menu_id)
        REFERENCES food_svc."food_menu"(id)MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
        CONSTRAINT fk_availability_id
        FOREIGN KEY(availability_id)
        REFERENCES food_svc."availability"(id)MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION,
        CONSTRAINT food_menu_availability_map_pkey PRIMARY KEY (id)
);
select setval('food_svc.food_menu_availability_map_seq', (select max(id)+1 from food_svc.food_menu_availability_map), false);

CREATE SEQUENCE food_svc.food_item_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE food_svc.food_item(
        "id" int8 NOT NULL default NEXTVAL('food_svc.food_item_seq'),
        "name"  VARCHAR(20) NOT NULL,
        "cost"  DOUBLE PRECISION NOT NULL,
        "quantity" INT NOT NULL,
        "created_on" TIMESTAMPTZ,
    	"modified_on" TIMESTAMPTZ,
        CONSTRAINT food_item_pkey PRIMARY KEY (id)
);
select setval('food_svc.food_item_seq', (select max(id)+1 from food_svc.food_item), false);

CREATE SEQUENCE food_svc.food_menu_food_item_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE food_svc.food_menu_food_item_map(
        "id" int8 NOT NULL default NEXTVAL('food_svc.food_menu_food_item_map_seq'),
        "food_menu_id"  int8 NOT NULL,
        "food_item_id"  int8 NOT NULL,
        "created_on" TIMESTAMPTZ,
    	"modified_on" TIMESTAMPTZ,
    	CONSTRAINT fk_food_menu_id
    	FOREIGN KEY(food_menu_id)
    	REFERENCES food_svc."food_menu"(id)MATCH SIMPLE
    	ON UPDATE NO ACTION ON DELETE NO ACTION,
    	CONSTRAINT fk_food_item_id
    	FOREIGN KEY(food_item_id)
    	REFERENCES food_svc."food_item"(id)MATCH SIMPLE
    	ON UPDATE NO ACTION ON DELETE NO ACTION,
        CONSTRAINT food_menu_food_item_map_pkey PRIMARY KEY (id)
);
select setval('food_svc.food_menu_food_item_map_seq', (select max(id)+1 from food_svc.food_menu_food_item_map), false);

CREATE SCHEMA user_svc;
CREATE SEQUENCE user_svc.role_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE user_svc.role(
        "id" int8 NOT NULL default NEXTVAL('user_svc.role_seq'),
        "name"  VARCHAR(20) NOT NULL,
        "description"  VARCHAR(20) NOT NULL,
        "created_on" TIMESTAMPTZ,
    	"modified_on" TIMESTAMPTZ,
        CONSTRAINT role_pkey PRIMARY KEY (id)
);
select setval('user_svc.role_seq', (select max(id)+1 from user_svc.role), false);

CREATE SEQUENCE user_svc.user_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE user_svc."user" (
	id int8 NOT NULL default NEXTVAL('user_svc.user_seq'),
	"name" varchar(20) NOT NULL,
	user_name varchar(20) NOT NULL UNIQUE,
	"password" varchar(20) NOT NULL,
	created_on timestamptz NULL,
	modified_on timestamptz NULL,
	CONSTRAINT user_pkey PRIMARY KEY (id)
);
select setval('user_svc.user_seq', (select max(id)+1 from user_svc.user), false);

CREATE SEQUENCE user_svc.user_role_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE user_svc.user_role_map (
	id int8 NOT NULL DEFAULT nextval('user_svc.user_role_map_seq'),
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	created_on timestamptz NULL,
	modified_on timestamptz NULL,
	CONSTRAINT fk_user_id 
	FOREIGN KEY (user_id) 
	REFERENCES user_svc."user"(id),
	CONSTRAINT fk_role_id 
	FOREIGN KEY (role_id) 
	REFERENCES user_svc.role(id),
	CONSTRAINT user_role_map_pkey PRIMARY KEY (id)
);
select setval('user_svc.user_role_map_seq', (select max(id)+1 from user_svc.user_role_map), false);

CREATE SEQUENCE user_svc.user_address_map_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
CREATE TABLE user_svc.user_address_map (
	id int8 NOT NULL DEFAULT nextval('user_svc.user_address_map_seq'),
	user_id int8 NOT NULL,
	door_number VARCHAR(20) NOT NULL,
	street_name VARCHAR(20) NOT NULL,
	city VARCHAR(20) NOT NULL,
	district VARCHAR(20) NOT NULL,
	state VARCHAR(20) NOT NULL,
	pincode VARCHAR(20) NOT NULL,
	landmark VARCHAR(20) NOT NULL,
	created_on timestamptz NULL,
	modified_on timestamptz NULL,
	CONSTRAINT fk_user_id 
	FOREIGN KEY (user_id) 
	REFERENCES user_svc."user"(id),
	CONSTRAINT user_address_map_pkey PRIMARY KEY (id)
);
select setval('user_svc.user_address_map_seq', (select max(id)+1 from user_svc.user_address_map), false);

CREATE SCHEMA order_svc;
CREATE SEQUENCE order_svc.order_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;
CREATE TABLE order_svc."order" (
	id int8 NOT NULL DEFAULT nextval('order_svc.order_seq'),
	user_id int8 NOT NULL,
	total_cost float8 NOT NULL,
	email varchar(20) NOT NULL,
	phone_number int8 NOT NULL,
	status varchar(20) NOT NULL,
	order_on timestamptz NULL,
	delivery_on timestamptz NULL,
	created_on timestamptz NULL,
	modified_on timestamptz NULL,
	user_address_id int8 NOT NULL,
	CONSTRAINT fk_user_address_id 
	FOREIGN KEY (user_address_id) 
	REFERENCES user_svc.user_address_map(id),
	CONSTRAINT fk_user_id 
	FOREIGN KEY (user_id) 
	REFERENCES user_svc."user"(id),
	CONSTRAINT order_pkey PRIMARY KEY (id)
);
select setval('order_svc.order_seq', (select max(id)+1 from order_svc.order), false);

CREATE SEQUENCE order_svc.order_item_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;
CREATE TABLE order_svc.order_item (
	id int8 NOT NULL DEFAULT nextval('order_svc.order_item_seq'),
	order_id int8 NOT NULL,
	food_item_id int8 NOT NULL,
	quantity int4 NOT NULL,
	"cost" float8 NOT NULL,
	created_on timestamptz NULL,
	modified_on timestamptz NULL,
	CONSTRAINT fk_food_item_id 
	FOREIGN KEY (food_item_id) 
	REFERENCES food_svc.food_item(id),
	CONSTRAINT fk_order_id 
	FOREIGN KEY (order_id) 
	REFERENCES order_svc."order"(id),
	CONSTRAINT order_item_pkey PRIMARY KEY (id)
);
select setval('order_svc.order_item_seq', (select max(id)+1 from order_svc.order_item ), false);