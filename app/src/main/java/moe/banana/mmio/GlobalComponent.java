package moe.banana.mmio;

import javax.inject.Singleton;

import dagger.Component;
import moe.banana.mmio.scope.ApplicationScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.service.GankApiModule;
import moe.banana.mmio.service.HttpClientModule;

@Singleton
@ApplicationScope
@Component(modules = {
        GankApiModule.class,
        HttpClientModule.class,
        ConfigurationModule.class,
        App.class
})
public interface GlobalComponent {

    Gank api();
}
