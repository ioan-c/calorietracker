CREATE EXTENSION IF NOT EXISTS citext;

--FOODS
DROP TABLE IF EXISTS foods;
CREATE TABLE foods
(
    id                  UUID primary key not null default gen_random_uuid(),
    barcode             character(13),
    name                citext           not null,
    brand               citext,
    created_date        date             not null default now()::date,
    is_current          bool             not null default true,
    calories            smallint         not null,
    fat                 numeric          not null,
    fat_saturated       numeric,
    fat_trans           numeric,
    fat_polyunsaturated numeric,
    fat_monounsaturated numeric,
    cholesterol         smallint,
    carbohydrates       numeric          not null,
    fiber               numeric,
    sugar               numeric,
    protein             numeric          not null,
    sodium              smallint,
    potassium           smallint,
    calcium             smallint,
    iron                smallint,
    vitamin_a           smallint,
    vitamin_c           smallint,
    vitamin_d           smallint
);
CREATE UNIQUE INDEX unique_barcode_current_true_idx ON foods (barcode) WHERE is_current;
CREATE UNIQUE INDEX unique_name_current_true_idx ON foods (name, brand) WHERE is_current;

--UNITS
DROP TABLE IF EXISTS units;
CREATE TABLE units
(
    name   citext primary key not null,
    abbrev citext unique
);
INSERT INTO units(name, abbrev)
VALUES ('gram', 'g');

--FOODS and UNITS
DROP TABLE IF EXISTS food_units;
CREATE TABLE food_units
(
    id              smallserial primary key,
    food_id         UUID,
    unit            text    not null,
    weight_in_grams numeric not null,
    unique nulls not distinct (food_id, unit)
);

INSERT INTO food_units(unit, weight_in_grams)
VALUES ('gram', 1);

--DIARY ENTRIES
DROP TABLE IF EXISTS diary_entries;
DROP TYPE IF EXISTS meal;

CREATE TYPE meal AS ENUM ('BREAKFAST', 'LUNCH', 'DINNER', 'SNACKS');
CREATE TABLE diary_entries
(
    id               UUID primary key not null default gen_random_uuid(),
    entry_date       date             not null,
    user_id          UUID             not null,
    food_id          UUID             not null,
    food_unit_id     smallint         not null,
    serving_quantity numeric          not null,
    meal             meal             not null
);

--USERS
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         UUID primary key not null default gen_random_uuid(),
    username   citext unique    not null,
    first_name citext           not null,
    last_name  citext           not null,
    unique (first_name, last_name)
);
INSERT INTO users(id, username, first_name, last_name)
VALUES ('86acb379-eab9-42c5-91f7-33b9c193756d', 'marshmy', 'Mar', 'Shmy'),
       ('08b63360-b996-4a01-91c8-74b044850c30', 'jelly', 'Je', 'Lly');
