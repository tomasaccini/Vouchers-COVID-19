# Vouchers-COVID-19 Server

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

### Troubleshoting

* If having an error when running `grails dbm-update`, try changing the version of the mysql connector from `server/build.gradle` from "    runtime 'mysql:mysql-connector-java:5.1.29'
" to your version (`mysql --version`) like "runtime 'mysql:mysql-connector-java:8.0.11'".

* If having troubles running the application with IntelliJ, delete the `.idea` directory and open the project from the `Vouchers` directory.

Documentation for this plugin: https://grails-plugins.github.io/grails-database-migration/3.0.x/index.html#introduction

# TMP  for development