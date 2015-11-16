package geoand.docker.events.factory

import geoand.docker.events.model.api.ContainerNameAwareEvent
import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent

/**
 * Created by gandrianakis on 16/11/2015.
 */
interface ContainerNameAwareEventFromDockerApiEventFactory {

    AbstractBaseDockerContainerEvent createContainerEvent(ContainerNameAwareEvent dockerApiEvent)
}
