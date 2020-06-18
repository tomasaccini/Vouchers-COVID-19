package iteration1

databaseChangeLog = {

    changeSet(author: "taccini (generated)", id: "1592491567279-4") {
        createTable(tableName: "client_counterfoil_interaction") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "client_counterfoil_interactionPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "counterfoil_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "client_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "interaction", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-5") {
        createTable(tableName: "complaint_complaint_message") {
            column(name: "complaint_messages_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "complaint_message_id", type: "BIGINT")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-6") {
        createTable(tableName: "complaint_message") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "complaint_messagePK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "message", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "owner_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-7") {
        createTable(tableName: "tracking") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "trackingPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "voucher_information_id", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "client_id", type: "BIGINT") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-8") {
        addColumn(tableName: "counterfoil") {
            column(name: "active", type: "bit") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-9") {
        addColumn(tableName: "counterfoil") {
            column(name: "amount_sold", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-10") {
        addColumn(tableName: "complaint") {
            column(name: "business_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-11") {
        addColumn(tableName: "complaint") {
            column(name: "client_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-12") {
        addColumn(tableName: "voucher") {
            column(name: "complaint_id", type: "bigint")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-13") {
        addColumn(tableName: "complaint") {
            column(name: "description", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-14") {
        addColumn(tableName: "voucher") {
            column(name: "last_state_change", type: "datetime")
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-15") {
        addColumn(tableName: "complaint") {
            column(name: "state", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-16") {
        addColumn(tableName: "voucher") {
            column(name: "state", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-17") {
        addColumn(tableName: "product") {
            column(name: "type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-18") {
        addForeignKeyConstraint(baseColumnNames: "client_id", baseTableName: "complaint", constraintName: "FK2etl8j6nsb0omb85te57mucvl", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-19") {
        addForeignKeyConstraint(baseColumnNames: "business_id", baseTableName: "complaint", constraintName: "FK3flg4aagswj9mpx48txxkinfq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-20") {
        addForeignKeyConstraint(baseColumnNames: "complaint_id", baseTableName: "voucher", constraintName: "FK3rf9200ys43jh9m3xtcdqbjea", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "complaint", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-21") {
        addForeignKeyConstraint(baseColumnNames: "counterfoil_id", baseTableName: "client_counterfoil_interaction", constraintName: "FKb7ko5kxquau66wvbx3upswo", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "counterfoil", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-22") {
        addForeignKeyConstraint(baseColumnNames: "voucher_information_id", baseTableName: "tracking", constraintName: "FKdqxk0hfrntuwddw4bt2qol774", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "voucher_information", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-23") {
        addForeignKeyConstraint(baseColumnNames: "complaint_message_id", baseTableName: "complaint_complaint_message", constraintName: "FKg1bmpvga9x3goipb9bc4nt877", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "complaint_message", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-24") {
        addForeignKeyConstraint(baseColumnNames: "owner_id", baseTableName: "complaint_message", constraintName: "FKkh1hhek9egou9h132dmaei050", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-25") {
        addForeignKeyConstraint(baseColumnNames: "client_id", baseTableName: "tracking", constraintName: "FKpaim5eiox70xidkbdn4cmv423", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-26") {
        addForeignKeyConstraint(baseColumnNames: "client_id", baseTableName: "client_counterfoil_interaction", constraintName: "FKpwuux59f1joewdhx6okr39v83", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-27") {
        addForeignKeyConstraint(baseColumnNames: "complaint_messages_id", baseTableName: "complaint_complaint_message", constraintName: "FKtkc52ows18kymppo6eakdllqd", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "complaint", validate: "true")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-28") {
        dropForeignKeyConstraint(baseTableName: "voucher_item", constraintName: "FKfc71aj4p068la8g1t75kxp6rl")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-29") {
        dropForeignKeyConstraint(baseTableName: "voucher_item", constraintName: "FKqumvfd1dcwafhpwwc1emo7p6d")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-30") {
        dropForeignKeyConstraint(baseTableName: "complaint", constraintName: "FKr4f717pv2w2yhlrh3b1h9s6gc")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-31") {
        dropTable(tableName: "voucher_item")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-32") {
        dropColumn(columnName: "motive", tableName: "complaint")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-33") {
        dropColumn(columnName: "voucher_id", tableName: "complaint")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-1") {
        dropNotNullConstraint(columnDataType: "bigint", columnName: "client_id", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-2") {
        dropNotNullConstraint(columnDataType: "bigint", columnName: "counterfoil_id", tableName: "voucher")
    }

    changeSet(author: "taccini (generated)", id: "1592491567279-3") {
        dropUniqueConstraint(constraintName: "UC_COUNTRYNAME_COL", tableName: "country")

        addUniqueConstraint(columnNames: "name", constraintName: "UC_COUNTRYNAME_COL", tableName: "country")
    }
}
