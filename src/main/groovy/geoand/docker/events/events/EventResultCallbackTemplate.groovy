package geoand.docker.events.events

import com.github.dockerjava.api.model.Event
import com.github.dockerjava.core.async.ResultCallbackTemplate
import com.github.dockerjava.core.command.EventsResultCallback
import groovy.transform.CompileStatic

/**
 * Created by gandrianakis on 13/11/2015.
 */
@CompileStatic
abstract class EventResultCallbackTemplate extends ResultCallbackTemplate<EventsResultCallback, Event> {
}
