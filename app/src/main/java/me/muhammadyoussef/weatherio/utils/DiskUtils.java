package me.muhammadyoussef.weatherio.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Single;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.di.qualifier.ForApplication;
import me.muhammadyoussef.weatherio.di.scope.ApplicationScope;

@ApplicationScope
public class DiskUtils {

    private final Context context;

    @Inject
    DiskUtils(@ForApplication Context context) {
        this.context = context;
    }

    public Single<Uri> save(@NonNull Bitmap bitmap, @NonNull File folder) {
        return Single.create(e -> {
            File newFile = newImageFile(folder);
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(newFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Uri uri = Uri.fromFile(newFile);
            e.onSuccess(uri);
        });
    }

    private File getRootDirectory() {
        String rootDirectory = getExternalStorageRoot();
        if (rootDirectory == null) {
            // TODO check disk access permission
            rootDirectory = getInternalStorageRoot();
        }
        File directory = new File(rootDirectory + "/" + context.getString(R.string.app_name) + "/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    @Nullable
    private String getExternalStorageRoot() {
        File externalFilesDir = context.getExternalFilesDir(null);
        if (externalFilesDir != null && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return externalFilesDir.getAbsolutePath();
        }
        return null;
    }

    @NonNull
    private String getInternalStorageRoot() {
        return context.getFilesDir().getAbsolutePath();
    }

    private File getNewDirectory(@NonNull String directoryName) {
        File directory = new File(getRootDirectory(), "/" + directoryName + "/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    public File getAttachmentsDirectory() {
        return getNewDirectory("attachments");
    }

    public File newImageFile(@NonNull File directory) {
        File[] files = directory.listFiles();
        String fileName = files == null ? "1" : "" + (files.length + 1);
        return new File(directory, fileName + ".jpg");
    }
}
