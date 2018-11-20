package za.co.addcolour.rxjavaretrofitprofile.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import za.co.addcolour.rxjavaretrofitprofile.R;
import za.co.addcolour.rxjavaretrofitprofile.databinding.RowItemProfileBinding;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;
import za.co.addcolour.rxjavaretrofitprofile.ui.viewHolder.ProfileViewHolder;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileViewHolder> {

    private List<? extends Profile> mProfileList;

    public void setProfileList(final List<? extends Profile> profileList) {
        if (mProfileList == null) {
            mProfileList = profileList;
            notifyItemRangeInserted(0, profileList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProfileList.size();
                }

                @Override
                public int getNewListSize() {
                    return profileList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mProfileList.get(oldItemPosition).getId() == profileList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Profile newProfile = profileList.get(newItemPosition);
                    Profile oldProfile = mProfileList.get(oldItemPosition);
                    return Objects.equals(newProfile.getId(), oldProfile.getId());
                }
            });
            mProfileList = profileList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemProfileBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.row_item_profile,
                        parent, false);
        return new ProfileViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        holder.mBinding.setData(mProfileList.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProfileList == null ? 0 : mProfileList.size();
    }
}