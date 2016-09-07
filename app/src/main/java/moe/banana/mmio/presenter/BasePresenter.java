package moe.banana.mmio.presenter;

import android.os.Bundle;

// TODO is this necessary?
public interface BasePresenter {
    void onCreate(Bundle savedInstanceState);
    void onStart();
    void onRestoreInstanceState(Bundle savedInstanceState);
    void onResume();
    void onPause();
    void onSaveInstanceState(Bundle outState);
    void onStop();
    void onDestroy();
}
