package me.muhammadyoussef.weatherio.ui.history.data;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.utils.FileUtils;
import me.muhammadyoussef.weatherio.utils.ResourcesUtil;
import me.muhammadyoussef.weatherio.utils.TimeUtil;

@FragmentScope
public class FileMapper {

    private final ResourcesUtil resourcesUtil;
    private final TimeUtil timeUtil;

    @Inject
    FileMapper(ResourcesUtil resourcesUtil, TimeUtil timeUtil) {
        this.resourcesUtil = resourcesUtil;
        this.timeUtil = timeUtil;
    }

    @NonNull
    public FileViewModel toViewModel(@NonNull File file) {
        return new FileViewModel(
                file.getAbsolutePath(),
                resourcesUtil.getString(R.string.photo) + " #" + FileUtils.getFileName(file),
                timeUtil.formatFullDate(file.lastModified()),
                Uri.fromFile(file));
    }

    @NonNull
    public List<FileViewModel> toViewModels(@NonNull File... files) {
        List<FileViewModel> viewModels = new ArrayList<>();
        for (File file : files) {
            viewModels.add(toViewModel(file));
        }
        return viewModels;
    }
}
