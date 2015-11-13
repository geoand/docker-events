package geoand.docker.events.di

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig
import com.google.inject.AbstractModule
import geoand.docker.events.container.ContainerFinder
import geoand.docker.events.container.SimpleContainerFinder
import geoand.docker.events.events.EventResultCallbackTemplate
import geoand.docker.events.events.SimpleEventResultCallbackTemplate
import groovy.transform.CompileStatic

/**
 * Created by gandrianakis on 13/11/2015.
 */
@CompileStatic
class InjectionModule extends AbstractModule{

    @Override
    protected void configure() {
        final DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                .withVersion("1.15")
                .build();

        final DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        bind(DockerClient).toInstance(dockerClient)
        bind(EventResultCallbackTemplate).to(SimpleEventResultCallbackTemplate)
        bind(ContainerFinder).to(SimpleContainerFinder)
    }
}
