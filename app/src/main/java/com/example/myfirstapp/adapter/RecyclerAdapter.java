package com.example.myfirstapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.database.entity.PersonEntity;
import com.example.myfirstapp.database.entity.VisitEntity;
import com.example.myfirstapp.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

import com.example.myfirstapp.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private List<PersonEntity> data;
    private List<VisitEntity> visitData;
    private RecyclerViewItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonEntity item = data.get(position);
        holder.textView.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(final List<PersonEntity> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    if (RecyclerAdapter.this.data instanceof PersonEntity) {
                        return (RecyclerAdapter.this.data.get(oldItemPosition)).getEmail().equals(
                                (data.get(newItemPosition)).getEmail());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter.this.data instanceof PersonEntity) {
                        PersonEntity newPerson = data.get(newItemPosition);
                        PersonEntity oldPerson = RecyclerAdapter.this.data.get(newItemPosition);
                        return Objects.equals(newPerson.getEmail(), oldPerson.getEmail())
                                && Objects.equals(newPerson.getFirstName(), oldPerson.getFirstName())
                                && Objects.equals(newPerson.getLastName(), oldPerson.getLastName());
                    }
                    return false;
                }
            });
            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }

    public void setVisitData(final List<VisitEntity> data) {
        if (this.visitData == null) {
            this.visitData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapter.this.visitData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                    if (RecyclerAdapter.this.visitData instanceof VisitEntity) {
                        return (RecyclerAdapter.this.visitData.get(oldItemPosition)).getIdVisit().equals(
                                (data.get(newItemPosition)).getIdVisit());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter.this.visitData instanceof VisitEntity) {
                        VisitEntity newVisit = data.get(newItemPosition);
                        VisitEntity oldVisit = RecyclerAdapter.this.visitData.get(newItemPosition);
                        return Objects.equals(newVisit.getDescription(), oldVisit.getDescription())
                                && Objects.equals(newVisit.getVisitor(), oldVisit.getVisitor())
                                && Objects.equals(newVisit.getEmployee(), oldVisit.getEmployee())
                                & Objects.equals(newVisit.getVisitDate(), oldVisit.getVisitDate());
                    }
                    return false;
                }
            });
            this.visitData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
