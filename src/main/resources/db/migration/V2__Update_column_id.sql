alter table item drop constraint fk_id_product;
alter table item drop constraint fk_id_order;

alter table product drop constraint pk_id_product;
alter table order_table drop constraint pk_id_order;
alter table item drop constraint pk_id_item;

alter table product alter column id_product type UUID USING id_product::uuid;
alter table product add constraint pk_id_product primary key(id_product);


alter table order_table alter column id_order type UUID USING id_order::uuid;
alter table order_table add constraint pk_id_order primary key(id_order);

alter table item alter column id_item type UUID USING id_item::uuid;
alter table item alter column id_product type UUID USING id_product::uuid;
alter table item alter column id_order type UUID USING id_order::uuid;

alter table item add constraint pk_id_item primary key(id_item);
alter table item add constraint fk_id_product foreign key(id_product) references product(id_product);
alter table item add constraint fk_id_order foreign key(id_order) references order_table(id_order);