package geoand.docker.events.model.api

import com.github.dockerjava.api.model.Event
import groovy.transform.CompileStatic

/**
 * Created by gandrianakis on 16/11/2015.
 */
@CompileStatic
class ContainerNameAwareEvent {

    @Delegate
    Event event

    String name
}
