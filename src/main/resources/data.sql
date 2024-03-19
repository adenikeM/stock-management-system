
INSERT INTO user_address( address, city) VALUES ('7, Station Road', 'Osogbo');
INSERT INTO user_address( address, city) VALUES ('Oloje Estate', 'Ilorin');

INSERT INTO user_role(role_title) VALUES ('Sales Attendant');
INSERT INTO user_role(role_title) VALUES ('Admin');

INSERT INTO user_profile( birth_date,user_role_id,address_id, first_name, last_name,email, gender, other_names, password, user_name)
VALUES ('2000-03-03','1','1', 'Tola', 'Ade', 'tola@gmail.com', 'FEMALE', 'Ola', 'Wumit@23','ToksToks');

INSERT INTO user_profile( birth_date,user_role_id, address_id, first_name, last_name,email, gender, other_names, password, user_name)
VALUES ('2001-03-03','2','2', 'Tolu', 'Ade', 'tolu@gmail.com', 'FEMALE', 'Ola', 'Wumih@23','TokToks');

INSERT INTO location(shelf,name)
VALUES ('2','Right Corner');

INSERT INTO product_category(location_id,created_date, category_name)
VALUES ('1','2019-01-05', 'Stationary');

INSERT INTO product(price,product_category_id,available_quantity,expiry_date,manufacture_date,colour,name,size)
VALUES ('5000.00','1', '20','2024-05-05','2020-04-05','Green','Book','Big');

INSERT INTO product(price,product_category_id,available_quantity,expiry_date,manufacture_date,colour,name,size)
VALUES ('3000.00','1', '30','2024-08-08','2021-01-03','Blue','Biro','Medium');

INSERT INTO sale(price,quantity_sold,sale_date,name)
VALUES ('2500.00','10','2023-12-15','Book');

INSERT INTO sale(price,quantity_sold,sale_date,name)
VALUES ('2000','20','2024-02-10','Biro');

INSERT INTO sale_products(products_id,sale_id) VALUES ('1','1');
INSERT INTO sale_products(products_id,sale_id) VALUES ('2','2');








