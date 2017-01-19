package moe.banana.mmio;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.Locale;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.service.GankApiModule;
import moe.banana.mmio.service.HttpClientModule;

@Module
public final class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = onCreateComponent();
    }

    public static AppComponent from(Context context) {
        return ((App) context.getApplicationContext()).mAppComponent;
    }

    public static AppComponent from(Fragment fragment) {
        return from(fragment.getContext());
    }

    private AppComponent onCreateComponent() {
        return DaggerAppComponent.builder()
                .app(this)
                .httpClientModule(new HttpClientModule(getUserAgent(), new File(getCacheDir(), "service/")))
                .gankApiModule(new GankApiModule("http://gank.io/api/"))
                .build();
    }

    @Provides
    public Context provideContext() {
        return this;
    }

    private static String getUserAgent() {
        String[] fingerprint = Build.FINGERPRINT != null ? Build.FINGERPRINT.split("/") : new String[0];
        String buildInfo = fingerprint.length > 3 ? "Build/" + Build.FINGERPRINT.split("/")[3] : "";
        return "Mozilla/5.0 (Linux; U; Android "
                + Build.VERSION.RELEASE + "; "
                + Locale.getDefault().toString() + "; "
                + Build.MODEL + " " + buildInfo + ") "
                + "AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 "
                + BuildConfig.APPLICATION_ID + "/" + BuildConfig.VERSION_NAME
                + " (Build " + BuildConfig.VERSION_CODE + ")";
    }
}
