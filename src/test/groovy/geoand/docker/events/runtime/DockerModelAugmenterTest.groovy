package geoand.docker.events.runtime

import com.github.dockerjava.api.model.Container
import spock.lang.Specification

/**
 * Created by gandrianakis on 13/11/2015.
 */
class DockerModelAugmenterTest extends Specification {

    def "firstName added to Container and returns the sanitized value of the first name of the container"(String[] names, String expectedFirstName) {
        given:
            final Container container = new Container(names: names)

        when:
            DockerModelAugmenter.augment()

        then:
            container.firstName == expectedFirstName

        where:
            names | expectedFirstName
            null | '(name missing)'
            []  | '(name missing)'
            ['/first'] | 'first'
            ['/first', 'second'] | 'first'
    }
}
