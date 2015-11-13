package geoand.docker.events.events

import com.github.dockerjava.api.model.Container
import com.github.dockerjava.api.model.Event
import geoand.docker.events.container.ContainerFinder
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.transform.Memoized
import groovy.util.logging.Slf4j

import javax.inject.Inject

/**
 * Created by gandrianakis on 13/11/2015.
 */

@CompileStatic
@Slf4j
class SimpleEventResultCallbackTemplate extends EventResultCallbackTemplate {

    final ContainerFinder containerRetriever

    enum TrackedEvent {
        START("started"),
        STOP("stopped")

        final String verb

        TrackedEvent(String verb) {
            this.verb = verb
        }

        static TrackedEvent findMatching(String eventName) {
            return values().find {trackedEvent -> trackedEvent.name().equalsIgnoreCase(eventName)}
        }
    }

    @Inject
    SimpleEventResultCallbackTemplate(ContainerFinder containerRetriever) {
        this.containerRetriever = containerRetriever
    }

    @Override
    void onNext(Event event) {
        final TrackedEvent trackedEvent = TrackedEvent.findMatching(event.status)
        if(!trackedEvent) {
            return
        }

        final Container correspondingContainer = getCorrespondingContainer(event)
        final String name = getContainerName(correspondingContainer)

        log.info("Container ${name} ${trackedEvent.verb}")
    }

    @CompileDynamic
    private String getContainerName(Container correspondingContainer) {
        return correspondingContainer?.firstName
    }

    private Container getCorrespondingContainer(Event event) {
        final String containerID = event.id
        return getContainerFromID(containerID)
    }

    @Memoized
    private Container getContainerFromID(String containerID) {
        containerRetriever.find(containerID)
    }
}
