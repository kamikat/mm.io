package moe.banana.mmio;

import android.os.Build;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;

@Module
public final class ConfigurationModule {

    @Provides
    @Configuration(key = "baseUrl")
    public static String provideBaseUrl() {
        return "http://gank.io/api/";
    }

    @Provides
    @Configuration(key = "userAgentString")
    public static String provideUserAgentString() {
        String[] fingerprint = Build.FINGERPRINT != null ? Build.FINGERPRINT.split("/") : new String[0];
        String buildInfo = fingerprint.length > 3 ? "Build/" + Build.FINGERPRINT.split("/")[3] : "";
        return "Mozilla/5.0 (Linux; U; Android "
                + Build.VERSION.RELEASE + "; "
                + Locale.getDefault().toString() + "; "
                + Build.MODEL + " " + buildInfo + ") "
                + "AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 "
                + BuildConfig.APPLICATION_ID + "/" + BuildConfig.VERSION_NAME
                + " (Build " + BuildConfig.VERSION_CODE + ")";
    }

    @Provides
    @Configuration(key = "pageSize")
    public static int providePageSize() {
        return 20;
    }
}
