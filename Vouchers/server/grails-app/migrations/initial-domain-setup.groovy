databaseChangeLog = {

    changeSet(author: "ndv (generated)", id: "1591591236204-1") {
        createTable(tableName: "business") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "businessPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "social_network", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "address", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "category", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "phone_number", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "website", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ndv (generated)", id: "1591591236204-2") {
        createTable(tableName: "purchase") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "purchasePK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "pickup_timestamp", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "business_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "purchase_timestamp", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ndv (generated)", id: "1591591236204-3") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "fullname", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "verified_account", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "phone_number", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "firebase_id", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ndv (generated)", id: "1591591236204-4") {
        createTable(tableName: "voucher") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "voucherPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "business_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "finish_retire_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "max_sells", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "start_sell_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "published", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "finish_sell_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "start_retire_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "active", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "category", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
