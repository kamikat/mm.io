package moe.banana.mmio.presenter;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import rx.Observable;
import rx.Subscriber;
import rx.internal.util.SubscriptionList;

public abstract class BasePresenter extends BaseObservable {

    private SubscriptionList subscriptions = new SubscriptionList();

    @CallSuper public void onCreate(Bundle savedInstanceState) { }
    @CallSuper public void onStart() { }
    @CallSuper public void onResume() { }
    @CallSuper public void onPause() { }
    @CallSuper public void onStop() { }
    @CallSuper public void onDestroy() {
        if (subscriptions != null) {
            subscriptions.unsubscribe();
        }
    }

    public <T> Observable.Operator<T, T> unsubscribeOnDestroy() {
        return subscriber -> {
            Subscriber<T> proxy = new Subscriber<T>() {
                @Override
                public void onCompleted() {
                    subscriber.onCompleted();
                }

                @Override
                public void onError(Throwable e) {
                    subscriber.onError(e);
                }

                @Override
                public void onNext(T t) {
                    subscriber.onNext(t);
                }
            };
            subscriptions.add(proxy);
            return proxy;
        };
    }
}
