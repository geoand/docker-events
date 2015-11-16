package geoand.docker.events.listener

import com.google.common.eventbus.Subscribe
import geoand.docker.events.model.container.DockerContainerEventStart
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

/**
 * Created by gandrianakis on 16/11/2015.
 */
@CompileStatic
@Slf4j
class StartEventListener implements DockerEventListener<DockerContainerEventStart> {

    @Subscribe
    @Override
    void handleEvent(DockerContainerEventStart event) {
        log.info("${event.containerFirstName} started")
    }
}
