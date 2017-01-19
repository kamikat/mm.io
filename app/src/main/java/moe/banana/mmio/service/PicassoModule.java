package moe.banana.mmio.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.scope.ApplicationScope;

@Module
public abstract class PicassoModule {

    private PicassoModule() {
        // This disables inheritance
    }

    @Provides
    @ApplicationScope
    public static Picasso providePicasso(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            memoryClass = am.getLargeMemoryClass();
        }
        int memCacheSize = 1024 * 1024 * memoryClass / 3; // Target ~33% of the available heap.

        Picasso picasso = new Picasso.Builder(context)
                .memoryCache(new LruCache(memCacheSize))
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }
}
