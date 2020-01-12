package me.muhammadyoussef.weatherio.ui.history;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.di.qualifier.ForFragment;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.ui.history.data.FileViewModel;

@FragmentScope
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final HistoryDataOwner dataOwner;

    @Inject
    HistoryAdapter(@ForFragment Context context, HistoryDataOwner dataOwner) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataOwner = dataOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_history;
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
        @BindView(R.id.tv_date)
        TextView dateTextView;
        @BindView(R.id.iv_thumbnail)
        ImageView thumbnailImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(FileViewModel item, HistoryDataOwner dataOwner) {
            if (getAdapterPosition() == 0) {
                applyMargins();
            }
            nameTextView.setText(item.getName());
            dateTextView.setText(item.getDate());
            Glide.with(itemView.getContext())
                    .load(item.getThumbnail())
                    .into(thumbnailImageView);
            itemView.setOnClickListener(v -> dataOwner.onItemClicked(item));
        }

        private void applyMargins() {
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(layoutParams.width, layoutParams.height);
            int margin = (int) (16 * Resources.getSystem().getDisplayMetrics().density);
            newParams.setMargins(margin, margin, margin, margin);
            itemView.setLayoutParams(newParams);
        }
    }
}
