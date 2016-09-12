package moe.banana.mmio.presenter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import moe.banana.mmio.BR;
import moe.banana.mmio.misc.ItemViewFactory;
import moe.banana.mmio.model.Article;
import moe.banana.mmio.model.DataSource;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.Holder> {

    private final DataSource<Article> source;
    private final ItemViewFactory factory;

    ArticleAdapter(DataSource<Article> source, ItemViewFactory factory) {
        this.source = source;
        this.factory = factory;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(factory, parent);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setVariable(BR.article, source.getItem(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return source == null || source.getItemCount() == 0 ? 0 : source.getItemCount() + 1;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        Holder(ItemViewFactory factory, ViewGroup parent) {
            super(factory.createView(parent));
            binding = DataBindingUtil.bind(itemView);
            binding.setVariable(BR.holder, this);
        }
    }
}
