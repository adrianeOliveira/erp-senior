create table order_table (
	id_order varchar(40) not null unique,
	status varchar(10) not null,
	constraint pk_id_order primary key(id_order)
);

create table product (
	id_product varchar(40) not null unique,
	name varchar(200) not null,
	price decimal(10,9) not null,
	is_service boolean not null,
	is_active boolean not null,
	constraint pk_id_product primary key(id_product)
);

create table item (
	id_item varchar(40) not null unique,
	unit_price decimal(10,2) not null,
	discount decimal(10,2) null,
	id_order varchar(40) not null,
	id_product varchar(40) not null,
	quantity int not null,
	constraint pk_id_item primary key(id_item),
	constraint fk_id_order foreign key(id_order) references order_table(id_order),
	constraint fk_id_product foreign key(id_product) references product(id_product)
);


insert into product values('08e47d0b-c7ee-4a26-81ff-d90655386366','Escorredor de pia',9.99,false,true),
						('47ed6f0c-41a7-477c-9a05-daafba841f6e','Delivery de pedido',5.5,true,false),
						('71722fe1-f63a-4331-aae8-16f6f1401ed7','Prato Cerâmica Porto',3.8,false,true);

insert into order_table values('4484bf5d-c758-40c4-a816-b9bc5cc45ea6','ABERTA');

insert into item values('4cb02b5b-fe45-4511-964a-9c72006f5679', 10.0, null,
'4484bf5d-c758-40c4-a816-b9bc5cc45ea6','08e47d0b-c7ee-4a26-81ff-d90655386366', 1);