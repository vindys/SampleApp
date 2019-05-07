package com.vindys.sampleapp.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.vindys.sampleapp.R;
import com.vindys.sampleapp.databinding.ActivityItemViewBinding;
import com.vindys.sampleapp.databinding.DateItemViewBinding;
import com.vindys.sampleapp.viewmodel.SampleAppViewModel;

import java.util.List;


public class ActivityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<? extends ListItem> activityList;
    SampleAppViewModel viewModel;

    public ActivityRecyclerViewAdapter(SampleAppViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setActivityList(final List<? extends ListItem> activityList) {
        if (this.activityList == null) {
            this.activityList = activityList;
            notifyItemRangeInserted(0, activityList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ActivityRecyclerViewAdapter.this.activityList.size();
                }

                @Override
                public int getNewListSize() {
                    return activityList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ActivityRecyclerViewAdapter.this.activityList.get(oldItemPosition).equals(
                            activityList.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ListItem activityLog = activityList.get(newItemPosition);
                    ListItem old = activityList.get(oldItemPosition);
                    return activityLog.equals(old);
                }
            });
            this.activityList = activityList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case ListItem.TYPE_GENERAL:
                ActivityItemViewBinding binding = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_item_view,
                                parent, false);

                viewHolder = new ActivityLogViewHolder(binding);
                break;
            case ListItem.TYPE_DATE:
                DateItemViewBinding binding1 = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.date_item_view,
                                parent, false);
                viewHolder = new DateViewHolder(binding1);
                break;

        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return activityList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            DateViewHolder dateViewHolder = (DateViewHolder) holder;
            dateViewHolder.binding.executePendingBindings();
            dateViewHolder.binding.setDateValue(((DateItem) activityList.get(position)).getDate());
        } else if (holder instanceof ActivityLogViewHolder) {
            ActivityLogViewHolder activityLogViewHolder = (ActivityLogViewHolder) holder;
            activityLogViewHolder.binding.executePendingBindings();
            activityLogViewHolder.binding.setActivityLog(((ActivityLogItem) activityList.get(position)).getActivityLog());
        }
    }

    @Override
    public int getItemCount() {
        return activityList == null ? 0 : activityList.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {

        final DateItemViewBinding binding;

        DateViewHolder(DateItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    static class ActivityLogViewHolder extends RecyclerView.ViewHolder {

        final ActivityItemViewBinding binding;

        ActivityLogViewHolder(ActivityItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
