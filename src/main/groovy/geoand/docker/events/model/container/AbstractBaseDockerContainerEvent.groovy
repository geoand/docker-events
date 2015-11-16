package geoand.docker.events.model.container

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor

/**
 * Created by gandrianakis on 16/11/2015.
 *
 * Subclasses of this class will automatically be eligible for binding with the corresponding
 * event of the Docker-API
 *
 * In order for this to happen two conditions need to be met
 * 1) Each subclass has to follow the standard Java-beans conventions
 * 2) The subclasses need to be in the same package as this class
 * 3) Each subclass has to contain a Groovy Tuple constructor
 *    This requirement can easily be satisfied by annotating subclasses with {@link @TupleConstructor}
 *
 *  (For implementation details about the aforementioned automatic handling,
 *   check out {@link geoand.docker.events.factory.SimpleDockerEventFactory})
 *
 *
 * By default for each subclass of this class the status of the corresponding event emitted by the Docker-API
 * is the trailing part of the class name after 'Event'.
 * For example, for the class DockerContainerEventTest, the name of the docker event would be 'Test'
 *
 */
@TupleConstructor
@CompileStatic
abstract class AbstractBaseDockerContainerEvent implements NameableEvent {

    String containerID
    String containerFirstName

    @Override
    String getEventName() {
        final String classSimpleName = this.class.simpleName
        return classSimpleName.substring(classSimpleName.indexOf('Event') + 'Event'.size())
    }
}
