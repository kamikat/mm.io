package moe.banana.mmio.service;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Rfc3339DateJsonAdapter;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.Configuration;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.schedulers.Schedulers;

@Module
public final class PicassoModule {

    @Provides
    @Singleton
    public static Gank provideGank(Configuration conf, OkHttpClient client, Moshi moshi) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(conf.baseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build();
        return retrofit.create(Gank.class);
    }

    @Provides
    public static Moshi provideJsonParser() {
        return new Moshi.Builder()
                .add(Date.class, new Rfc3339DateJsonAdapter())
                .build();
    }
}
