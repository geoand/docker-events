package geoand.docker.events.di

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.Container
import com.github.dockerjava.api.model.Event
import com.google.inject.Binder
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import com.google.inject.util.Modules
import geoand.docker.events.callback.EventResultCallbackTemplate
import geoand.docker.events.container.ContainerFinder
import geoand.docker.events.listener.StartEventListener
import geoand.docker.events.listener.StopEventListener
import geoand.docker.events.model.container.DockerContainerEventStart
import spock.lang.Specification

/**
 * Created by gandrianakis on 16/11/2015.
 */
class EventBusSpec extends Specification {

    static final String CONTAINER_ID = "id"
    static final String CONTAINER_NAME = "name"

    final DockerClient dockerClient = Mock(DockerClient)
    final ContainerFinder containerFinder = Mock(ContainerFinder)
    final StartEventListener startEventListener = Mock(StartEventListener)
    final StopEventListener stopEventListener = Mock(StopEventListener)

    final Event event = Mock(Event)
    final Container container = Mock(Container)

    EventResultCallbackTemplate eventResultCallbackTemplate

    void setup() {
        final Injector injector = Guice.createInjector(Modules.override(new InjectionModule()).with(new Module() {
            @Override
            void configure(Binder binder) {
                binder.bind(ContainerFinder).toInstance(containerFinder)
                binder.bind(StartEventListener).toInstance(startEventListener)
                binder.bind(StopEventListener).toInstance(stopEventListener)
            }
        }))

        eventResultCallbackTemplate = injector.getInstance(EventResultCallbackTemplate)
    }

    def "start event emitted"() {
        given:
            event.id >> CONTAINER_ID
            event.status >> "start"

        and:
            container.names >> [CONTAINER_NAME]
            containerFinder.find(CONTAINER_ID) >> container

        when:
            eventResultCallbackTemplate.onNext(event)

        then:
            1 * startEventListener.handleEvent(_) >> { arguments ->
                final DockerContainerEventStart eventStart = arguments[0]
                assert eventStart.containerFirstName == CONTAINER_NAME
            }

    }

    def "stop event emitted"() {
        given:
            event.id >> CONTAINER_ID
            event.status >> "stop"

        and:
            container.names >> [CONTAINER_NAME]
            containerFinder.find(CONTAINER_ID) >> container

        when:
            eventResultCallbackTemplate.onNext(event)

        then:
            1 * stopEventListener.handleEvent(_) >> { arguments ->
                final DockerContainerEventStart eventStop = arguments[0]
                assert eventStop.containerFirstName == CONTAINER_NAME
            }

    }
}
