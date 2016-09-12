package moe.banana.mmio.misc;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public final class BindingAdapters {

    @BindingAdapter("src")
    public static void bindImage(ImageView view, String url) {
        Picasso.with(view.getContext()).load(url).fit().centerCrop().into(view);
    }
}
