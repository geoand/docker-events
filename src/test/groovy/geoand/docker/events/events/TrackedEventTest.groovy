package geoand.docker.events.events

import geoand.docker.events.events.SimpleEventResultCallbackTemplate.TrackedEvent
import spock.lang.Specification

/**
 * Created by gandrianakis on 13/11/2015.
 */
class TrackedEventTest extends Specification {

    def "findMatching works regardless of the case"(String eventName, TrackedEvent expectedResult) {
        expect:
            TrackedEvent.findMatching(eventName) == expectedResult

        where:
            eventName | expectedResult
            "start" | TrackedEvent.START
            "stArT" | TrackedEvent.START
            "START" | TrackedEvent.START
            "stop" | TrackedEvent.STOP
            "StOp" | TrackedEvent.STOP
            "STOP" | TrackedEvent.STOP
            "missing" | null
    }
}
