
-- Categories
INSERT INTO category (id, name) VALUES (1, 'Dog');
INSERT INTO category (id, name) VALUES (3, 'Cat');
INSERT INTO category (id, name) VALUES (4, 'Bird');
INSERT INTO category (id, name) VALUES (5, 'Reptile');
INSERT INTO category (id, name) VALUES (6, 'Rodent');
INSERT INTO category (id, name) VALUES (7, 'Horse');
INSERT INTO category (id, name) VALUES (8, 'Fish');

-- Insert some animals for tests
INSERT INTO pet (name, quantity, cat_id) VALUES ('Labrador chocolate', 3, 1);
INSERT INTO pet (name, quantity, cat_id) VALUES ('Yellow bird', 2, 4);