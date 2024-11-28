--liquibase formatted sql

-- changeset shinkarev:create_phones_table
CREATE TABLE IF NOT EXISTS client_service.phones
(
    phone_id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id    UUID        NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    CONSTRAINT fk_phones_client FOREIGN KEY (client_id) REFERENCES client_service.clients (client_id) ON DELETE CASCADE,
    CONSTRAINT uq_phone_number UNIQUE (phone_number)
);

-- changeset shinkarev:create_phones_index
CREATE INDEX IF NOT EXISTS idx_phones_client_id ON client_service.phones (client_id);
