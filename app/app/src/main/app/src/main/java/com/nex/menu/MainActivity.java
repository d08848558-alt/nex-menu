package com.nex.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.net.Uri;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.BLACK);

        TextView title = new TextView(this);
        title.setText("NEX MENU");
        title.setTextColor(Color.WHITE);
        title.setTextSize(32);
        layout.addView(title);

        TextView status = new TextView(this);
        status.setText("ANDROID GAME LOADER");
        status.setTextColor(Color.GRAY);
        layout.addView(status);

        Button connect = new Button(this);
        connect.setText("ПОДКЛЮЧИТЬ");
        layout.addView(connect);

        connect.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            for (ResolveInfo app : getPackageManager()
                    .queryIntentActivities(intent, 0)) {
                String name = app.loadLabel(
                    getPackageManager()).toString();

                if (name.equalsIgnoreCase("Standoff 2")) {
                    Intent launch = getPackageManager()
                        .getLaunchIntentForPackage(
                            app.activityInfo.packageName);
                    if (launch != null) {
                        startActivity(launch);
                        return;
                    }
                }
            }
            status.setText("Игра не найдена!");
        });

        Button overlay = new Button(this);
        overlay.setText("ВКЛЮЧИТЬ ПАНЕЛЬ");
        layout.addView(overlay);

        overlay.setOnClickListener(v -> {
            if (!Settings.canDrawOverlays(this)) {
                Intent i = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
                startActivity(i);
            } else {
                startService(new Intent(this, OverlayService.class));
            }
        });

        setContentView(layout);
    }
}
