create table if not exists user(
first_name varchar(255),
last_name varchar(255),
password varchar(255),
mail_id varchar(255),
primary key(mail_id)
);

create table if not exists train(
train_no float,
train_name varchar(255),
from_station varchar(255),
to_station varchar(255),
seats int,
fare double,
primary key(train_no)
);

create table if not exists booking_details(
trans_id varchar(255),
mail_id varchar(255),
train_no float,
date varchar(255),
from_station varchar(255),
to_station varchar(255),
primary key (trans_id),
FOREIGN KEY (mail_id) references user(mail_id)
on delete cascade
);