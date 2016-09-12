package moe.banana.mmio.presenter;

import android.databinding.ViewDataBinding;

public interface PresenterComponent<VM extends ViewDataBinding, PRESENTER extends BasePresenter> {

    /**
     * @return DataBinding view model object (must support BR.presenter)
     */
    VM vm();

    /**
     * @return Presenter object
     */
    PRESENTER presenter();
}
