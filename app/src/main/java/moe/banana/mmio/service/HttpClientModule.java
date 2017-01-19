package moe.banana.mmio.service;

import java.io.File;
import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Module
public final class HttpClientModule {

    private final String userAgentString;
    private final File cacheDir;
    private final long cacheSize;

    public HttpClientModule(String userAgentString, File cacheDir) {
        this(userAgentString, cacheDir, 2 << 23); // 16MB response cache by default
    }

    public HttpClientModule(String userAgentString, File cacheDir, long cacheSize) {
        this.userAgentString = userAgentString;
        this.cacheDir = cacheDir;
        this.cacheSize = cacheSize;
    }

    @Provides
    public OkHttpClient provideHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new UserAgentInterceptor())
                .cache(new Cache(cacheDir, cacheSize))
                .build();
    }

    private class UserAgentInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            return chain.proceed(request.newBuilder()
                    .header("User-Agent", userAgentString)
                    .build());
        }
    }
}
