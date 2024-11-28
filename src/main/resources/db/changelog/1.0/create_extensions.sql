--liquibase formatted sql

-- changeset shinkarev:enable_pgcrypto
-- preconditions:
--   - not:
--       - extensionExists:
--           extensionName: "pgcrypto"
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
