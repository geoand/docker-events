package geoand.docker.events.factory

import geoand.docker.events.model.api.ContainerNameAwareEvent
import geoand.docker.events.model.container.AbstractBaseDockerContainerEvent
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.reflections.Reflections

/**
 * Created by gandrianakis on 16/11/2015.
 */
@CompileStatic
@Slf4j
class ClasspathScanningContainerNameAwareEventFromDockerApiEventFactory implements ContainerNameAwareEventFromDockerApiEventFactory {

    static final Reflections reflections = new Reflections("geoand.docker.events.model.container") //use Reflections to scan all the subclasses of AbstractBaseDockerContainerEvent

    /**
     * TODO change value to be some kind of strategy if the way subclasses are used differentiates
     */
    static final Map<String, Class> EVENT_NAME_TO_EVENT_CLASS = [:]

    static {
        /**
         * Find all the subtypes of AbstractBaseDockerContainerEvent
         * and populate EVENT_NAME_TO_EVENT_CLASS using
         * the result of 'getEventName' as the key
         * and the class itself as the value
         */
        final Set<Class<? extends AbstractBaseDockerContainerEvent>> subTypes = reflections.getSubTypesOf(AbstractBaseDockerContainerEvent)
        EVENT_NAME_TO_EVENT_CLASS.putAll(subTypes.collectEntries {subType ->
            [(subType.newInstance().eventName.toLowerCase()) : subType]
        })
    }


    @Override
    AbstractBaseDockerContainerEvent createContainerEvent(ContainerNameAwareEvent dockerApiEvent) {
        final Class correspondingClass = EVENT_NAME_TO_EVENT_CLASS.get(dockerApiEvent.status.toLowerCase())
        if(!correspondingClass) {
            return null
        }

        try {
            return correspondingClass.metaClass.&invokeConstructor(dockerApiEvent.id, dockerApiEvent.name) as AbstractBaseDockerContainerEvent
        } catch (Exception e) {
            log.error("Unable to create instance of $correspondingClass", e)
            return null
        }
    }
}
