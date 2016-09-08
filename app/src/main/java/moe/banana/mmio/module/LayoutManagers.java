package moe.banana.mmio.module;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.scope.ActivityScope;

@Module
public class LayoutManagers {

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Columns {
        int value();
    }

    @Provides
    @ActivityScope
    public static LinearLayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    @ActivityScope
    @Columns(2)
    public static GridLayoutManager provideGridLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }

}
