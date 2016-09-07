package moe.banana.mmio.presenter;

import android.support.v7.widget.RecyclerView;

public interface MainPresenter {

    RecyclerView.LayoutManager getLayoutManager();

    RecyclerView.Adapter<?> getAdapter();
}
