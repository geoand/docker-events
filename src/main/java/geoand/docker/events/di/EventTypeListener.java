package geoand.docker.events.di;

import com.google.common.eventbus.EventBus;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * Created by gandrianakis on 16/11/2015.
 */
public class EventTypeListener implements TypeListener {

    private final EventBus eventBus;

    public EventTypeListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        encounter.register((InjectionListener<I>) eventBus::register);
    }
}
