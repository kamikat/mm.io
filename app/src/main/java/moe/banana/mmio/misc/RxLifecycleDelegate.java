package moe.banana.mmio.misc;

import rx.Observable;
import rx.Subscriber;
import rx.internal.util.SubscriptionList;

public final class RxLifecycleDelegate {

    private SubscriptionList subscriptionsOnDestroy;

    public void onDestroy() {
        if (subscriptionsOnDestroy != null) {
            subscriptionsOnDestroy.unsubscribe();
        }
    }

    public <T> Observable.Operator<T, T> disposeOnDestroy() {
        return subscriber -> {
            if (subscriptionsOnDestroy == null) {
                subscriptionsOnDestroy = new SubscriptionList();
            }
            Subscriber<T> proxy = new IdenticalSubscriber<>(subscriber);
            subscriptionsOnDestroy.add(proxy);
            return proxy;
        };
    }

    private static class IdenticalSubscriber<T> extends Subscriber<T> {

        final Subscriber<? super T> subscriber;

        IdenticalSubscriber(Subscriber<? super T> subscriber) {
            this.subscriber = subscriber;
        }

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
    }
}
