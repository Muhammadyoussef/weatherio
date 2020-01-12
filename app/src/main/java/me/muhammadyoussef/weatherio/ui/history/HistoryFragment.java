package me.muhammadyoussef.weatherio.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.muhammadyoussef.weatherio.R;
import me.muhammadyoussef.weatherio.di.ComponentProvider;
import me.muhammadyoussef.weatherio.di.activity.ActivityComponent;
import me.muhammadyoussef.weatherio.di.fragment.FragmentModule;
import me.muhammadyoussef.weatherio.ui.details.DetailsActivityArgs;
import me.muhammadyoussef.weatherio.ui.history.data.FileViewModel;

public class HistoryFragment extends Fragment implements HistoryContract.View, HistoryDataOwner {

    @BindView(R.id.rv_history)
    RecyclerView recyclerView;

    @Inject
    HistoryAdapter adapter;
    @Inject
    HistoryPresenter presenter;

    public static Fragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ComponentProvider) {
            ComponentProvider provider = ((ComponentProvider) context);
            if (provider.getComponent() instanceof ActivityComponent) {
                ((ActivityComponent) provider.getComponent())
                        .plus(new FragmentModule(this))
                        .inject(this);
            } else {
                throw new IllegalStateException("Component must be " + ActivityComponent.class.getName());
            }
        } else {
            throw new IllegalStateException("host context must implement " + ComponentProvider.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        presenter.onViewCreated();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public FileViewModel getItem(int position) {
        return presenter.getItem(position);
    }

    @Override
    public void onItemClicked(FileViewModel item) {
        if (getContext() != null) {
            new DetailsActivityArgs(item.getThumbnail())
                    .launch(getContext());
        }
    }

    @Override
    public void updateUi(DiffUtil.DiffResult result) {
        result.dispatchUpdatesTo(adapter);
    }
}
