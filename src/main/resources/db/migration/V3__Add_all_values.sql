insert into `user` (`id`, `city`, `create_date`, `discount`, `email`,
                         `first_name`, `last_name`, `password`, `street`, `country_code`)
values
    ('3', 'City1', '2022-07-01 21:23:22', '10', 'First@admin.com',
    'First', 'Check', 'First123@', 'Street1', 'UA'),
    ('4', 'City2', '2022-07-01 21:23:22', '10', 'Second@admin.com',
    'Second', 'Check', 'Second123@', 'Street2', 'UA'),
    ('5', 'City3', '2022-07-01 21:23:22', '3', 'Third@admin.com',
    'Third', 'Check', 'Third123@', 'Street3', 'UA'),
    ('6', 'City4', '2022-07-01 21:23:22', '3', 'Four@admin.com',
    'Four', 'Check', 'Four123@', 'Street4', 'UA'),
    ('7', 'City5', '2022-07-01 21:23:22', '3', 'Five@admin.com',
    'Five', 'Check', 'Five123@', 'Street5', 'UA');

insert into `user_roles`(`user_id`,`roles_id`)
 values
('3', '1'),
('4', '1'),
('5', '2'),
('6', '2'),
('7', '2');

insert into `category` (`id`, `name`, `parent_category_id`)
values
('1', 'Generators', null),
('2', 'Hand Tools', null),
('3', 'Power Tools', null),
('4', 'Industrial', '1'),
('5', 'Portable', '1'),
('6', 'Standby', '1'),
('7', 'Levels', '2'),
('8', 'Drills', '3'),
('9', 'Saws', '3'),
('10', 'Hammers & Mallets', '2'),
('11', 'Tool Boxes', '2'),
('12', 'Storage', '11'),
('13', 'Mallets', '10'),
('14', 'Hammers', '10'),
('15', 'Wrenches', '2');

insert into `feature` (`id`, `name`)
values
('16', 'Brand'),
('17', 'Country'),
('18', 'Color');

insert into `feature_key` (`id`, `name`, `feature_id`)
values
('19', 'DeWalt', '16'),
('20', 'Polax', '16'),
('21', 'DWT', '16'),
('22', 'China', '17'),
('23', 'USA', '17'),
('24', 'Poland', '17'),
('25', 'Yellow', '18'),
('26', 'Green', '18'),
('27', 'Orange', '18');

insert into `product` (`id`, `code_unit`, `create_date`, `description`,
`is_ACTIVE`, `max_price`, `name`, `price`, `category_id`)
values
('28', '01A023', '2022-07-01 12:23:06', 'somth about product1', true, '8000', 'wrenche', '7600', '15'),
('29', '02A023', '2022-07-01 12:24:06', 'somth about product2', true, '24000', 'tool box', '23000', '11'),
('30', '03A023', '2022-07-01 12:24:06', 'somth about product3', true, '220000', 'drill', '205000', '8'),
('31', '04A023', '2022-07-01 12:24:06', 'somth about product4', true, '600000', 'generator', '590000', '6');

insert into `products_with_feature` (`product_id`, `feature_key_id`)
values
('28', '20'),
('29', '20'),
('30', '19'),
('31', '21'),
('28', '24'),
('29', '27'),
('30', '23'),
('31', '26'),
('28', '27'),
('29', '22'),
('30', '25'),
('31', '22');

insert into `hibernate_sequence`
values (32);