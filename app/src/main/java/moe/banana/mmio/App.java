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

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().app(this).build();
    }

    public static AppComponent from(Context context) {
        return ((App) context.getApplicationContext()).mAppComponent;
    }
}
