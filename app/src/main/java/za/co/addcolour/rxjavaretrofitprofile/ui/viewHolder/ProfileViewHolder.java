package za.co.addcolour.rxjavaretrofitprofile.ui.viewHolder;

import android.support.v7.widget.RecyclerView;

import za.co.addcolour.rxjavaretrofitprofile.databinding.RowItemProfileBinding;

public class ProfileViewHolder extends RecyclerView.ViewHolder {

    public RowItemProfileBinding mBinding;

    public ProfileViewHolder(RowItemProfileBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
}