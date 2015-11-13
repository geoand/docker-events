package geoand.docker.events.container

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.Container
import groovy.transform.CompileStatic

import javax.inject.Inject

/**
 * Created by gandrianakis on 13/11/2015.
 */
@CompileStatic
class SimpleContainerFinder implements ContainerFinder {

    final DockerClient dockerClient

    @Inject
    SimpleContainerFinder(DockerClient dockerClient) {
        this.dockerClient = dockerClient
    }

    @Override
    Container find(String containerID) {
        dockerClient.listContainersCmd().withShowAll(true).exec().find { container -> container.id == containerID}
    }
}
