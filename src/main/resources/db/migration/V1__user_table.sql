-- auto-generated definition
create table if not exists  user
(
    id                        int auto_increment
        primary key,
    birthdate                 datetime(6)  null,
    created_at                datetime(6)  null,
    email                     varchar(255) null,
    first_name                varchar(255) null,
    gender                    varchar(255) null,
    is_account_not_expired    bit          null,
    is_account_not_locked     bit          null,
    is_credential_not_expired bit          null,
    is_enable                 bit          null,
    last_name                 varchar(255) null,
    middle_name               varchar(255) null,
    user_password             varchar(255) null,
    picture                   text         null,
    sufix                     varchar(255) null,
    updated_at                datetime(6)  null,
    role                      varchar(255) null
);
