create table if not exists user
(
    id            char(36)     not null,
    name          varchar(100) not null,
    email         varchar(255) not null UNIQUE,
    password      varchar(255) not null,
    mobile_number VARCHAR(255),
    role          varchar(255),
    created_at    timestamp    not null default current_timestamp,
    updated_at    timestamp    not null default current_timestamp on update CURRENT_TIMESTAMP,
    primary key (id)
);


create table if not exists about
(
    id              varchar(36)  not null,
    description     text         not null,
    `period`        text         not null,
    name            varchar(200) not null,
    school          text         not null,
    diploma         text         not null,
    diploma_url     text         not null,
    region_country  text         not null,
    face_image_path text         not null,
    uploaded        datetime     not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


create table if not exists certification
(
    id           varchar(36) not null,
    name         text        not null,
    download_url text        not null,
    updated      datetime    not null,
    uploaded     datetime    not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


create table if not exists project
(
    id           varchar(36)  not null,
    image_path   text         not null,
    project_name varchar(200) not null,
    url          varchar(255) not null,
    updated      datetime     not null,
    uploaded     datetime     not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE if not exists contact
(
    id       VARCHAR(36) NOT NULL,
    email    TEXT        NOT NULL,
    subject  TEXT        NOT NULL,
    content  TEXT        NOT NULL,
    uploaded DATETIME    NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


create table if not exists introduction
(
    id       varchar(36)  not null,
    say_hi   TEXT         not null,
    name     varchar(255) not null,
    opening  text         not null,
    uploaded datetime     not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


create table if not exists experience
(
    id             varchar(36)  not null,
    title          varchar(200) not null,
    img_path       text         not null,
    company        text         not null,
    position_name  text         not null,
    status         text         not null,
    working_period text         not null,
    description    text         not null,
    uploaded       datetime     not null,
    updated        datetime     not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


create table if not exists first_category
(
    id       varchar(36) not null,
    name     varchar(30) not null,
    uploaded datetime    not null,
    updated  datetime    not null,
    unique key (name),
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists second_category
(
    id          varchar(36) not null,
    name        varchar(30) not null,
    first_category_id varchar(36) not null,
    uploaded    datetime    not null,
    updated     datetime    not null,
    constraint fk_platform_category foreign key (first_category_id)
        references first_category (id),
    constraint category_unique_key unique key (name, first_category_id),
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists skill_set_item
(
    id          varchar(36) not null,
    title       varchar(30)  not null,
    image_path  text         not null,
    description text         not null,
    uploaded    datetime     not null,
    updated     datetime     not null,
    second_category_id varchar(200) not null,
    constraint fk_category_skill_set_item foreign key (second_category_id)
        references second_category (id),
    constraint skill_set_item_unique_key unique key (second_category_id, title),
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists relevant_project
(
    id                varchar(36)  not null,
    name              varchar(100) not null,
    url               text         not null,
    uploaded          datetime     not null,
    updated           datetime     not null,
    skill_set_item_id varchar(36)  not null,
    constraint fk_skill_set_item_relevant_project foreign key (skill_set_item_id)
        references skill_set_item (id),
    constraint relevant_project_unique_key unique key (skill_set_item_id, url(400)),
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;








