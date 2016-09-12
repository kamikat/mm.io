package moe.banana.mmio;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.service.UserAgentInterceptor;
import okhttp3.OkHttpClient;

@Module
public final class TestHttpClientModule {

    @Provides
    @Singleton
    public static OkHttpClient provideHttpClient(Configuration conf) {
        return new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor(conf.userAgent()))
                .build();
    }
}
