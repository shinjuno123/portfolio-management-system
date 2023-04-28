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
    id              varchar(36) not null,
    description     text        not null,
    `period`        text        not null,
    school          text        not null,
    degree          text        not null,
    region_country  text        not null,
    face_image_path text        not null,
    uploaded        datetime    not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


create table if not exists project
(
    id          varchar(36)  not null,
    image_path  text         not null,
    title       varchar(255) not null,
    description text         not null,
    url         varchar(255) not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE if not exists contact
(
    id              VARCHAR(36)  NOT NULL,
    closing_title   TEXT         NOT NULL,
    closing_content TEXT         NOT NULL,
    closing_regard  TEXT         NOT NULL,
    button_content  TEXT         NOT NULL,
    email           VARCHAR(255) NOT NULL,
    uploaded        DATETIME     NOT NULL,
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
    detail   text         not null,
    uploaded datetime     not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists noteworthy_project
(
    id          varchar(36)  not null,
    title       varchar(255) not null,
    description text         not null,
    url         varchar(255) not null,
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table tech_category
(
    id            VARCHAR(36)  NOT NULL,
    category_name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

create table if not exists tech_category_item
(
    id               varchar(36)  not null,
    score            integer      not null,
    stack_name       varchar(255) not null,
    tech_category_id varchar(36)  not null,
    primary key (id),
    unique key uk_tech_category_item_stack_name (stack_name),
    constraint fk_tech_category_item_tech_category foreign key (tech_category_id)
        references tech_category (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


