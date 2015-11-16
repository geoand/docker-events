package geoand.docker.events.model

import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent
import geoand.docker.events.model.container.DockerContainerEventStart
import org.reflections.Reflections
import spock.lang.Shared
import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

/**
 * Created by gandrianakis on 16/11/2015.
 */
class BaseDockerContainerEventSubclassesSpec extends Specification {

    @Shared
    Reflections reflections = new Reflections("geoand.docker.events.model.container")

    def "all subclasses have getEventName and getEventNameStatic"() {
        when:
            final Set<Class<? extends AbstractBaseDockerContainerEvent>> subTypes = reflections.getSubTypesOf(AbstractBaseDockerContainerEvent)

        then:
            subTypes.each { subType ->
                assertThat(subType.methods*.name).contains("getEventName")
            }
    }

    def "DockerContainerEventStart has correct getEventName implementation"() {
        expect:
            new DockerContainerEventStart().eventName == "Start"
    }
}
