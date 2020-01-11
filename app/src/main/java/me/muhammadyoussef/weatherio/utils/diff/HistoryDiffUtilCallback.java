package me.muhammadyoussef.weatherio.utils.diff;

import android.util.Pair;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import me.muhammadyoussef.weatherio.ui.history.data.FileViewModel;


public class HistoryDiffUtilCallback extends DiffUtil.Callback {

    private final List<FileViewModel> oldItems;
    private final List<FileViewModel> newItems;

    public HistoryDiffUtilCallback(Pair<List<FileViewModel>, List<FileViewModel>> oldNewListsPair) {
        this.oldItems = oldNewListsPair.first;
        this.newItems = oldNewListsPair.second;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition) == newItems.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition));
    }

    public List<FileViewModel> getOldItems() {
        return oldItems;
    }

    public List<FileViewModel> getNewItems() {
        return newItems;
    }
}
