CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table order_table (
	order_id UUID not null unique default uuid_generate_v4(),
	status varchar(10) not null,
	constraint pk_order_id primary key(order_id)
);

create table product (
	product_id UUID not null unique default uuid_generate_v4(),
	name varchar(200) not null,
	price decimal(10,9) not null,
	is_service boolean not null,
	is_active boolean not null,
	constraint pk_product_id primary key(product_id)
);

create table item (
	item_id UUID not null unique default uuid_generate_v4(),
	unit_price decimal(10,2) not null,
	discount decimal(10,2) null,
	order_id UUID not null,
	product_id UUID not null,
	quantity int not null,
	constraint pk_item_id primary key(item_id),
	constraint fk_order_id foreign key(order_id) references order_table(order_id),
	constraint fk_product_id foreign key(product_id) references product(product_id)
);


insert into product values('08e47d0b-c7ee-4a26-81ff-d90655386366','Escorredor de pia',9.99,false,true),
						('47ed6f0c-41a7-477c-9a05-daafba841f6e','Delivery de pedido',5.50,true,false),
						('71722fe1-f63a-4331-aae8-16f6f1401ed7','Prato Cerâmica Porto',3.80,false,true);

insert into order_table values('4484bf5d-c758-40c4-a816-b9bc5cc45ea6','ABERTA');

insert into item values('4cb02b5b-fe45-4511-964a-9c72006f5679', 10.00, null,
'4484bf5d-c758-40c4-a816-b9bc5cc45ea6','08e47d0b-c7ee-4a26-81ff-d90655386366', 1);