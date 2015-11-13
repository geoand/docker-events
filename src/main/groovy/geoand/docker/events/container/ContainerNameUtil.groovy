package geoand.docker.events.container

import groovy.transform.CompileStatic

/**
 * Created by gandrianakis on 13/11/2015.
 */
@CompileStatic
abstract class ContainerNameUtil {

    static String sanitize(String name) {
        return name?.replace('/', '')
    }
}
