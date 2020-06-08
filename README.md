# Vouchers-COVID-19

## Database Setup

This project requires MySQL installed.

To setup your local database for development, login as root into mysql and follow these steps:

1. `CREATE DATABASE vouchers_dev;`
2. `CREATE USER 'vouchers'@'localhost' IDENTIFIED by 'dev';`
3. `GRANT ALL PRIVILEGES ON vouchers_dev . * TO 'vouchers'@'localhost';`
4. `FLUSH  PRIVILEGES;`

The typical workflow is:

1. Make sure your current working directory is `server`.
2. Create or update some Domain class.
3. Run `grails dbm-gorm-diff diff.groovy`
4. Rename `diff.groovy` accordingly.
5. Update `changelog.groovy` to reference the new file. 
6. After reviewing the changes, run `grails dbm-update` to apply new migrations.

Documentation for this plugin: https://grails-plugins.github.io/grails-database-migration/3.0.x/index.html#introduction
