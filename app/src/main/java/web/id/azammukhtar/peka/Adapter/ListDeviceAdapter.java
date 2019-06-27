package web.id.azammukhtar.peka.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import web.id.azammukhtar.peka.Model.UserDevice;
import web.id.azammukhtar.peka.R;

public class ListDeviceAdapter extends RecyclerView.Adapter<ListDeviceAdapter.ViewHolder> {

    private List<UserDevice> mDevice = new ArrayList<>();
    private OnViewClick listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_device,parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDevice userDevice = mDevice.get(position);
        holder.deviceBrand.setText(userDevice.getHandphoneBrand());
        holder.deviceModel.setText(userDevice.getHandphoneModel());
    }

    @Override
    public int getItemCount() {
        return mDevice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView deviceBrand, deviceModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceBrand = itemView.findViewById(R.id.textListDeviceBrand);
            deviceModel = itemView.findViewById(R.id.textListDeviceModel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onViewClick(getAdapterPosition());
                }
            });
        }
    }

    public void setDeviceList(List<UserDevice> userDevices){
        this.mDevice = userDevices;
        notifyDataSetChanged();
    }

    public void clearList(List<UserDevice> userDevices) {
        this.mDevice = userDevices;
        userDevices.clear();
    }


    public interface OnViewClick {
        void onViewClick(int position);
    }

    public void setOnItemClickListener(ListDeviceAdapter.OnViewClick listener) {
        this.listener = listener;
    }
}
