package com.example.visitapp.adapter;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visitapp.database.entity.VisitorEntity;
import com.example.visitapp.database.entity.VisitEntity;
import com.example.visitapp.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

import com.example.visitapp.R;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        if (item.getClass().equals(VisitorEntity.class))
            holder.mTextView.setText(((VisitorEntity) item).toString());
        if (item.getClass().equals(VisitEntity.class))
            holder.mTextView.setText(((VisitEntity) item).getDescription());
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof VisitorEntity) {
                        return ((VisitorEntity) mData.get(oldItemPosition)).getVisitorId().equals(((VisitorEntity) data.get(newItemPosition)).getVisitorId());
                    }
                    if (mData instanceof VisitEntity) {
                        return ((VisitEntity) mData.get(oldItemPosition)).getVisitId().equals(
                                ((VisitEntity) data.get(newItemPosition)).getVisitId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof VisitorEntity) {
                        VisitorEntity newVisitor = (VisitorEntity) data.get(newItemPosition);
                        VisitorEntity oldVisitor = (VisitorEntity) mData.get(newItemPosition);
                        return newVisitor.getVisitorId().equals(oldVisitor.getVisitorId())
                                && Objects.equals(newVisitor.getFirstName(), oldVisitor.getFirstName())
                                && Objects.equals(newVisitor.getLastName(), oldVisitor.getLastName())
                                && newVisitor.getEmail().equals(oldVisitor.getEmail());
                    }
                    if (mData instanceof VisitEntity) {
                        VisitEntity newVisit = (VisitEntity) data.get(newItemPosition);
                        VisitEntity oldVisit = (VisitEntity) mData.get(newItemPosition);
                        return Objects.equals(newVisit.getVisitId(), oldVisit.getVisitId())
                                && Objects.equals(newVisit.getVisitDate(), oldVisit.getVisitDate())
                                && Objects.equals(newVisit.getDescription(), oldVisit.getDescription())
                                && Objects.equals(newVisit.getVisitor(), oldVisit.getVisitor())
                                && Objects.equals(newVisit.getHostId(), oldVisit.getHostId());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
