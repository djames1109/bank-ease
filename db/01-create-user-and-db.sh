#!/bin/sh
createdb bank_ease_db
createuser bank_ease_user
psql -d bank_ease_db --command "alter user bank_ease_user with encrypted password 'password'"
psql -d bank_ease_db --command "grant all privileges on database bank_ease_db to bank_ease_user;"

