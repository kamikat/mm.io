package moe.banana.mmio.presenter;

public interface PresenterComponent<VM, PRESENTER extends BasePresenter> {

    /**
     * @return DataBinding view model object (must support BR.presenter)
     */
    VM vm();

    /**
     * @return Presenter object
     */
    PRESENTER presenter();
}
