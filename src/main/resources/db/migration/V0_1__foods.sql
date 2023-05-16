CREATE TABLE foods (
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
    vitaminA smallint,
    vitaminC smallint,
    vitaminD smallint
)
