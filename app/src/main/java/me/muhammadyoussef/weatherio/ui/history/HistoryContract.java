package me.muhammadyoussef.weatherio.ui.history;

import androidx.recyclerview.widget.DiffUtil;
import me.muhammadyoussef.weatherio.ui.history.data.FileViewModel;

public interface HistoryContract {

    interface View {

        void updateUi(DiffUtil.DiffResult result);
    }

    interface Presenter {

        void onViewCreated();

        int getItemCount();

        FileViewModel getItem(int position);

        void onDestroy();
    }
}
