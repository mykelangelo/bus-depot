ALTER TABLE depot_user
  DROP COLUMN id,
  ADD PRIMARY KEY (email);