package moe.banana.mmio.service;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;

import java.util.Date;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.scope.ApplicationScope;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.schedulers.Schedulers;

@Module
public final class GankApiModule {

    private final HttpUrl endpoint;

    public GankApiModule(String endpoint) {
        this(HttpUrl.parse(endpoint));
    }

    public GankApiModule(HttpUrl endpoint) {
        this.endpoint = endpoint;
    }

    @Provides
    @ApplicationScope
    public Gank provideGank(OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder()
                        .add(Date.class, new Rfc3339DateJsonAdapter())
                        .build()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .build();
        return retrofit.create(Gank.class);
    }
}
