package me.muhammadyoussef.weatherio.ui.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.muhammadyoussef.weatherio.ui.ActivityArgs;

public class DetailsActivityArgs implements ActivityArgs {

    private static final String KEY_FILE = "EXTRA_PHOTO";
    private final Uri photoUri;

    public DetailsActivityArgs(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public static DetailsActivityArgs deserializeFrom(@NonNull Intent intent) {
        return new DetailsActivityArgs(intent.getParcelableExtra(KEY_FILE));
    }

    @Nullable
    public Uri getPhotoUri() {
        return photoUri;
    }

    @Override
    public Intent intent(Context activity) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(KEY_FILE, photoUri);
        return intent;
    }
}
