CREATE TABLE IF NOT EXISTS foods (
    id UUID primary key not null default gen_random_uuid(),
    barcode character(13) unique,
    name text,
    brand text,
    created_date date,
    calories smallint,
    fat numeric,
    fat_saturated numeric,
    fat_trans numeric,
    fat_polyunsaturated numeric,
    fat_monounsaturated numeric,
    cholesterol smallint,
    carbohydrates numeric,
    fiber numeric,
    sugar numeric,
    protein numeric,
    sodium smallint,
    potassium smallint,
    calcium smallint,
    iron smallint,
    vitamin_a smallint,
    vitamin_c smallint,
    vitamin_d smallint
);

CREATE TABLE IF NOT EXISTS users (
    id UUID primary key not null default gen_random_uuid(),
    username text,
    first_name text,
    last_name text
);

CREATE TABLE IF NOT EXISTS units (
    id UUID primary key not null default gen_random_uuid(),
    name text,
    weight_in_grams numeric
);

CREATE TYPE meal AS ENUM ('BREAKFAST', 'LUNCH', 'DINNER', 'SNACKS');

CREATE TABLE IF NOT EXISTS diary_entries (
    id UUID primary key not null default gen_random_uuid(),
    name text,
    brand text,
    entry_date date,
    user_id UUID,
    unit_id UUID,
    serving_quantity numeric,
    meal meal,
    calories smallint,
    fat numeric,
    fat_saturated numeric,
    fat_trans numeric,
    fat_polyunsaturated numeric,
    fat_monounsaturated numeric,
    cholesterol smallint,
    carbohydrates numeric,
    fiber numeric,
    sugar numeric,
    protein numeric,
    sodium smallint,
    potassium smallint,
    calcium smallint,
    iron smallint,
    vitamin_a smallint,
    vitamin_c smallint,
    vitamin_d smallint
);
