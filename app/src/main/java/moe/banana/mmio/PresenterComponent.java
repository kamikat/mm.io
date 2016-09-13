package moe.banana.mmio;

import android.databinding.ViewDataBinding;

public interface PresenterComponent<VM extends ViewDataBinding, PRESENTER> {

    /**
     * @return DataBinding view model object (must support BR.presenter)
     */
    VM vm();

    /**
     * @return Presenter object
     */
    PRESENTER presenter();
}
