DROP TABLE IF EXISTS etl;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS bank;


DROP TABLE IF EXISTS etl_user_group;
DROP TABLE IF EXISTS etl_group;
DROP TABLE IF EXISTS etl_user;
DROP TABLE IF EXISTS etl_to_group;


CREATE TABLE bank (
  id INTEGER   PRIMARY KEY ,
  name VARCHAR(40) NOT NULL
 );


 CREATE TABLE etl_user (
  id INTEGER   PRIMARY KEY auto_increment,
  username VARCHAR(40) NOT NULL,
  status  VARCHAR(10) NOT NULL
 );


 CREATE TABLE etl_group (
  id INTEGER   PRIMARY KEY auto_increment,
  groupname VARCHAR(40) NOT NULL
 );

-- CREATE TABLE etl_user_group (
--  id INTEGER   PRIMARY KEY auto_increment,
--  user_id INTEGER,
--  group_id INTEGER,
--  FOREIGN KEY (user_id) REFERENCES etl_user(id),  
--  FOREIGN KEY (group_id) REFERENCES etl_group(id)  
-- );
 
 
 
 
CREATE TABLE etl (
  id INTEGER   PRIMARY KEY auto_increment,
  bank_id INTEGER ,
  name VARCHAR(40) NOT NULL,
  description VARCHAR(40) ,
  folder VARCHAR(40) NOT NULL,
  schedule_crone VARCHAR(40) NOT NULL,
  FOREIGN KEY (bank_id) REFERENCES bank(id)  
);


--CREATE TABLE etl_to_group (
--  id INTEGER   PRIMARY KEY auto_increment,
--  etl_id INTEGER,
--  group_id INTEGER,
--  FOREIGN KEY (group_id) REFERENCES etl_group(id),  
--  FOREIGN KEY (etl_id) REFERENCES etl(id)
--);
  



CREATE TABLE transaction (
  id INTEGER   PRIMARY KEY auto_increment,
  bank_id INTEGER ,
  name VARCHAR(40) ,
  description VARCHAR(40) ,
  amount DOUBLE NOT NULL,
  created_date TIMESTAMP,
  FOREIGN KEY (bank_id) REFERENCES bank(id)
);

 

insert into   bank(id,name) value ( 10,'Leumi');
insert into   bank(id,name) value ( 12,'Poalim');

insert into   etl(id,bank_id,name,description,folder,schedule_crone) values( null,12,'Poalim_etl','Poalim',"C:\\videoturnstile\\data","20 * * * * ?");
insert into   etl(id,bank_id,name,description,folder,schedule_crone) values( null,10,'Leumi_etl','Leumi',"C:\\videoturnstile\\data","50 * * * * ?");
  
