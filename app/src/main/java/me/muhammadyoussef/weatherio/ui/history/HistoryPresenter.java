package me.muhammadyoussef.weatherio.ui.history;

import android.util.Pair;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.recyclerview.widget.DiffUtil;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import me.muhammadyoussef.weatherio.di.scope.FragmentScope;
import me.muhammadyoussef.weatherio.schedulers.ThreadSchedulers;
import me.muhammadyoussef.weatherio.schedulers.qualifires.IOThread;
import me.muhammadyoussef.weatherio.ui.history.data.FileMapper;
import me.muhammadyoussef.weatherio.ui.history.data.FileViewModel;
import me.muhammadyoussef.weatherio.utils.DiskUtils;
import me.muhammadyoussef.weatherio.utils.diff.HistoryDiffUtilCallback;
import timber.log.Timber;

@FragmentScope
class HistoryPresenter implements HistoryContract.Presenter {

    private final BehaviorRelay<List<FileViewModel>> filesRelay;
    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposables;
    private final HistoryContract.View view;
    private final FileMapper fileMapper;
    private final DiskUtils diskUtils;

    @Inject
    HistoryPresenter(@IOThread ThreadSchedulers threadSchedulers,
                     HistoryContract.View view,
                     FileMapper fileMapper,
                     DiskUtils diskUtils) {
        this.filesRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.disposables = new CompositeDisposable();
        this.threadSchedulers = threadSchedulers;
        this.fileMapper = fileMapper;
        this.diskUtils = diskUtils;
        this.view = view;
    }

    @Override
    public void onResume() {
        disposables.add(Single.fromCallable(diskUtils::getAttachmentsDirectory)
                .map(File::listFiles)
                .filter(Objects::nonNull)
                .map(fileMapper::toViewModels)
                .map(files -> new Pair<>(filesRelay.getValue(), files))
                .map(HistoryDiffUtilCallback::new)
                .map(historyDiffUtilCallback -> new Pair<>(DiffUtil.calculateDiff(historyDiffUtilCallback), historyDiffUtilCallback.getNewItems()))
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(diffResultListPair -> {
                    view.updateUi(diffResultListPair.first);
                    filesRelay.accept(diffResultListPair.second);
                }, Timber::e));
    }

    @Override
    public int getItemCount() {
        return filesRelay.getValue().size();
    }

    @Override
    public FileViewModel getItem(int position) {
        return filesRelay.getValue().get(position);
    }
}
