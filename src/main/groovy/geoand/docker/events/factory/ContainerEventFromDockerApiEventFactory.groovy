package geoand.docker.events.factory

import com.github.dockerjava.api.model.Event
import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent

/**
 * Created by gandrianakis on 16/11/2015.
 */
interface ContainerEventFromDockerApiEventFactory {

    AbstractBaseDockerContainerEvent createContainerEvent(Event dockerApiEvent)
}