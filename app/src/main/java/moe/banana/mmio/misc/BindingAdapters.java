package moe.banana.mmio.misc;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import moe.banana.mmio.R;

public final class BindingAdapters {

    @BindingAdapter("src")
    public static void bindImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).fit().centerCrop().into(view);
    }

    @BindingAdapter("thumbnailSrc")
    public static void bindThumbnail(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url)
                .resizeDimen(R.dimen.thumbnail_size_w, R.dimen.thumbnail_size_h)
                .centerCrop()
                .into(view);
    }

    @BindingAdapter("itemFactory")
    public static void bindItemFactory(RecyclerView view, ItemViewFactory factory) {
        // do nothing
    }
}
