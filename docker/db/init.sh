#!/bin/bash
set -e

# Start Oracle XE in the background
/opt/oracle/startDB.sh &

# Function to test if database is ready
function test_connection() {
    sqlplus -S system/TicketPass123@//localhost:1521/$ORACLE_DATABASE <<EOF
    exit;
EOF
    return $?
}

# Wait for database to be ready
echo "Waiting for Oracle Database to be ready..."
for i in {1..50}; do
    if test_connection; then
        echo "Database is ready!"
        break
    fi
    echo "Attempt $i: Database not ready yet..."
    sleep 10
done

if ! test_connection; then
    echo "Database failed to start within the allocated time."
    exit 1
fi

# Run the schema initialization
echo "Running schema initialization..."
sqlplus system/TicketPass123@//localhost:1521/$ORACLE_DATABASE @/container-entrypoint-initdb.d/schema.sql

# Keep container running
tail -f /opt/oracle/diag/rdbms/*/$ORACLE_SID/trace/alert_$ORACLE_SID.log