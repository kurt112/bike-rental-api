-- auto-generated definition
create table if not exists bike
(
    id          int auto_increment
        primary key,
    brand       varchar(255) null,
    created_at  datetime(6)  null,
    description varchar(255) null,
    name        varchar(255) null,
    price       double       null,
    size        double       null,
    updated_at  datetime(6)  null
);

-- auto-generated definition
create table if not exists bike_picture
(
    id    int auto_increment
        primary key,
    image blob null,
    bike  int  not null,
    constraint FKr22hrq2ty38263qm3pdg4mk2a
        foreign key (bike) references bike (id)
);

