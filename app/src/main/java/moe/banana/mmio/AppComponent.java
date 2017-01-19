package moe.banana.mmio;

import com.squareup.picasso.Picasso;

import dagger.Component;
import moe.banana.mmio.scope.ApplicationScope;
import moe.banana.mmio.service.Gank;
import moe.banana.mmio.service.GankApiModule;
import moe.banana.mmio.service.HttpClientModule;
import moe.banana.mmio.service.PicassoModule;

@ApplicationScope
@Component(modules = {
        HttpClientModule.class,
        GankApiModule.class,
        PicassoModule.class,
        App.class
})
public interface AppComponent {

    Gank api();

    Picasso picasso();
}
