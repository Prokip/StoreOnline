create table `hibernate_sequence`
(
    `next_val` bigint
) engine = MyISAM;

insert into `hibernate_sequence`
values (1);

create table `card`
(
    `id`         bigint not null,
    `is_active`  boolean,
    `quantity`   integer,
    `total`      integer,
    `order_id`   bigint,
    `product_id` bigint,
    primary key (`id`)
) engine = MyISAM;

create table `category`
(
    `id`                 bigint not null,
    `name`               varchar(255),
    `parent_category_id` bigint,
    primary key (`id`)
) engine = MyISAM;

create table `feature`
(
    `id`   bigint not null,
    `name` varchar(255),
    primary key (`id`)
) engine = MyISAM;

create table `feature_key`
(
    `id`         bigint not null,
    `name`       varchar(255),
    `feature_id` bigint,
    primary key (`id`)
) engine = MyISAM;

create table `order`
(
    `id`           bigint not null,
    `create_date`  varchar(255),
    `delivery`     integer,
    `number_order` varchar(255),
    `status`       varchar(100),
    `total_amount` integer,
    `user_id`      bigint,
    primary key (`id`)
) engine = MyISAM;

create table `product`
(
    `id`          bigint not null,
    `code_unit`   varchar(100),
    `create_date` datetime,
    `description` varchar(255),
    `is_active`   boolean,
    `max_price`   integer,
    `name`        varchar(150),
    `price`       integer,
    `category_id` bigint,
    primary key (`id`)
) engine = MyISAM;

create table `product_file`
(
    `id`   bigint not null,
    `path` varchar(255),
    primary key (`id`)
) engine = MyISAM;

create table `product_image`
(
    `id`   bigint not null,
    `path` varchar(255),
    primary key (`id`)
) engine = MyISAM;

create table `roles`
(
    `id`   bigint not null,
    `role` varchar(20),
    primary key (`id`)
) engine = MyISAM;

create table `user`
(
    `id`           bigint not null,
    `city`         varchar(100),
    `country_code` varchar(3),
    `create_date`  datetime,
    `discount`     integer,
    `email`        varchar(200) not null,
    `first_name`   varchar(200) not null,
    `last_name`    varchar(200),
    `password`     varchar(150) not null,
    `phone`        varchar(200),
    `street`       varchar(200),
    primary key (`id`)
) engine = MyISAM;

create table `products_with_feature`
(
    `product_id`     bigint not null,
    `feature_key_id` bigint not null,
    primary key (`product_id`, `feature_key_id`)
) engine = MyISAM;

create table `products_with_files`
(
    `product_id` bigint not null,
    `file_id`    bigint not null,
    primary key (`product_id`, `file_id`)
) engine = MyISAM;

create table `products_with_images`
(
    `product_id` bigint not null,
    `image_id`   bigint not null,
    primary key (`product_id`, `image_id`)
) engine = MyISAM;

create table `user_roles`
(
    `user_id`  bigint not null,
    `roles_id` bigint not null,
    primary key (`user_id`, `roles_id`)
) engine = MyISAM;

create table `users_favourite_products`
(
    `user_id`    bigint not null,
    `product_id` bigint not null,
    primary key (`user_id`, `product_id`)
) engine = MyISAM;

alter table `user`
    add constraint `user_email` unique (`email`);

alter table `card`
    add constraint `card_order_fk`
    foreign key (`order_id`) references `order` (`id`);

alter table `card`
    add constraint `card_product_fk`
    foreign key (`product_id`) references `product` (`id`);

alter table `category`
    add constraint `category_parent_category_fk`
    foreign key (`parent_category_id`) references `category` (`id`);

alter table `feature_key`
    add constraint `feature_key_feature_fk`
    foreign key (`feature_id`) references `feature` (`id`);

alter table `order`
    add constraint `order_user_fk`
    foreign key (`user_id`) references `user` (`id`);

alter table `product`
    add constraint `product_category_fk`
    foreign key (`category_id`) references `category` (`id`);

alter table `products_with_feature`
    add constraint `feature_fk`
    foreign key (`feature_key_id`) references `feature_key` (`id`);

alter table `products_with_feature`
    add constraint `product_fk`
    foreign key (`product_id`) references `product` (`id`);

alter table `products_with_files`
    add constraint `file_fk`
    foreign key (`file_id`) references `product_file` (`id`);

alter table `products_with_files`
    add constraint `product_fk`
    foreign key (`product_id`) references `product` (`id`);

alter table `products_with_images`
    add constraint `image_fk`
    foreign key (`image_id`) references `product_image` (`id`);

alter table `products_with_images`
    add constraint `product_fk`
    foreign key (`product_id`) references `product` (`id`);

alter table `user_roles`
    add constraint `roles_fk`
    foreign key (`roles_id`) references `roles` (`id`);

alter table `user_roles`
    add constraint `user_roles_fk`
    foreign key (`user_id`) references `user` (`id`);

alter table `users_favourite_products`
    add constraint `product_fk`
    foreign key (`product_id`) references `product` (`id`);

alter table `users_favourite_products`
    add constraint `user_fk`
    foreign key (`user_id`) references `user` (`id`);