## Build

    $ ./gradlew shadowJar
    
The uber-jar ends-up in the `build/libs` directory

## Run locally
    
    $ java -jar -Ddocker.io.url=unix:///var/run/docker.sock docker-events.jar
    
## Run as docker container
    
    $ cd docker && docker build -t "geoand/docker-events" . && docker-compose up -d