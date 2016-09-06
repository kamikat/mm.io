package moe.banana.mmio;

import javax.inject.Singleton;

import dagger.Component;
import moe.banana.mmio.scope.ApplicationScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.service.GankApiModule;

@Singleton
@ApplicationScope
@Component(modules = {
        GankApiModule.class,
        ConfigurationModule.class,
        TestHttpClientModule.class
})
public interface TestApiComponent {

    Gank api();
}
