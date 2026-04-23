create table admin(
	id serial primary key,
	username varchar(50) not null unique,
	password varchar(255) not null
);

create table product(
	id serial primary key,
	name varchar(100) not null,
	brand varchar(50) not null,
	price decimal(12,2) not null,
	stock int not null
);

create table customer(
	id serial primary key,
	name varchar(100) not null,
	phone varchar(20),
	email varchar(100) unique,
	address varchar(255)
);

create table invoice(
	id serial primary key,
	customer_id int references customer(id),
	created_at timestamp default current_timestamp,
	total_amount decimal(12,2) not null
);

create table invoice_details(
	id serial primary key,
	invoice_id int references invoice(id),
	product_id int references product(id),
	quantity int not null,
	unit_price decimal(12,2) not null
);

-- Các chức năng quản lý điện thoại
-- Kiểm tra sự tồn tại của sản phẩm theo tên
create or replace function isExist_product_name(
	p_name varchar
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from product where name = p_name) then
		return true;
	end if;
	return false;
end;
$$;
-- Kiểm tra sự tồn tại của sản phẩm theo tên (gần đúng)
create or replace function isContains_product_name(
	p_name varchar
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from product where name ilike '%' || p_name || '%') then
		return true;
	end if;
	return false;
end;
$$;
-- Kiểm tra sự tồn tại của sản phẩm theo id
create or replace function isExist_product_id(
	p_id int
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from product where id = p_id) then
		return true;
	end if;
	return false;
end;
$$;
-- Kiểm tra giá bán và số lượng tồn kho của sản phẩm phải lớn hơn 0
create or replace function isCheck_price_stock(
	p_price decimal,
	p_stock int
)
returns boolean
language plpgsql
as $$
begin
	if p_price >= 0 and p_stock >= 0 then
		return true;
	end if;
	return false;
end;
$$;
-- Kiểm tra sự tồn tại của sản phẩm trong kho hàng
create or replace function isCheck_product_stock(
	p_id int
)
returns boolean
language plpgsql
as $$
declare v_stock int;
begin
	select stock into v_stock from product where id = p_id;
	if v_stock > 0 then
		return true;
	end if;
	return false;
end;
$$;
-- 1. Thêm mới sản phẩm
create or replace procedure add_product(
	p_name varchar,
	p_brand varchar,
	p_price decimal,
	p_stock int
)
language plpgsql
as $$
begin
	-- Sản phẩm có name ... đã tồn tại! (exception)
	-- Giá bản sản phẩm phải > 0 (exception)
	-- Sản phẩm phải có lượng tồn kho stock > 0 (exception)
	insert into product(name, brand, price, stock) values (
		p_name, p_brand, p_price, p_stock
	);
end;
$$;
-- 2. Hiển thị thông tin hiện tại của sản phẩm
create or replace function info_product(
	p_id int
)
returns table (
	id int,
	name varchar,
	brand varchar,
	price decimal,
	stock int
)
language plpgsql
as $$
begin
	-- Không tồn tại sản phẩm có id ... (exception)
	return query
	select p.id, p.name, p.brand, p.price, p.stock
	from product p where p.id = p_id;
end;
$$;
-- 3. Cập nhật thông tin của sản phẩm (không cập nhật id)
create or replace procedure update_product(
	p_id int,
	p_name varchar,
	p_brand varchar,
	p_price decimal,
	p_stock int
)
language plpgsql
as $$
begin
	-- Không tồn tại sản phẩm có id ... (exception)
	update product
	set name = p_name,
		brand = p_brand,
		price = p_price,
		stock = p_stock
	where id = p_id;
end;
$$;
-- 4. Xóa sản phẩm
create or replace procedure delete_product(
	p_id int
)
language plpgsql
as $$
begin
	-- Không tồn tại sản phẩm có id ... (exception)
	delete from product where id = p_id;
end;
$$;

-- 5. Hiển thị danh sách sản phẩm
create or replace function list_product()
returns table(
	id int,
	name varchar,
	brand varchar,
	price decimal,
	stock int
)
language plpgsql
as $$
begin
	return query
	select p.id, p.name, p.brand, p.price, p.stock
	from product p
	order by p.id;
end;
$$;
-- 6. Tìm kiếm điện thoại theo brand (tìm kiếm gần đúng)

create or replace function search_product_by_brand(
	p_brand varchar
)
returns table (
	id int,
	name varchar,
	brand varchar,
	price decimal,
	stock int
)
language plpgsql
as $$
begin
	-- Không tồn tại sản phẩm có brand phù hợp ... (exception)
	return query
	select p.id, p.name, p.brand, p.price, p.stock
	from product p
	where p.brand ilike '%' || p_brand || '%'
	order by p.id;
end;
$$;

-- 7. Tìm kiếm sản phẩm theo price range
create or replace function search_product_by_price(
	p_begin_price decimal,
	p_end_price decimal
)
returns table (
	id int,
	name varchar,
	brand varchar,
	price decimal,
	stock int
)
language plpgsql
as $$
begin
	return query
	select p.id, p.name, p.brand, p.price, p.stock
	from product p
	where p.price between p_begin_price and p_end_price
	order by p.id;
end;
$$;
-- Kiểm tra số lượng tồn kho có còn đủ hay không
create or replace function isCheck_product_stock(
	p_name varchar,
	p_stock int
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from product where name ilike '%' || p_name || '%' and stock >= p_stock) then
		return true;
	end if;
	return false;
end;
$$;
-- 8. Tìm kiếm sản phẩm theo tên và số lượng tồn kho
create or replace function search_product_by_name (
    p_name varchar,
    p_stock int
)
returns table(
	id int,
	name varchar,
	brand varchar,
	price decimal,
	stock int
)
language plpgsql
as $$
begin
	return query
	select p.id, p.name, p.brand, p.price, p.stock
	from product p
	where p.name ilike '%' || p_name || '%' and p.stock >= p_stock
	order by p.id;
end;
$$;

-- Chức năng quản lý khách hàng
select * from customer;
-- Kiểm tra sự tồn tại của phone và email của khách hàng
create or replace function isExist_email(
	p_email varchar
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from customer where email ilike p_email) then
		return true;
	end if;
	return false;
end;
$$;
create or replace function isExist_phone(
	p_phone varchar
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from customer where phone ilike p_phone) then
		return true;
	end if;
	return false;
end;
$$;
-- 1. Thêm mới khách hàng
create or replace procedure add_customer(
	p_name varchar,
	p_phone varchar,
	p_email varchar,
	p_address varchar
)
language plpgsql
as $$
begin
	insert into customer(name, phone, email, address) values
	(p_name, p_phone, p_email, p_address);
end;
$$;
-- Kiểm tra sự tồn tại của khách hàng theo id
create or replace function isExist_customer_id(
	p_id int
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from customer where id = p_id) then
		return true;
	end if;
	return false;
end;
$$;
-- Hiển thị thông tin hiện tại của khách hàng
create or replace function info_customer(
	p_id int
)
returns table(
	id int,
	name varchar,
	phone varchar,
	email varchar,
	address varchar
)
language plpgsql
as $$
begin
	return query
	select c.id, c.name, c.phone, c.email, c.address
	from customer c
	where c.id = p_id;
end;
$$;
-- 2. Cập nhật khách hàng
create or replace procedure update_customer(
	p_id int,
	p_name varchar,
	p_phone varchar,
	p_email varchar,
	p_address varchar
)
language plpgsql
as $$
begin
	update customer set name = p_name, phone = p_phone, email = p_email, address = p_address
	where id = p_id;
end;
$$;
-- 3. Xóa khách hàng
create or replace procedure delete_customer(
	p_id int
)
language plpgsql
as $$
begin
	delete from customer where id = p_id;
end;
$$;
-- 4. Hiển thị danh sách
create or replace function list_customer()
returns table(
	id int,
	name varchar,
	phone varchar,
	email varchar,
	address varchar
)
language plpgsql
as $$
begin
	return query
	select c.id, c.name, c.phone, c.email, c.address
	from customer c
	order by c.id;
end;
$$;

-- Quản lý thông tin mua bán
select * from invoice;
-- Lấy thông tin của khách hàng được thêm vào mới nhất
create or replace function info_new_customer()
returns table(
	id int,
	name varchar,
	phone varchar,
	email varchar,
	address varchar
)
language plpgsql
as $$
begin
	return query
	select c.id, c.name, c.phone, c.email, c.address
	from customer c where c.id = (select max(cus.id) from customer cus);
end;
$$;
-- 1. Thêm mới đơn hàng
-- Tạo mới một đơn hàng mới (trống thông tin)
create or replace procedure add_invoice(
	p_customer_id int
)
language plpgsql
as $$
begin
	insert into invoice(customer_id, created_at, total_amount) values
	(p_customer_id, current_timestamp, 0);
end;
$$;
-- Lấy thông tin của đơn hàng được thêm vào mới nhất
create or replace function info_new_invoice()
returns table(
	id int,
	customer_id int,
	created_at timestamp,
	total_amount decimal
)
language plpgsql
as $$
begin
	return query
	select i.id, i.customer_id, i.created_at, i.total_amount
	from invoice i where i.id = (select max(iv.id) from invoice iv);
end;
$$;
select * from invoice_details;
-- Tạo đơn hàng chi tiết
create or replace procedure add_invoice_details(
	p_invoice_id int,
	p_product_id int,
	p_quantity int
)
language plpgsql
as $$
declare
	v_unit_price decimal;
	v_price decimal;
begin
	select p.price into v_price
	from product p where p.id = p_product_id;
	v_unit_price := v_price * p_quantity;
	insert into invoice_details(invoice_id, product_id, quantity, unit_price) values
	(p_invoice_id, p_product_id, p_quantity, v_unit_price);
	update product set stock = stock - p_quantity where id = p_product_id;
end;
$$;
-- Kiểm tra số lượng tồn kho của sản phẩm trước khi được thêm vào đơn hàng
create or replace function check_product_stock(
	p_product_id int,
	p_quantity int
)
returns boolean
language plpgsql
as $$
declare v_stock int;
begin
	select stock into v_stock from product where id = p_product_id;
	if (v_stock >= p_quantity) then
		return true;
	end if;
	return false;
end;
$$;
-- Tạo hoàn thành một đơn hàng hoàn chỉnh
create or replace procedure add_final_invoice(
	p_id int
)
language plpgsql
as $$
declare
	v_total_amount decimal;
begin
	select coalesce(sum(ide.unit_price), 0) into v_total_amount
	from invoice_details ide where ide.invoice_id = p_id;
	update invoice set total_amount = p_total_amount where id = p_id;
end;
$$;
-- Xóa đơn hàng theo id
create or replace procedure delete_invoice(
	p_id int
)
language plpgsql
as $$
begin
	delete from invoice where id = p_id;
end;
$$;
-- 2. Hiển thị danh sách hóa đơn
create or replace function list_invoice()
returns table(
	out_id int,
	out_customer_id int,
	out_created_at timestamp,
	out_total_amount decimal
)
language plpgsql
as $$
begin
	return query
	select i.id, i.customer_id, i.created_at, i.total_amount
	from invoice i
	order by i.id;
end;
$$;
-- Hiển thị danh sách của chi tiết hóa đơn
create or replace function get_invoice_details(
	p_invoice_id int
)
returns table (
	id int,
	invoice_id int,
	product_id int,
	product_name varchar,
	quantity int,
	unit_price decimal
)
language plpgsql
as $$
begin
	return query
	select ide.id, ide.product_id, ide.product_id, p.name, ide.quantity, ide.unit_price
	from invoice_details ide
	join product p on ide.product_id = p.id
	where ide.invoice_id = p_invoice_id
	order by ide.product_id;
end;
$$;
-- 3. Tìm kiếm hóa đơn
-- Tìm theo tên khách hàng
create or replace function search_invoice_by_customer_name(
	p_customer_name varchar
)
returns table(
	id int,
	customer_id int,
	customer_name varchar,
	created_at timestamp,
	total_amount decimal
)
language plpgsql
as $$
begin
	return query
	select i.id, i.customer_id, c.name, i.created_at, i.total_amount
	from customer c
	join invoice i on c.id = i.customer_id and c.name ilike '%' || p_customer_name || '%'
	order by c.id;
end;
$$;
-- Tìm theo ngày/tháng/năm
create or replace function search_invoice_by_date(
	p_date date
)
returns table(
	id int,
	customer_id int,
	customer_name varchar,
	created_at timestamp,
	total_amount decimal
)
language plpgsql
as $$
begin
	return query
	select i.id, c.id, c.name, i.created_at, i.total_amount
	from customer c
	join invoice i on c.id = i.customer_id
	and (i.created_at >= p_date::timestamp and i.created_at < (p_date + interval '1 day')::timestamp)
	order by c.id;
end;
$$;
-- 4. Thống kê doanh thu
-- Doanh thu theo ngày
create or replace function daily_revenue(
	p_date date
)
returns decimal
language plpgsql
as $$
declare v_revenue decimal;
begin
	select sum(total_amount) into v_revenue
	from invoice
	where created_at >= p_date::timestamp and created_at < (p_date + interval '1 day')::timestamp;
	return coalesce(v_revenue, 0);
end;
$$;
-- Doanh thu theo tháng
create or replace function monthly_revenue(
	p_month int,
	p_year int
)
returns decimal
language plpgsql
as $$
declare
	v_date date;
	v_revenue decimal;
begin
	v_date := make_date(p_year, p_month, 1);
	select sum(total_amount) into v_revenue
	from invoice
	where created_at >= v_date::timestamp
	and created_at < (v_date + interval '1 month')::timestamp;
	return coalesce(v_revenue, 0);
end;
$$;
-- Doanh thu theo năm
create or replace function year_revenue(
	p_year int
)
returns decimal
language plpgsql
as $$
declare
	v_date date;
	v_revenue decimal;
begin
	v_date := make_date(p_year, 1, 1);
	select sum(total_amount) into v_revenue
	from invoice
	where created_at >= v_date::timestamp
	and created_at < (v_date + interval '1 year')::timestamp;
	return coalesce(v_revenue, 0);
end;
$$;
-- Xác thực admin
create or replace function check_admin(
	p_username varchar,
	p_password varchar
)
returns boolean
language plpgsql
as $$
begin
	if exists (select 1 from admin where username = p_username and password = p_password) then
		return true;
	end if;
	return false;
end;
$$;
-- data mẫu để test
INSERT INTO product (name, brand, price, stock) VALUES
('iPhone 15 Pro Max', 'Apple', 28500000, 15),
('iPhone 14', 'Apple', 16000000, 10),
('iPhone 13', 'Apple', 13500000, 8),
('Samsung Galaxy S24 Ultra', 'Samsung', 26000000, 12),
('Samsung Galaxy Z Fold5', 'Samsung', 32000000, 5),
('Samsung Galaxy A54', 'Samsung', 8500000, 25),
('Xiaomi 14 Pro', 'Xiaomi', 18000000, 10),
('Redmi Note 13 Pro', 'Xiaomi', 6500000, 30),
('OPPO Reno11', 'OPPO', 10500000, 20),
('OPPO Find N3 Flip', 'OPPO', 21000000, 7),
('iPad Air M2', 'Apple', 17500000, 14),
('MacBook Air M3', 'Apple', 27900000, 9),
('Sony Xperia 1 V', 'Sony', 23000000, 4),
('Google Pixel 8 Pro', 'Google', 19500000, 6),
('Realme GT5', 'Realme', 11000000, 18),
('Vivo V30 Pro', 'Vivo', 12500000, 15),
('Nokia G42 5G', 'Nokia', 4500000, 40),
('ASUS ROG Phone 8', 'ASUS', 24500000, 5),
('Huawei Mate 60 Pro', 'Huawei', 22000000, 3),
('iPhone SE 2024', 'Apple', 11500000, 12);

select * from invoice_details;
select * from customer;
select * from invoice;
select * from admin;
insert into admin(username, password) values
('quan41144', '12345');