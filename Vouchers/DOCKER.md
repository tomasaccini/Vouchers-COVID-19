# Docker reference


One of the goals of using Docker is to get rid of all the annoyance of setting up a new environment.

We decided to implement Docker as a way to avoid all the pains that come with setting up Java, nullify the differences between our choice of OS and to guarantee that every member of the team is developing in the same environment.

The production environment setup is still a WIP.

## Installation and requirements

Get your flavour of Docker in the [following link](https://docs.docker.com/get-docker/) depending on the platform you'll be running this application on.

If you're installing the application in Linux you'll need to install [docker-compose](https://docs.docker.com/compose/install/).

Working directory from now on will be `Vouchers/`

```bash
echo "NODE_PATH=src\nPORT=3030" >> client/.env

```

## How to run the application

Once the initial setup is done, run

* `docker-compose build` to create all the images. [Documentation](https://docs.docker.com/compose/reference/build/).
* `docker-compose up` to start all services. [Documentation](https://docs.docker.com/compose/reference/up/).
* `docker-compose down` to stop all services. [Documentation](https://docs.docker.com/compose/reference/down/).
* `docker-compose ps` to check the status of each container currently running in your environment. [Documentation](https://docs.docker.com/compose/reference/ps/).
