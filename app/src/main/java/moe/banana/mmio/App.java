package moe.banana.mmio;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

@Module
public final class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().app(this).build();
    }

    public static AppComponent from(Context context) {
        return ((App) context.getApplicationContext()).mAppComponent;
    }

    public static AppComponent from(Fragment fragment) {
        return from(fragment.getContext());
    }

    @Provides
    public Context provideContext() {
        return this;
    }
}
