package moe.banana.mmio.misc;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import moe.banana.mmio.BR;

/**
 * A {@link android.support.v7.widget.RecyclerView.ViewHolder} for Data Binding Library
 * Help parses binding object and set a holder variable on target binding.
 */
public class BindingHolder<VM extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public static <VM extends ViewDataBinding> BindingHolder<VM> create(View view) {
        return new BindingHolder<>(view);
    }

    public VM binding;

    BindingHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.setVariable(BR.holder, this);
    }
}
