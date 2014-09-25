
CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


CREATE TABLE manufacturer
(
  manufacturer_id integer NOT NULL,
  description character varying(300),
  home_page character varying(50),
  name character varying(50) NOT NULL,
  CONSTRAINT manufacturer_pkey PRIMARY KEY (manufacturer_id)
);


CREATE TABLE category
(
  category_id character varying(50) NOT NULL,
  description character varying(300),
  sup_category_id character varying(50),
  CONSTRAINT category_pkey PRIMARY KEY (category_id),
  CONSTRAINT fk302bcfef36f2122 FOREIGN KEY (sup_category_id)
      REFERENCES category (category_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE product
(
  product_id integer NOT NULL,
  category_id character varying(50),
  description character varying(300),
  is_offered boolean NOT NULL,
  manufacturer_id integer NOT NULL,
  name character varying(50) NOT NULL,
  price double precision NOT NULL,
  CONSTRAINT product_pkey PRIMARY KEY (product_id),
  CONSTRAINT fked8dccef27420993 FOREIGN KEY (category_id)
      REFERENCES category (category_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fked8dccef83c4033 FOREIGN KEY (manufacturer_id)
      REFERENCES manufacturer (manufacturer_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT product_name_key UNIQUE (name)
);

CREATE TABLE image
(
  image_id integer NOT NULL,
  product_id integer NOT NULL,
  CONSTRAINT image_pkey PRIMARY KEY (image_id),
  CONSTRAINT fk5faa95b5440ffa1 FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE opinion
(
  opinion_id integer NOT NULL,
  content character varying(300) NOT NULL,
  product_id integer NOT NULL,
  CONSTRAINT opinion_pkey PRIMARY KEY (opinion_id),
  CONSTRAINT fkb4edb3825440ffa1 FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
;


CREATE TABLE feature_type
(
  feature_type_id character varying(50) NOT NULL,
  description character varying(300),
  CONSTRAINT feature_type_pkey PRIMARY KEY (feature_type_id)
)
;



CREATE TABLE feature
(
  feature_type_id character varying(50) NOT NULL,
  product_id integer NOT NULL,
  value character varying(300) NOT NULL,
  CONSTRAINT feature_pkey PRIMARY KEY (feature_type_id, product_id),
  CONSTRAINT fkc5a27af65440ffa1 FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkc5a27af6e34ac0e FOREIGN KEY (feature_type_id)
      REFERENCES feature_type (feature_type_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE product_instance
(
  product_instance_id integer NOT NULL,
  added timestamp without time zone NOT NULL,
  product_id integer NOT NULL,
  CONSTRAINT product_instance_pkey PRIMARY KEY (product_instance_id),
  CONSTRAINT fkbe4415e55440ffa1 FOREIGN KEY (product_id)
      REFERENCES product (product_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



CREATE TABLE usr
(
  login character varying(50) NOT NULL,
  email character varying(50) NOT NULL,
  is_enabled boolean NOT NULL,
  password character varying(50) NOT NULL,
  CONSTRAINT usr_pkey PRIMARY KEY (login),
  CONSTRAINT usr_email_key UNIQUE (email)
);

CREATE TABLE admin
(
  login character varying(50) NOT NULL,
  CONSTRAINT admin_pkey PRIMARY KEY (login),
  CONSTRAINT fk586034fddf358c4 FOREIGN KEY (login)
      REFERENCES usr (login) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE client
(
  login character varying(50) NOT NULL,
  family_name character varying(50) NOT NULL,
  given_name character varying(50) NOT NULL,
  phone_number character varying(50) NOT NULL,
  CONSTRAINT client_pkey PRIMARY KEY (login),
  CONSTRAINT fkaf12f3cbddf358c4 FOREIGN KEY (login)
      REFERENCES usr (login) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE delivery_address
(
  delivery_address_id integer NOT NULL,
  city character varying(50) NOT NULL,
  login character varying(50) NOT NULL,
  post_code character varying(10) NOT NULL,
  street character varying(50) NOT NULL,
  CONSTRAINT delivery_address_pkey PRIMARY KEY (delivery_address_id),
  CONSTRAINT fk1d2b2aa91000d60d FOREIGN KEY (login)
      REFERENCES client (login) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE db_status
(
  db_status_id character varying(1) NOT NULL,
  CONSTRAINT db_status_pkey PRIMARY KEY (db_status_id)
)
;

CREATE TABLE ord
(
  order_id integer NOT NULL,
  created timestamp without time zone NOT NULL,
  db_status_id character varying(1) NOT NULL,
  delivery_address_id integer NOT NULL,
  modified timestamp without time zone NOT NULL,
  CONSTRAINT ord_pkey PRIMARY KEY (order_id),
  CONSTRAINT fk1aee15daed8b0 FOREIGN KEY (db_status_id)
      REFERENCES db_status (db_status_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk1aee1f5198918 FOREIGN KEY (delivery_address_id)
      REFERENCES delivery_address (delivery_address_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE ordered_item
(
  order_id integer NOT NULL,
  product_instance_id integer NOT NULL,
  CONSTRAINT ordered_item_pkey PRIMARY KEY (order_id, product_instance_id),
  CONSTRAINT fk60ba77a563555501 FOREIGN KEY (order_id)
      REFERENCES ord (order_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk60ba77a5f350eda0 FOREIGN KEY (product_instance_id)
      REFERENCES product_instance (product_instance_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);




DROP TABLE ordered_item;
DROP TABLE ord;
DROP TABLE db_status;
DROP TABLE delivery_address;
DROP TABLE client;
DROP TABLE admin;
DROP TABLE usr;
DROP TABLE product_instance;
DROP TABLE feature;
DROP TABLE feature_type; 
DROP TABLE opinion;
DROP TABLE image;
DROP TABLE product;
DROP TABLE category;
DROP TABLE manufacturer;

DROP SEQUENCE hibernate_sequence;







