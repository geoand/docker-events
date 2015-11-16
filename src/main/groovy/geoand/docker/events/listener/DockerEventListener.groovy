package geoand.docker.events.listener

import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent

/**
 * Created by gandrianakis on 16/11/2015.
 */
interface DockerEventListener<T extends AbstractBaseDockerContainerEvent> {

    void handleEvent(T event)

}