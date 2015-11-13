package geoand.docker.events.container

import spock.lang.Specification

/**
 * Created by gandrianakis on 13/11/2015.
 */
class ContainerNameUtilTest extends Specification {

    def "leading slash is removed"(String originalName, String expectedSanitizedName) {
        expect:
            ContainerNameUtil.sanitize(originalName) == expectedSanitizedName

        where:
            originalName | expectedSanitizedName
            null | null
            "" | ""
            "test" | "test"
            "/test" | "test"
            "/test/" | "test"
    }
}
