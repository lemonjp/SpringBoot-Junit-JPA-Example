
CREATE TABLE IF NOT EXISTS category 
(
  id serial PRIMARY KEY,
  name varchar(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS pet
(
  id serial PRIMARY KEY,
  name varchar(150) NOT NULL,
  quantity integer NOT NULL,
  cat_id bigint NOT NULL,
  CONSTRAINT fk_pet_categoty_id FOREIGN KEY (cat_id)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
