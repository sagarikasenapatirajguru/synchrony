create table users (login varchar(200) PRIMARY KEY,password VARCHAR(200),firstName varchar(200), lastName varchar(200),
emailId varchar(200), profession varchar(200),imageLink varchar(200));

create table user_images (image_id varchar(200) PRIMARY KEY,imageLink varchar(200),deleteHash VARCHAR(200),login varchar(200),
foreign key (login)  references users(login));