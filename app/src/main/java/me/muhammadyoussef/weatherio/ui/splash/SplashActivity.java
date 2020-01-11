package me.muhammadyoussef.weatherio.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.ui.host.HostActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyWindowFlags();
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this::navigateToMainScreen, 2000);
    }

    private void applyWindowFlags() {
        int flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        int mask = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setFlags(flags, mask);
    }

    private void navigateToMainScreen() {
        startActivity(new Intent(this, HostActivity.class));
        finish();
    }
}
