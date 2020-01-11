package me.muhammadyoussef.weatherio.ui.host;

public interface HostContract {

    interface View {
        void setupClickListeners();

        void displayCameraScreen();

        void displayHistoryScreen();
    }

    interface Presenter {
        void onCreate();

        void onCameraClicked();

        void onHistoryClicked();
    }
}
