package moe.banana.mmio.presenter;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.CallSuper;

public abstract class BasePresenter extends BaseObservable {
    @CallSuper public void onCreate(Bundle savedInstanceState) { }
    @CallSuper public void onStart() { }
    @CallSuper public void onResume() { }
    @CallSuper public void onPause() { }
    @CallSuper public void onStop() { }
    @CallSuper public void onDestroy() { }
}
