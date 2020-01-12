package me.muhammadyoussef.weatherio.ui.annotation;

import android.graphics.Bitmap;
import android.net.Uri;

public interface AnnotationContract {

    interface View {

        void setupViews();

        void loadPhoto(Uri photoUri);

        void showLoadingHint();

        void showEditingHint();

        void showNetworkFailureHint();

        void notifyDataSetChanged();

        void navigateToDetails(Uri uri);

        void showTemperature(String temp);

        void showHumidity(String humidity);

        void showLocation(String location);

        void showCondition(String condition);

        void hideTemperature();

        void hideHumidity();

        void hideLocation();

        void hideCondition();
    }

    interface Presenter {

        void onCreate(AnnotationActivityArgs activityArgs);

        void onSaveClicked(Bitmap captureBitmap);

        void onReloadClicked();
    }
}
