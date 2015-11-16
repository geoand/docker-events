package geoand.docker.events

import com.github.dockerjava.api.DockerClient
import com.google.inject.Guice
import com.google.inject.Injector
import geoand.docker.events.callback.EventResultCallbackTemplate
import geoand.docker.events.di.InjectionModule
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * Created by gandrianakis on 13/11/2015.
 */

@CompileStatic
@Slf4j
class Main {

    static void main(String[] args) {
        final Injector injector = Guice.createInjector(new InjectionModule()) //use Guice to wire-up all the classes that will be used

        final DockerClient dockerClient = injector.getInstance(DockerClient)
        final EventResultCallbackTemplate eventsResultHandler = injector.getInstance(EventResultCallbackTemplate)

        dockerClient.eventsCmd().exec(eventsResultHandler) //setup callback to for Docker Events

        log.info("Listening for docker events");
    }
}
