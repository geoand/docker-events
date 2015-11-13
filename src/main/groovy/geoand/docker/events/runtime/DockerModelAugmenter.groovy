package geoand.docker.events.runtime

import com.github.dockerjava.api.model.Container
import geoand.docker.events.container.ContainerNameUtil

/**
 * Created by gandrianakis on 13/11/2015.
 */
abstract class DockerModelAugmenter {

    static void augment() {
        addFirstNameMethodToContainerModel()
    }

    private static void addFirstNameMethodToContainerModel() {
        Container.metaClass.getFirstName = {
            final String name = delegate.names?.find { true } ?: "(name missing)"
            return ContainerNameUtil.sanitize(name)
        }
    }
}
