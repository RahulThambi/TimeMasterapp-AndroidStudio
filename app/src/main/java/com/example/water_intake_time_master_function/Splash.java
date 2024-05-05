package com.example.water_intake_time_master_function;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class Splash extends AppCompatActivity
{

    private static final int SPLASH_DURATION = 3000; // 3 seconds
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    logoImageView = findViewById(R.id.logoImageView);

    // Apply blinking animation
    applyBlinkingAnimation();

    // Delay the launch of the main activity to match the animation duration
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run()
        {
            Intent intent = new Intent(Splash.this, caraousel.class);
            startActivity(intent);
            finish();
        }
    }, SPLASH_DURATION); // Match the duration with the animation duration
}
    private void applyBlinkingAnimation()
    {
        Animation blinkAnimation = new AlphaAnimation(1.0f, 0.0f);
        blinkAnimation.setDuration(1000); // 1 second for each phase (on and off)
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        blinkAnimation.setRepeatCount(Animation.INFINITE);

        logoImageView.startAnimation(blinkAnimation);
    }
}