package geoand.docker.events.factory

import com.github.dockerjava.api.model.Container
import com.github.dockerjava.api.model.Event
import geoand.docker.events.container.ContainerFinder
import geoand.docker.events.model.api.ContainerNameAwareEvent
import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.Memoized
import groovy.util.logging.Slf4j

import javax.inject.Inject

/**
 * Created by gandrianakis on 16/11/2015.
 */
@CompileStatic
@Slf4j
class SimpleDockerEventFactory implements ContainerEventFromDockerApiEventFactory {

    final ContainerFinder containerRetriever
    final ContainerNameAwareEventFromDockerApiEventFactory nameAwareFactory

    @Inject
    SimpleDockerEventFactory(ContainerFinder containerRetriever, ContainerNameAwareEventFromDockerApiEventFactory nameAwareFactory) {
        this.containerRetriever = containerRetriever
        this.nameAwareFactory = nameAwareFactory
    }

    /**
     * Looks up the class of the event from the corresponding name, and if found invokes the tuple constructor
     */
    @Override
    AbstractBaseDockerContainerEvent createContainerEvent(Event dockerApiEvent) {
        final Container correspondingContainer = getCorrespondingContainer(dockerApiEvent)
        final String name = getContainerName(correspondingContainer)

        return nameAwareFactory.createContainerEvent(new ContainerNameAwareEvent(name: name, event: dockerApiEvent))
    }

    private Container getCorrespondingContainer(Event event) {
        final String containerID = event.id
        return getContainerFromID(containerID)
    }

    @Memoized
    private Container getContainerFromID(String containerID) {
        try {
            return containerRetriever.find(containerID)
        } catch (Exception e) {
            log.error("An unexpected exception occurred while looking up container with ID = $containerID", e)
            return null
        }
    }

    /**
     * Needs to be @CompileDynamic because it uses a method that is monkey patched at runtime
     */
    @CompileDynamic
    private String getContainerName(Container correspondingContainer) {
        return correspondingContainer?.firstName
    }
}


