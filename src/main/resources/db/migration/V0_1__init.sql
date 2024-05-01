CREATE EXTENSION IF NOT EXISTS citext;

--FOODS
DROP TABLE IF EXISTS foods;
CREATE TABLE foods
(
    id                  serial primary key,
    barcode             character(13),
    name                citext   not null,
    brand               citext,
    created_date        date     not null default now()::date,
    is_current          bool     not null default true,
    calories            smallint not null,
    fat                 numeric  not null,
    fat_saturated       numeric,
    fat_trans           numeric,
    fat_polyunsaturated numeric,
    fat_monounsaturated numeric,
    cholesterol         smallint,
    carbohydrates       numeric  not null,
    fiber               numeric,
    sugar               numeric,
    protein             numeric  not null,
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
INSERT INTO foods (name, is_current, calories, fat, carbohydrates, fiber, sugar, protein, sodium, potassium, calcium,
                   vitamin_c)
VALUES ('Water', true, 0, 0, 0, 0, 0, 0, 2, 9, 10, 0),
       ('Lemon juice', true, 22, 0.24, 6.9, 0.3, 2.52, 0.35, 1, 103, 6, 38.7),
       ('Lemonade', true, 2, 0.02, 0.69, 0.03, 0.25, 0.04, 1.9, 18.4, 9.6, 3.87);

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
    food_id         integer,
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
    id               serial primary key,
    entry_date       date     not null,
    user_id          text     not null,
    food_id          integer  not null,
    food_unit_id     smallint not null,
    serving_quantity numeric  not null,
    meal             meal     not null
);

--RECIPES
DROP TABLE IF EXISTS recipes;
CREATE TABLE recipes
(
    id            smallserial primary key,
    name          citext unique not null,
    description   text,
    cooked_weight smallint      not null
);
INSERT INTO recipes (name, cooked_weight)
VALUES ('Lemonade', 100);

DROP TABLE IF EXISTS recipe_ingredients;
CREATE TABLE recipe_ingredients
(
    id           serial primary key,
    recipe_id    smallint not null,
    food_id      integer  not null,
    food_unit_id smallint not null,
    quantity     numeric  not null
);
INSERT INTO recipe_ingredients (recipe_id, food_id, food_unit_id, quantity)
VALUES (1, 1, 1, 90),
       (1, 2, 1, 10);
