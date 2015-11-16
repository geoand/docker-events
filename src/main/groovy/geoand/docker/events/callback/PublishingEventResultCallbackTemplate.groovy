package geoand.docker.events.callback

import com.github.dockerjava.api.model.Event
import com.google.common.eventbus.EventBus
import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent
import geoand.docker.events.factory.ContainerEventFromDockerApiEventFactory
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

/**
 * Created by gandrianakis on 13/11/2015.
 *
 * This class reads is notified with the raw docker-api events
 * and subsequently pushes the corresponding subclass of {@link AbstractBaseDockerContainerEvent}
 * onto the event-bus
 * These events are later handled by their corresponding event listeners
 */

@CompileStatic
@Slf4j
class PublishingEventResultCallbackTemplate extends EventResultCallbackTemplate {

    final ContainerEventFromDockerApiEventFactory eventFactory
    final EventBus eventBus

    @Inject
    PublishingEventResultCallbackTemplate(ContainerEventFromDockerApiEventFactory eventFactory, EventBus eventBus) {
        this.eventFactory = eventFactory
        this.eventBus = eventBus
    }

    @Override
    void onNext(Event dockerApiEvent) {
        final AbstractBaseDockerContainerEvent internalEvent = eventFactory.createContainerEvent(dockerApiEvent)
        if (internalEvent) {
            eventBus.post(internalEvent)
        }
    }

}
