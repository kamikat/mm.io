package moe.banana.mmio.presenter;

import android.os.Bundle;

public final class ActivityPresenterDelegate<PRESENTER extends BasePresenter> extends ActivityPresenter {

    private PRESENTER delegate;

    public ActivityPresenterDelegate(PRESENTER presenter) {
        delegate = presenter;
    }

    public PRESENTER getDelegate() {
        return delegate;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (delegate instanceof ActivityPresenter) {
            ((ActivityPresenter) delegate).onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (delegate instanceof ActivityPresenter) {
            ((ActivityPresenter) delegate).onSaveInstanceState(outState);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        delegate.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        delegate.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        delegate.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        delegate.onDestroy();
    }
}
