run:
	./gradlew server:bootRun

run-all:
	./gradlew server:bootRun &
	sleep 10
	cd client && npm start

clean:
	./gradlew clean

update-db:
	grails dbm-update

run-test:
	cd server && grails test-app
	cd client && npm test
