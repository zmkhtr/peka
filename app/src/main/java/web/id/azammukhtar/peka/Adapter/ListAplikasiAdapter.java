package web.id.azammukhtar.peka.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import web.id.azammukhtar.peka.Model.Aplikasi;
import web.id.azammukhtar.peka.R;

public class ListAplikasiAdapter extends RecyclerView.Adapter<ListAplikasiAdapter.ViewHolder> {

    private List<Aplikasi> mAplikasi = new ArrayList<>();
    private OnViewClick listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_aplikasi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Aplikasi aplikasi = mAplikasi.get(position);
        holder.lableAplikasi.setText(aplikasi.getNamaAplikasi());
        holder.iconAplikasi.setImageDrawable(aplikasi.getIconAplikasi());
        holder.statusAplikasi.setImageResource(aplikasi.getLocked());
    }

    @Override
    public int getItemCount() {
        return mAplikasi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lableAplikasi;
        private ImageView iconAplikasi, statusAplikasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lableAplikasi = itemView.findViewById(R.id.textListAplikasiLable);
            iconAplikasi = itemView.findViewById(R.id.imageListAplikasiIcon);
            statusAplikasi = itemView.findViewById(R.id.imageListAplikasiStatus);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onViewClick(getAdapterPosition());
//                }
//            });
        }
    }

    public void setAplikasiList(List<Aplikasi> aplikasiList){
        this.mAplikasi = aplikasiList;
        notifyDataSetChanged();
    }

    public void clearList(List<Aplikasi> aplikasiList) {
        this.mAplikasi = aplikasiList;
        aplikasiList.clear();
    }


    public interface OnViewClick {
        void onViewClick(int position);
    }

    public void setOnItemClickListener(OnViewClick listener) {
        this.listener = listener;
    }
}
