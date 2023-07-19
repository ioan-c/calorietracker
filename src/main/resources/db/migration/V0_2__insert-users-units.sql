DELETE FROM users where id in ('86acb379-eab9-42c5-91f7-33b9c193756d', '08b63360-b996-4a01-91c8-74b044850c30');

INSERT INTO users(id, username, first_name, last_name)
VALUES ('86acb379-eab9-42c5-91f7-33b9c193756d', 'marshmy', 'Mar', 'Shmy'),
       ('08b63360-b996-4a01-91c8-74b044850c30', 'jelly', 'Je', 'Lly');

DELETE FROM units where id = '914743b6-b1f1-4630-a373-113810939541';

INSERT INTO units(id, name, weight_in_grams)
VALUES ('914743b6-b1f1-4630-a373-113810939541', 'gram', 1);
