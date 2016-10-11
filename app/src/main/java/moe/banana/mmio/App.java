package moe.banana.mmio;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

@Module
public final class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder().app(this).build();

        // Setup Picasso singleton
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        boolean largeHeap = (getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && SDK_INT >= HONEYCOMB) {
            memoryClass = am.getLargeMemoryClass();
        }
        int memCacheSize = 1024 * 1024 * memoryClass / 3; // Target ~33% of the available heap.

        Picasso picasso = new Picasso.Builder(this)
                .memoryCache(new LruCache(memCacheSize))
                .build();
        Picasso.setSingletonInstance(picasso);
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
