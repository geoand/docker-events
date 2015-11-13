package geoand.docker.events

import com.github.dockerjava.api.DockerClient
import com.google.inject.Guice
import com.google.inject.Injector
import geoand.docker.events.di.InjectionModule
import geoand.docker.events.events.EventResultCallbackTemplate
import geoand.docker.events.runtime.DockerModelAugmenter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * Created by gandrianakis on 13/11/2015.
 */

@CompileStatic
@Slf4j
class Main {

    static void main(String[] args) {
        DockerModelAugmenter.augment() //monkey-patch the model specified by docker-java with some useful methods

        final Injector injector = Guice.createInjector(new InjectionModule())

        final DockerClient dockerClient = injector.getInstance(DockerClient)
        final EventResultCallbackTemplate eventsResultHandler = injector.getInstance(EventResultCallbackTemplate)

        dockerClient.eventsCmd().exec(eventsResultHandler) //setup event handling

        log.info("Listening for docker events");
    }
}
