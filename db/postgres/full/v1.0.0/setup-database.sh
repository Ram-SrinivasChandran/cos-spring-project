#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE cafeteria_service_db;
    CREATE ROLE cafeteria_service_usr WITH LOGIN PASSWORD 'cafeteriaservice123';
    GRANT ALL PRIVILEGES ON DATABASE cafeteria_service_db TO cafeteria_service_usr;
    GRANT pg_read_server_files TO cafeteria_service_usr;
    ALTER DATABASE cafeteria_service_db OWNER TO cafeteria_service_usr;
EOSQL

psql -U cafeteria_service_usr cafeteria_service_db < /tmp/psql/cafeteria-svc/cafeteria.sql
