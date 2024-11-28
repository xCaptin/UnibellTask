--liquibase formatted sql

-- changeset shinkarev:create_emails_table
CREATE TABLE IF NOT EXISTS client_service.emails
(
    email_id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id     UUID         NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    CONSTRAINT fk_emails_client FOREIGN KEY (client_id) REFERENCES client_service.clients (client_id) ON DELETE CASCADE,
    CONSTRAINT uq_email_address UNIQUE (email_address)
);

-- changeset shinkarev:create_emails_index
CREATE INDEX IF NOT EXISTS idx_emails_client_id ON client_service.emails (client_id);
