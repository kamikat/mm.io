package moe.banana.mmio.module;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

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
    public @interface Linear {
        int orientation() default LinearLayoutManager.VERTICAL;
    }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Grid {
        int columns() default 2;
        int orientation() default GridLayoutManager.VERTICAL;
    }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Staggered {
        int columns() default 2;
        int orientation() default StaggeredGridLayoutManager.VERTICAL;
    }

    @Provides
    @ActivityScope
    @Linear
    public static RecyclerView.LayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    @Provides
    @ActivityScope
    @LayoutManagers.Grid
    public static RecyclerView.LayoutManager provideGridLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }

    @Provides
    @ActivityScope
    @LayoutManagers.Staggered
    public static RecyclerView.LayoutManager provideStaggeredLayoutManager(Context context) {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

}
