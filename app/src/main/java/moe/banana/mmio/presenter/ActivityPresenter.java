package moe.banana.mmio.presenter;

import android.os.Bundle;
import android.support.annotation.CallSuper;

public abstract class ActivityPresenter extends BasePresenter {
    @CallSuper public void onRestoreInstanceState(Bundle savedInstanceState) { }
    @CallSuper public void onSaveInstanceState(Bundle outState) { }
}
