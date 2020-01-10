package me.muhammadyoussef.weatherio.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import me.muhammadyoussef.weatherio.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyWindowFlags();
        setContentView(R.layout.activity_splash);
        // TODO navigate to main screen
        new Handler().postDelayed(this::finish, 2000);
    }

    private void applyWindowFlags() {
        int flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        int mask = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setFlags(flags, mask);
    }
}
