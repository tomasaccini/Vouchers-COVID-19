package iteration1

databaseChangeLog = {
    include file: 'iteration1/DELTA_001_initial-domain-setup.groovy'
    include file: 'iteration1/DELTA_002_advanced-domain-setup.groovy'
}
