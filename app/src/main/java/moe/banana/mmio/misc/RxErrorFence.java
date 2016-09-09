package moe.banana.mmio.misc;

import rx.Observable;
import rx.Subscriber;

public class RxErrorFence<T> implements Observable.OnSubscribe<T> {

    private Subscriber<? super T> subscriber;

    public void boom(Throwable e) {
        subscriber.onError(e);
    }

    public Observable<T> build() {
        return Observable.create(this);
    }

    @Override
    public void call(Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    public static <T> RxErrorFence<T> create() {
        return new RxErrorFence<>();
    }
}
