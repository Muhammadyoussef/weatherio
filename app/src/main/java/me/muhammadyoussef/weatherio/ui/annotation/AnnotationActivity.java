package me.muhammadyoussef.weatherio.ui.annotation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.WeatherioApp;
import me.muhammadyoussef.weatherio.di.activity.ActivityModule;
import me.muhammadyoussef.weatherio.store.model.weather.WeatherConditionItem;
import me.muhammadyoussef.weatherio.ui.details.DetailsActivityArgs;

public class AnnotationActivity extends AppCompatActivity implements AnnotationContract.View, WeatherDataOwner {

    @BindView(R.id.iv_photo)
    ImageView photoImageView;
    @BindView(R.id.tv_status)
    TextView statusTextView;
    @BindView(R.id.layout_bottom_sheet)
    ConstraintLayout bottomSheetLayout;
    @BindView(R.id.rv_info)
    RecyclerView recyclerView;
    @BindView(R.id.tv_reload)
    TextView emptyTextView;
    @BindView(R.id.layout_photo_container)
    ConstraintLayout photoContainer;
    @BindView(R.id.tv_temp)
    TextView temperatureTextView;
    @BindView(R.id.tv_himidity)
    TextView humidityTextView;
    @BindView(R.id.tv_location)
    TextView locationTextView;
    @BindView(R.id.tv_condition)
    TextView conditionTextView;

    @Inject
    AnnotationPresenter presenter;
    @Inject
    WeatherAdapter adapter;

    BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        WeatherioApp.getComponent(getApplicationContext())
                .plus(new ActivityModule(this))
                .inject(this);
        ButterKnife.bind(this);
        presenter.onCreate(AnnotationActivityArgs.deserializeFrom(getIntent()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setupViews() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadPhoto(Uri photoUri) {
        Glide.with(this)
                .load(photoUri)
                .into(photoImageView);
    }

    @Override
    public void showLoadingHint() {
        statusTextView.setText(R.string.fetching);
        statusTextView.setActivated(false);
        emptyTextView.setVisibility(View.GONE);
    }

    @Override
    public void showEditingHint() {
        statusTextView.setText(R.string.add_info);
        statusTextView.setActivated(true);
        emptyTextView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNetworkFailureHint() {
        statusTextView.setText(R.string.network_failure);
        statusTextView.setActivated(false);
        emptyTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToDetails(Uri uri) {
        new DetailsActivityArgs(uri)
                .launch(this);
        finish();
    }

    @Override
    public void showTemperature(String temp) {
        temperatureTextView.setVisibility(View.VISIBLE);
        temperatureTextView.setText(temp);
    }

    @Override
    public void showHumidity(String humidity) {
        humidityTextView.setVisibility(View.VISIBLE);
        humidityTextView.setText(humidity);
    }

    @Override
    public void showLocation(String location) {
        locationTextView.setVisibility(View.VISIBLE);
        locationTextView.setText(location);
    }

    @Override
    public void showCondition(String condition) {
        conditionTextView.setVisibility(View.VISIBLE);
        conditionTextView.setText(condition);
    }

    @Override
    public void hideTemperature() {
        temperatureTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideHumidity() {
        humidityTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLocation() {
        locationTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideCondition() {
        conditionTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showGPSWarning() {
        Toast.makeText(getApplicationContext(), getString(R.string.gps_warning), Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public WeatherConditionItem getItem(int position) {
        return presenter.getItem(position);
    }

    @Override
    public void onItemClicked(WeatherConditionItem item, boolean activated) {
        presenter.onItemClicked(item, activated);
    }

    @OnClick(R.id.btn_save)
    public void onSaveClicked() {
        presenter.onSaveClicked(captureBitmap());
    }

    @OnClick(R.id.tv_reload)
    public void onReloadClicked() {
        presenter.onReloadClicked();
    }

    private Bitmap captureBitmap() {
        Bitmap b = Bitmap.createBitmap(photoContainer.getWidth(), photoContainer.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        photoContainer.draw(c);
        return b;
    }
}
