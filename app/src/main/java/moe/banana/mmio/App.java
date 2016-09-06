package moe.banana.mmio;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.scope.ApplicationScope;

@Module
public final class App extends Application {

    @Provides
    @ApplicationScope
    public Context provideContext() {
        return this;
    }

    private GlobalComponent mGlobalComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mGlobalComponent = DaggerGlobalComponent.builder().app(this).build();
    }

    public static GlobalComponent getGlobalComponent(Context context) {
        return ((App) context.getApplicationContext()).mGlobalComponent;
    }
}
