package geoand.docker.events.listener

import com.google.common.eventbus.Subscribe
import geoand.docker.events.model.container.DockerContainerEventStop
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * Created by gandrianakis on 16/11/2015.
 */
@CompileStatic
@Slf4j
class StopEventListener implements DockerEventListener<DockerContainerEventStop> {

    @Subscribe
    @Override
    void handleEvent(DockerContainerEventStop event) {
        log.info("${event.containerFirstName} stopped")
    }
}
