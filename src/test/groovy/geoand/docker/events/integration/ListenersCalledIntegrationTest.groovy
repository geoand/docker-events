package geoand.docker.events.integration

import com.github.dockerjava.api.DockerClient
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.google.inject.Binder
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Module
import com.google.inject.util.Modules
import geoand.docker.events.callback.EventResultCallbackTemplate
import geoand.docker.events.di.InjectionModule
import geoand.docker.events.listener.StartEventListener
import geoand.docker.events.model.container.DockerContainerEventStart
import org.junit.*

import static com.github.tomakehurst.wiremock.client.WireMock.*

/**
 * Created by gandrianakis on 17/11/2015.
 */
class ListenersCalledIntegrationTest {

    private static final int DOCKER_PORT = 8089

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(DOCKER_PORT);
    Injector injector

    /**
     * Use the poor-man's Mock, since Mockito does not work correctly with Groovy classes
     */
    StartEventListener startEventListener = new StartEventListener() {

        public List<DockerContainerEventStart> argumentsOfEachInvocation = []

        @Override
        void handleEvent(DockerContainerEventStart event) {
            argumentsOfEachInvocation << event
        }
    }

    @BeforeClass
    public static void init() {
        System.setProperty("docker.io.url", "http://localhost:${DOCKER_PORT}")
    }

    @AfterClass
    public static void cleanup() {
        System.clearProperty("docker.io.url")
    }



    @Before
    public void setUp() {
        injector = Guice.createInjector(Modules.override(new InjectionModule()).with(new Module() {
            @Override
            void configure(Binder binder) {
                binder.bind(StartEventListener).toInstance(startEventListener)
            }
        }))
    }

    @Test
    public void startEventListenerCalled() {
        final String containerID = "d2a7615c5741549fd2c4e712329f2866224f8997a1ca8436a12307086298bace"
        final String containerName = "testRabbitMQ"
        stubCallsToDockerAPI(containerID, containerName)


        final DockerClient dockerClient = injector.getInstance(DockerClient)
        final EventResultCallbackTemplate eventsResultHandler = injector.getInstance(EventResultCallbackTemplate)

        dockerClient.eventsCmd().exec(eventsResultHandler) //initiate event listening
        Thread.sleep(1000) //make sure that docker-java has had enough time to process the event


        final def listenerArguments = startEventListener.argumentsOfEachInvocation
        assert listenerArguments.size() == 1
        assert listenerArguments[0].containerFirstName == containerName
    }

    def void stubCallsToDockerAPI(String containerID, String containerName) {
        stubFor(get(urlEqualTo("/v1.16/events"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                            {
                                "status": "start",
                                "id": "${containerID}",
                                "from": "rabbitmq",
                                "time": 1447768131,
                                "timeNano": 1447768131919385039
                            }
                    """))
        )

        stubFor(get(urlPathEqualTo("/v1.16/containers/json"))
                .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    [{
                             "Id": "${containerID}",
                             "Names":["/${containerName}"],
                             "Image": "rabbitmq:latest",
                             "Command": "echo 1",
                             "Created": 1367854155,
                             "Status": "Exit 0",
                             "Ports": [{"PrivatePort": 2222, "PublicPort": 3333, "Type": "tcp"}],
                             "SizeRw": 12288,
                             "SizeRootFs": 0
                     }]
                """))
        )
    }
}
