package geoand.docker.events.di

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig
import com.google.common.eventbus.EventBus
import com.google.inject.AbstractModule
import com.google.inject.matcher.Matchers
import geoand.docker.events.callback.EventResultCallbackTemplate
import geoand.docker.events.callback.PublishingEventResultCallbackTemplate
import geoand.docker.events.container.ContainerFinder
import geoand.docker.events.container.SimpleContainerFinder
import geoand.docker.events.factory.ClasspathScanningContainerNameAwareEventFromDockerApiEventFactory
import geoand.docker.events.factory.ContainerEventFromDockerApiEventFactory
import geoand.docker.events.factory.ContainerNameAwareEventFromDockerApiEventFactory
import geoand.docker.events.factory.SimpleDockerEventFactory
import geoand.docker.events.listener.StartEventListener
import geoand.docker.events.listener.StopEventListener
import geoand.docker.events.runtime.ModelAugmenter
import groovy.transform.CompileStatic

/**
 * Created by gandrianakis on 13/11/2015.
 */
@CompileStatic
class InjectionModule extends AbstractModule {

    /**
     * Exists for testing purposes
     */
    protected void configure(DockerClient dockerClient) {
        ModelAugmenter.augment() //monkey-patch the model specified by docker-java with some useful methods

        /**
         * TODO extract into separate modules in order to ease testing
         * Currently testing is possible using Modules.overrides but this
         * approach is not the best possible
         */

        bind(DockerClient).toInstance(dockerClient)
        bind(ContainerFinder).to(SimpleContainerFinder)
        bind(ContainerNameAwareEventFromDockerApiEventFactory).to(ClasspathScanningContainerNameAwareEventFromDockerApiEventFactory)
        bind(ContainerEventFromDockerApiEventFactory).to(SimpleDockerEventFactory)
        bind(EventResultCallbackTemplate).to(PublishingEventResultCallbackTemplate)

        final def eventBus = new EventBus("Default EventBus")
        bind(EventBus).toInstance(eventBus)

        createEventListenerBiding(eventBus)
    }

    @Override
    protected void configure() {
        final DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                .withVersion("1.16")
                .build();

        final DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        configure(dockerClient)
    }

    private void createEventListenerBiding(EventBus eventBus) {
        bindListener(Matchers.any(), new EventTypeListener(eventBus))

        bind(StartEventListener).toInstance(new StartEventListener())
        bind(StopEventListener).toInstance(new StopEventListener())
    }
}
