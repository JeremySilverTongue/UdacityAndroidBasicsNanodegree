package com.udacity.silver.musicalstructure;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public final class Utils {

    private Utils() {
    }

    public static void createClickListener(Button button, final Context context, final Class destinationActivity) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, destinationActivity);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        });
    }
}
