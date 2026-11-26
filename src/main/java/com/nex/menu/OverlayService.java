package com.nex.menu;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.*;

public class OverlayService extends Service {

    WindowManager wm;
    TextView bubble;
    LinearLayout panel;

    @Override
    public void onCreate() {
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        int type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        bubble = new TextView(this);
        bubble.setText("N");
        bubble.setTextColor(Color.WHITE);
        bubble.setTextSize(25);
        bubble.setGravity(Gravity.CENTER);
        bubble.setBackgroundColor(Color.DKGRAY);

        WindowManager.LayoutParams params =
            new WindowManager.LayoutParams(
                55, 55, type,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            );

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 20;
        params.y = 150;

        wm.addView(bubble, params);

        bubble.setOnClickListener(v -> openMenu());
    }

    void openMenu() {
        if (panel != null) return;

        panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(20, 15, 20, 15);
        panel.setBackgroundColor(Color.BLACK);

        TextView title = new TextView(this);
        title.setText("NEX MENU       ACTIVE");
        title.setTextColor(Color.WHITE);
        title.setTextSize(21);
        panel.addView(title);

        for (String name : new String[]{
            "ESP",
            "Skeleton",
            "Box ESP",
            "AIM",
            "Aim Circle",
            "MISC"
        }) {
            Switch sw = new Switch(this);
            sw.setText(name);
            sw.setTextColor(Color.WHITE);
            panel.addView(sw);
        }

        Button close = new Button(this);
        close.setText("ЗАКРЫТЬ");
        panel.addView(close);

        close.setOnClickListener(v -> {
            wm.removeView(panel);
            panel = null;
        });

        WindowManager.LayoutParams p =
            new WindowManager.LayoutParams(
                850, 950,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            );

        p.gravity = Gravity.CENTER;
        wm.addView(panel, p);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
