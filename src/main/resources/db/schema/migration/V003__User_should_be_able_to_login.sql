ALTER TABLE depot_user
    MODIFY COLUMN user_type
        ENUM ('BUS_DRIVER', 'DEPOT_ADMIN') NOT NULL
        AFTER email;