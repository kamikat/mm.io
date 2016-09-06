package moe.banana.mmio.service;

import android.content.Context;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.Configuration;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public final class HttpClientModule {

    @Provides
    @Singleton
    public static OkHttpClient provideHttpClient(
            Context context, @Configuration(key = "userAgentString") String uaStr) {
        return new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor(uaStr))
                .cache(new Cache(new File(context.getCacheDir(), "service"), 2 << 23)) // create 16MB response cache.
                .build();
    }
}
