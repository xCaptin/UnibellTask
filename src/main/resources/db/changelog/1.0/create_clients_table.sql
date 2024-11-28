--liquibase formatted sql

-- changeset shinkarev:create_clients_table
CREATE TABLE IF NOT EXISTS client_service.clients
(
    client_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL
);