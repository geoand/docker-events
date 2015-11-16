package geoand.docker.events.model

import geoand.docker.events.factory.ClasspathScanningContainerNameAwareEventFromDockerApiEventFactory
import geoand.docker.events.model.api.ContainerNameAwareEvent
import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent
import geoand.docker.events.model.container.DockerContainerEventStart
import geoand.docker.events.model.container.DockerContainerEventStop
import spock.lang.Specification

/**
 * Created by gandrianakis on 16/11/2015.
 */
class ClasspathScanningContainerNameAwareEventFromDockerApiEventFactorySpec extends Specification {

    final String CONTAINER_ID = "id"
    final String CONTAINER_NAME = "name"

    final ContainerNameAwareEvent event = Mock(ContainerNameAwareEvent)

    final ClasspathScanningContainerNameAwareEventFromDockerApiEventFactory factory = new ClasspathScanningContainerNameAwareEventFromDockerApiEventFactory()

    def "start/stop event"(String eventStatus, Class expectedEventClass) {
        given:
            event.status >> eventStatus
            event.id >> CONTAINER_ID
            event.name >> CONTAINER_NAME

        when:
            final AbstractBaseDockerContainerEvent internalEvent = factory.createContainerEvent(event)

        then:
            internalEvent.class == expectedEventClass
            internalEvent.containerID == CONTAINER_ID
            internalEvent.containerFirstName == CONTAINER_NAME

        where:
            eventStatus | expectedEventClass
            "start" | DockerContainerEventStart.class
            "stop" | DockerContainerEventStop.class
    }

    def "missing corresponding event class"() {
        given:
            event.status >> "dummy"

        expect:
            factory.createContainerEvent(event) == null
    }
}
