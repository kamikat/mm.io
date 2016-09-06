package moe.banana.mmio;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import moe.banana.mmio.presenter.HelloWorldPresenter;
import moe.banana.mmio.scope.ActivityScope;
import moe.banana.mmio.view.HelloWorldView;

@Module
public class MainActivity extends AppCompatActivity implements HelloWorldPresenter {

    @ActivityScope
    @Component(modules = {MainActivity.class})
    interface Controller {
        HelloWorldView view();
        HelloWorldPresenter presenter();
    }

    @Provides
    @ActivityScope
    public HelloWorldView provideViewBinding() {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Provides
    @ActivityScope
    public HelloWorldPresenter providePresenter() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller controller = DaggerMainActivity_Controller.builder().mainActivity(this).build();
        controller.view().setPresenter(controller.presenter());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.GET_ACCOUNTS}, 0x01);
        }
    }

    @Override
    public String getName() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }

    @Override
    public void sayHello(String name) {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.say_hello, name))
                .setPositiveButton(android.R.string.ok, ((dialog, which) -> {}))
                .show();
    }

}
