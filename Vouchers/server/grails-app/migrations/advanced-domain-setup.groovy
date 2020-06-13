databaseChangeLog = {

    changeSet(author: "taccini (generated)", id: "1592086810546-2") {
        createTable(tableName: "address") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "addressPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "province", type: "VARCHAR(255)")

            column(name: "number", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "street", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "country_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "apartment", type: "VARCHAR(255)")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-3") {
        createTable(tableName: "complaint") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "complaintPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "voucher_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "motive", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-4") {
        createTable(tableName: "counterfoil") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "counterfoilPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "stock", type: "INT") {
                constraints(nullable: "false")
            }

            column(name: "voucher_information_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "business_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-5") {
        createTable(tableName: "country") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "countryPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-6") {
        createTable(tableName: "item") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "itemPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "product_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "quantity", type: "INT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-7") {
        createTable(tableName: "product") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "productPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)")

            column(name: "business_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-8") {
        createTable(tableName: "voucher_information") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "voucher_informationPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "valid_from", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "price", type: "DOUBLE") {
                constraints(nullable: "false")
            }

            column(name: "valid_until", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-9") {
        createTable(tableName: "voucher_information_item") {
            column(name: "voucher_information_items_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "item_id", type: "BIGINT")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-10") {
        createTable(tableName: "voucher_item") {
            column(name: "voucher_items_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "item_id", type: "BIGINT")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-11") {
        addColumn(tableName: "user") {
            column(name: "address_id", type: "bigint")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-12") {
        addColumn(tableName: "user") {
            column(name: "category", type: "varchar(255)")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-13") {
        addColumn(tableName: "user") {
            column(name: "class", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-14") {
        addColumn(tableName: "voucher") {
            column(name: "client_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-15") {
        addColumn(tableName: "voucher") {
            column(name: "counterfoil_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-16") {
        addColumn(tableName: "voucher") {
            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-17") {
        addColumn(tableName: "user") {
            column(name: "full_name", type: "varchar(255)")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-18") {
        addColumn(tableName: "user") {
            column(name: "name", type: "varchar(255)")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-19") {
        addColumn(tableName: "voucher") {
            column(name: "voucher_information_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-20") {
        addColumn(tableName: "user") {
            column(name: "website", type: "varchar(255)")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-21") {
        addUniqueConstraint(columnNames: "name", constraintName: "UC_COUNTRYNAME_COL", tableName: "country")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-22") {
        addForeignKeyConstraint(baseColumnNames: "business_id", baseTableName: "counterfoil", constraintName: "FK15keilrk8vjqyr2rx2t7laltj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-23") {
        addForeignKeyConstraint(baseColumnNames: "voucher_information_items_id", baseTableName: "voucher_information_item", constraintName: "FK425ckh426kj9fr1p7vx22mjuv", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "voucher_information", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-24") {
        addForeignKeyConstraint(baseColumnNames: "client_id", baseTableName: "voucher", constraintName: "FK4ml3ei33n3xe8v7pqqkrg0w3m", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-25") {
        addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "voucher_information_item", constraintName: "FK5txbbya68fbixfqbm42cd4ofo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-26") {
        addForeignKeyConstraint(baseColumnNames: "voucher_information_id", baseTableName: "counterfoil", constraintName: "FK708nwto4dc39vqtluvln75gew", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "voucher_information", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-27") {
        addForeignKeyConstraint(baseColumnNames: "product_id", baseTableName: "item", constraintName: "FKd1g72rrhgq1sf7m4uwfvuhlhe", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "product", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-28") {
        addForeignKeyConstraint(baseColumnNames: "address_id", baseTableName: "user", constraintName: "FKddefmvbrws3hvl5t0hnnsv8ox", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "address", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-29") {
        addForeignKeyConstraint(baseColumnNames: "country_id", baseTableName: "address", constraintName: "FKe54x81nmccsk5569hsjg1a6ka", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "country", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-30") {
        addForeignKeyConstraint(baseColumnNames: "voucher_items_id", baseTableName: "voucher_item", constraintName: "FKfc71aj4p068la8g1t75kxp6rl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "voucher", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-31") {
        addForeignKeyConstraint(baseColumnNames: "counterfoil_id", baseTableName: "voucher", constraintName: "FKkkrarq4era0is0xtb0dcgx54s", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "counterfoil", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-32") {
        addForeignKeyConstraint(baseColumnNames: "voucher_information_id", baseTableName: "voucher", constraintName: "FKpp49cxccvku30gfsbp3vuonq1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "voucher_information", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-33") {
        addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "voucher_item", constraintName: "FKqumvfd1dcwafhpwwc1emo7p6d", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-34") {
        addForeignKeyConstraint(baseColumnNames: "voucher_id", baseTableName: "complaint", constraintName: "FKr4f717pv2w2yhlrh3b1h9s6gc", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "voucher", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-35") {
        addForeignKeyConstraint(baseColumnNames: "business_id", baseTableName: "product", constraintName: "FKsyqqf7twkitde1ifykttxvcjm", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-36") {
        dropTable(tableName: "business")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-37") {
        dropTable(tableName: "purchase")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-38") {
        dropColumn(columnName: "active", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-39") {
        dropColumn(columnName: "business_id", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-40") {
        dropColumn(columnName: "category", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-41") {
        dropColumn(columnName: "description", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-42") {
        dropColumn(columnName: "finish_retire_date", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-43") {
        dropColumn(columnName: "finish_sell_date", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-44") {
        dropColumn(columnName: "firebase_id", tableName: "user")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-45") {
        dropColumn(columnName: "fullname", tableName: "user")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-46") {
        dropColumn(columnName: "max_sells", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-47") {
        dropColumn(columnName: "published", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-48") {
        dropColumn(columnName: "start_retire_date", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-49") {
        dropColumn(columnName: "start_sell_date", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-50") {
        dropColumn(columnName: "username", tableName: "user")
    }

    changeSet(author: "taccini (generated)", id: "1592086810546-1") {
        dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "phone_number", tableName: "user")
    }
}
