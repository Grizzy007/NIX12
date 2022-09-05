USE `heroku_798da66bf65e891` ;

create table engine
(
    id     varchar(255) not null
        primary key,
    brand  varchar(255) null,
    valves int          not null,
    volume double       not null
);

create table auto
(
    id           varchar(255)   not null
        primary key,
    created      datetime(6)    null,
    manufacturer varchar(255)   null,
    model        varchar(255)   null,
    price        decimal(19, 2) null,
    bodyType     varchar(255)   null,
    Engine_id    varchar(255)   null,
    constraint FKpk2544ixoamra9efk136rh5ar
        foreign key (Engine_id) references engine (id)
);

create table flyway_schema_history
(
    installed_rank int                                 not null
        primary key,
    version        varchar(50)                         null,
    description    varchar(200)                        not null,
    type           varchar(20)                         not null,
    script         varchar(1000)                       not null,
    checksum       int                                 null,
    installed_by   varchar(100)                        not null,
    installed_on   timestamp default CURRENT_TIMESTAMP not null,
    execution_time int                                 not null,
    success        tinyint(1)                          not null
);

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);

create table invoice
(
    id      varchar(255) not null
        primary key,
    created datetime(6)  null
);

create table motorcycle
(
    id           varchar(255)   not null
        primary key,
    created      datetime(6)    null,
    manufacturer varchar(255)   null,
    model        varchar(255)   null,
    price        decimal(19, 2) null,
    landing      int            not null,
    Engine_id    varchar(255)   null,
    constraint FK6mr900dumwnx5287nhkop376x
        foreign key (Engine_id) references engine (id)
);

create table truck
(
    id           varchar(255)   not null
        primary key,
    created      datetime(6)    null,
    manufacturer varchar(255)   null,
    model        varchar(255)   null,
    price        decimal(19, 2) null,
    capacity     int            not null,
    Engine_id    varchar(255)   null,
    constraint FKgajqmf9ur7w5s3v3h4wb6bxba
        foreign key (Engine_id) references engine (id)
);

create table vehicle
(
    DTYPE        varchar(31)    not null,
    id           varchar(255)   not null
        primary key,
    created      datetime(6)    null,
    manufacturer varchar(255)   null,
    model        varchar(255)   null,
    price        decimal(19, 2) null,
    landing      int            null,
    capacity     int            null,
    bodyType     varchar(255)   null,
    Engine_id    varchar(255)   null,
    constraint FK25yaj8eh3o6cyjiuqoppug81q
        foreign key (Engine_id) references engine (id)
);

create table vehicles_in_invoice
(
    invoice_id varchar(255) not null,
    vehicle_id varchar(255) not null,
    primary key (invoice_id, vehicle_id),
    constraint FK2pb1264eg2y0i7jgkokbrwenr
        foreign key (vehicle_id) references vehicle (id),
    constraint FKae9ww6hksv9hxcu9q11ybb02e
        foreign key (invoice_id) references invoice (id)
);

