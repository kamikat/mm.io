package moe.banana.mmio.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

import moe.banana.mmio.R;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.Holder> {

    @Inject LayoutInflater inflater;

    @Inject ArticleAdapter() {
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater, parent);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class Holder extends RecyclerView.ViewHolder {

        Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.article_card, parent, false));
        }
    }
}
