package moe.banana.mmio;

import android.databinding.ViewDataBinding;

import moe.banana.mmio.presenter.BasePresenter;

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
