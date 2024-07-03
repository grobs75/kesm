USE [KESM]
GO

SET IDENTITY_INSERT person ON
GO
INSERT INTO person
  (id, first_name, last_name, id_card, tax_number, birth_place, birth_date)
VALUES
  (1, 'Petra', 'Petrovich', '131517AS', '1657329823', 'Szekszárd', '1980-10-30'),
  (2, 'Edit', 'Horváth', '341767SE', '8794538975', 'Budapest', '1990-01-12'),
  (3, 'Andor', 'Asztalos', '871992CD', '7684328976', 'Martonvásár', '1970-07-10'),
  (4, 'Károly', 'Németh', '325622SA', '8795490813', 'Siófok', '1980-11-21'),
  (5, 'Dénes', 'Török', '546718RE', '8795293816', 'Szeged', '1990-05-07'),
  (6, 'Csilla', 'Vörös', '654312RT', '2755450823', 'Tatabánya', '1960-12-01')
GO
SET IDENTITY_INSERT person OFF
GO

SET IDENTITY_INSERT address ON
GO
INSERT INTO address
  (id, city, postal_code, street, temporary, person_id)
VALUES
  (1, 'Pécs', '7600', 'Petőfi utca 32.', NULL, 1),
  (2, 'Szekszárd', '7100', 'Kossuth utca 4.', 1, 1),
  (3, 'Érd', '2036', 'Budai út 32.', NULL, 2),
  (4, 'Budapest', '1037', 'Erdőalja út 2.', NULL, 3),
  (5, 'Siófok', '8600', 'Petőfi sétány 7.', NULL, 4),
  (6, 'Paks', '7030', 'Gagarin utca 4.', 1, 4),
  (7, 'Szeged', '6700', 'Temesvári körút 15.', NULL, 5),
  (8, 'Hódmezővásárhely', '6800', 'Ady Endre utca 41.', 1, 5),
  (9, 'Komárom', '2900', 'Táncsics Mihály utca 27.', NULL, 6),
  (10, 'Tata', '2890', 'Rákoczi utca 7.', 1, 6)
SET IDENTITY_INSERT address ON
GO

SET IDENTITY_INSERT contact ON
GO
INSERT INTO contact
  (id, contact, address_id, contact_type_id)
VALUES
  (1, 'ppetra@gmail.com', 1, 1),
  (2, 'ppetra21@outlook.com', 1, 1),
  (3, '+36 30 432 987 7766', 1, 2),
  (4, 'ppetra@gmail.com', 2, 1),
  (5, '+36 30 432 987 7766', 2, 2),
  (6, 'voroscs33@gmail.com', 6, 1)
SET IDENTITY_INSERT contact OFF
GO
