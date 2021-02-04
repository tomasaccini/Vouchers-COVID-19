FROM openjdk:11

ENV GRAILS_VERSION 4.0.5

# Initial setup
# Download and install Grails, setup environment variables
WORKDIR /usr/lib/jvm
RUN wget https://github.com/grails/grails-core/releases/download/v$GRAILS_VERSION/grails-$GRAILS_VERSION.zip && \
    unzip grails-$GRAILS_VERSION.zip && \
    rm -rf grails-$GRAILS_VERSION.zip && \
    ln -s grails-$GRAILS_VERSION grails

ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH
ENV GRADLE_USER_HOME /app/.gradle

# Compile the app, we use stats so it triggers the download of all the dependencies
WORKDIR /
COPY . /app
WORKDIR /app
RUN ["grails", "stats"]

# Run the app
EXPOSE 8080
CMD ["grails", "run-app"]
