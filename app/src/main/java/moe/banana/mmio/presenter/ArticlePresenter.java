package moe.banana.mmio.presenter;

import javax.inject.Inject;

import moe.banana.mmio.view.ArticleItem;
import moe.banana.mmio.view.MainViewModel;

public class ArticlePresenter {

    @Inject public ArticleItem vm;

    @Inject ArticlePresenter() {
    }
}
