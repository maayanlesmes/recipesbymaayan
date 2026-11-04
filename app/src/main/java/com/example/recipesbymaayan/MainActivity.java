package com.example.recipesbymaayan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // שינינו ל-activity_splash

        // 1. מוצאים את רכיב התמונה
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView logoImage = findViewById(R.id.imageView2);

        // 2. טוענים את קובץ התנועה (האנימציה) שיצרת
        Animation runUpAnim = AnimationUtils.loadAnimation(this, R.anim.movement1);

        // 3. מפעילים את האנימציה על התמונה!
        logoImage.startAnimation(runUpAnim);
    } }
