package geoand.docker.events.callback

import com.github.dockerjava.api.model.Event
import com.google.common.eventbus.EventBus
import geoand.docker.events.factory.ContainerEventFromDockerApiEventFactory
import geoand.docker.events.model.container.DockerContainerEventStart
import spock.lang.Specification

/**
 * Created by gandrianakis on 16/11/2015.
 */
class PublishingEventResultCallbackTemplateSpec extends Specification {

    static final String CONTAINER_ID = "cid"

    final ContainerEventFromDockerApiEventFactory factory = Mock(ContainerEventFromDockerApiEventFactory)
    final EventBus eventBus = Mock(EventBus)

    final Event dockerApiEvent = Mock(Event)

    final PublishingEventResultCallbackTemplate callback = new PublishingEventResultCallbackTemplate(factory, eventBus)

    void setup() {
        dockerApiEvent.id >> CONTAINER_ID
    }

    def "factory returns null"() {
        given:
            factory.createContainerEvent(dockerApiEvent) >> null

        when:
            callback.onNext(dockerApiEvent)

        then:
            0 * eventBus.post(_)
    }

    def "factory returns valid AbstractBaseDockerContainerEvent"() {
        given:
            final DockerContainerEventStart containerEventStart = new DockerContainerEventStart()
            factory.createContainerEvent(dockerApiEvent) >> containerEventStart

        when:
            callback.onNext(dockerApiEvent)

        then:
            1 * eventBus.post(containerEventStart)
    }
}
