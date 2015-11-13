## Build

    $ ./gradlew shadowJar
    
The uber-jar ends-up in the `build/libs` directory

## Run
    
    $ java -jar -Ddocker.io.url=unix:///var/run/docker.sock docker-events.jar