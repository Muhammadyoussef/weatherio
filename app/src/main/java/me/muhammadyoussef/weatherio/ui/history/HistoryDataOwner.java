package me.muhammadyoussef.weatherio.ui.history;

import me.muhammadyoussef.weatherio.ui.history.data.FileViewModel;

public interface HistoryDataOwner {

    int getItemCount();

    FileViewModel getItem(int position);

    void onItemClicked(FileViewModel item);
}
