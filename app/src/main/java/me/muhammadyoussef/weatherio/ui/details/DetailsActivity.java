package me.muhammadyoussef.weatherio.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.muhammadyoussef.weatherio.R;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_photo)
    ImageView photoImageView;

    private DetailsActivityArgs activityArgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        activityArgs = DetailsActivityArgs.deserializeFrom(getIntent());
        if (activityArgs.getPhotoUri() != null) {
            loadPhoto(activityArgs.getPhotoUri());
        }
    }

    public void loadPhoto(Uri uri) {
        Glide.with(this)
                .load(uri)
                .into(photoImageView);
    }

    @OnClick(R.id.iv_back)
    public void onUpArrowClicked() {
        finish();
    }

    @OnClick(R.id.iv_share)
    public void onShareButtonClicked() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, activityArgs.getPhotoUri());
        startActivity(Intent.createChooser(share, ""));
    }
}
