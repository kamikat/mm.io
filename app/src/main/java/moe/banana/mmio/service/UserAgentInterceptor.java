package moe.banana.mmio.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class UserAgentInterceptor implements Interceptor {

    private final String mUserAgent;

    public UserAgentInterceptor(String userAgent) {
        mUserAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(request.newBuilder()
                .header("User-Agent", mUserAgent)
                .build());
    }
}
