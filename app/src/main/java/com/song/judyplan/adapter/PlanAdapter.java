package com.song.judyplan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.song.judyplan.R;
import com.song.judyplan.entity.Plan;

import java.util.List;

/**
 * Created by Judy on 2017/4/22.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private Context mContext;
    private List<Plan> mPlanList;
    private OnItemClickListener mOnItemClickListener;

    public PlanAdapter(Context context) {
        mContext = context;
    }

    static class PlanViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox mCheckBox;
        private final TextView mTextView;

        public PlanViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_completed);
            mTextView = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.list_item_plan, parent, false);
        return new PlanViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(PlanViewHolder holder, int position) {
        final Plan plan = mPlanList.get(position);
        holder.mCheckBox.setChecked(plan.getIsCompleted());
        holder.mTextView.setText(plan.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(plan);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlanList == null ? 0 : mPlanList.size();
    }

    public void setPlanList(List<Plan> planList) {
        mPlanList = planList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Plan plan);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
