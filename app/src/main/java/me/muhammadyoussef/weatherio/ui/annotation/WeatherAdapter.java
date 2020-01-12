package me.muhammadyoussef.weatherio.ui.annotation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.di.qualifier.ForActivity;
import me.muhammadyoussef.weatherio.di.scope.ActivityScope;
import me.muhammadyoussef.weatherio.store.model.weather.WeatherConditionItem;

@ActivityScope
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final WeatherDataOwner dataOwner;

    @Inject
    WeatherAdapter(@ForActivity Context context, WeatherDataOwner dataOwner) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataOwner = dataOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_weather_info;
        ConstraintLayout root = new ConstraintLayout(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(layout, root, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(dataOwner.getItem(position), dataOwner);
    }

    @Override
    public int getItemCount() {
        return dataOwner.getItemCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView nameTextView;
        @BindView(R.id.iv_state)
        ImageView checkImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(WeatherConditionItem weatherConditionItem, WeatherDataOwner dataOwner) {
            nameTextView.setText(weatherConditionItem.getName());
            itemView.setOnClickListener(view -> onItemClicked(weatherConditionItem, dataOwner));
        }

        private void onItemClicked(WeatherConditionItem item, WeatherDataOwner dataOwner) {
            checkImageView.setActivated(!checkImageView.isActivated());
            dataOwner.onItemClicked(item, checkImageView.isActivated());
        }
    }
}
