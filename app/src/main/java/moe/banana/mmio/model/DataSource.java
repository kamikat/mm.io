package moe.banana.mmio.model;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import rx.Observable;

public interface DataSource<T> {

    int MASK_CHANGE_FLAG = 0x01; // 0b00000001
    int MASK_NOTIFY_FLAG = 0x02; // 0b00001110
    int MASK_ACTION_FLAG = 0xF0; // 0b11110000

    int FLAG_ACTION_REFRESH = 0x10;
    int FLAG_ACTION_FORWARD = 0x20;
    int FLAG_NOTIFY_ONGOING = 0x02;
    int FLAG_CHANGED        = 0x01;

    int getItemCount();

    T getItem(int position);

    void requestRefresh();

    Observable<Integer> notifyChangesTo(
            RecyclerView.Adapter<?> adapter,
            Observable.Transformer<List<? extends Article>, List<? extends Article>> afterReceive);
}
