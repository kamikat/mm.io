package moe.banana.mmio.presenter;

import android.os.Bundle;
import android.support.annotation.CallSuper;

public abstract class ActivityPresenter {
    @CallSuper public void onCreate(Bundle savedInstanceState) { }
    @CallSuper public void onStart() { }
    @CallSuper public void onResume() { }
    @CallSuper public void onPause() { }
    @CallSuper public void onStop() { }
    @CallSuper public void onDestroy() { }
    @CallSuper public void onRestoreInstanceState(Bundle savedInstanceState) { }
    @CallSuper public void onSaveInstanceState(Bundle outState) { }
}
