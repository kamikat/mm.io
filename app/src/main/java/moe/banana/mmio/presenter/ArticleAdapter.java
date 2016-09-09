package moe.banana.mmio.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import moe.banana.mmio.R;
import moe.banana.mmio.model.ArticleSource;
import moe.banana.mmio.view.ArticleItemView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.Holder> {

    @Inject LayoutInflater inflater;

    @Inject
    ArticleAdapter() {
    }

    private ArticleSource source;

    public void setDataSource(ArticleSource source) {
        this.source = source;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.binding.setArticle(source.getItem(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return source == null || source.getItemCount() == 0 ? 0 : source.getItemCount() + 1;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        ArticleItemView binding;

        Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_article, parent, false));
            binding = ArticleItemView.bind(itemView);
            binding.setHolder(this);
        }
    }
}
