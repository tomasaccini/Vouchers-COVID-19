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
	./gradlew server:bootRun &	
	cd client && pm2 start npm -- start	
	sleep 20
	cd client && npm test
