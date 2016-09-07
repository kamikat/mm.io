package moe.banana.mmio.module;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    public @interface Linear { }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Grid2 { }

    @Qualifier
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Staggered { }

    @Provides
    @ActivityScope
    @Linear
    public static RecyclerView.LayoutManager provideLinearLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

}
