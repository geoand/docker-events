package geoand.docker.events.listener

import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent

/**
 * Created by gandrianakis on 16/11/2015.
 *
 * Every class that wants to handle some Docker event needs to extend this class and have
 * @{link handleEvent} annotated with {@link com.google.common.eventbus.Subscribe}
 */
interface DockerEventListener<T extends AbstractBaseDockerContainerEvent> {

    void handleEvent(T event)

}