package moe.banana.mmio.misc;

import android.view.View;
import android.view.ViewGroup;

public interface ItemViewFactory {

    /**
     * @param parent layout parent
     * @return item view that can be bind by {@link android.databinding.DataBindingUtil#bind(View)}
     */
    View createView(ViewGroup parent);
}
