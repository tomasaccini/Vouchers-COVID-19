# Vouchers-COVID-19

## Database Setup

This project requires MySQL installed.

To setup your local database for development, login as root into mysql and follow these steps:

1. `CREATE DATABASE vouchers_dev;`
2. `CREATE USER 'vouchers'@'localhost' IDENTIFIED by 'dev';`
3. `GRANT ALL PRIVILEGES ON vouchers_dev . * TO 'vouchers'@'localhost';`
4. `FLUSH  PRIVILEGES;`
