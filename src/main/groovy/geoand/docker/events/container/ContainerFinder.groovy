package geoand.docker.events.container

import com.github.dockerjava.api.model.Container

/**
 * Created by gandrianakis on 13/11/2015.
 */
interface ContainerFinder {

    Container find(String containerID)
}
