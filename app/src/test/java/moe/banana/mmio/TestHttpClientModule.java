package moe.banana.mmio;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public final class TestHttpClientModule {

    @Provides
    @Singleton
    public static OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder().build();
    }
}
