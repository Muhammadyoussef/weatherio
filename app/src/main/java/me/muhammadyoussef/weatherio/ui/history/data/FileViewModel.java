package me.muhammadyoussef.weatherio.ui.history.data;

import android.net.Uri;

import java.util.Objects;

import androidx.annotation.NonNull;

public class FileViewModel {

    private final String id;
    private final String name;
    private final String date;
    private final Uri thumbnail;

    FileViewModel(@NonNull String id, @NonNull String name, @NonNull String date, @NonNull Uri thumbnail) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileViewModel)) {
            return false;
        }
        FileViewModel that = (FileViewModel) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                getDate().equals(that.getDate()) &&
                getThumbnail().equals(that.getThumbnail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDate(), getThumbnail());
    }
}
