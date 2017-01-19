package moe.banana.mmio;

import android.os.Bundle;

public interface Presenter {

    void onCreate(Bundle savedInstanceState);

    void onSaveInstanceState(Bundle outState);

    void onDestroy();

}
