package com.mahamudigitallab.buscandoheroismarvelapi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahamudigitallab.buscandoheroismarvelapi.R;

import java.util.ArrayList;
import java.util.List;

public class RvButtonAdapter extends RecyclerView.Adapter<RvButtonAdapter.HolderButton> {
    private Context context;
    private List<Button>listButtons = new ArrayList<Button>();
    private static RvButtonAdapter.HolderButton.ItemClickListener itemClickListener;

    public  RvButtonAdapter(Context ctx, List<Button> list){
        this.context = ctx;
        this.listButtons =list;
    }

    @NonNull

    @Override
    public RvButtonAdapter.HolderButton onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_pages, parent, false);
        return new HolderButton(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderButton holderButton, int i) {
        holderButton.btnPage.setText(String.valueOf(i+1));
    }

    @Override
    public int getItemCount() { return listButtons.size(); }

    public void setOnItemClickListener(RvButtonAdapter.HolderButton.ItemClickListener itemClickListener){
        this.itemClickListener = (HolderButton.ItemClickListener) itemClickListener;

    }

    public static class HolderButton extends RecyclerView.ViewHolder implements View.OnClickListener{
        Button btnPage;

        public HolderButton(View itemView){
            super(itemView);
            btnPage = itemView.findViewById(R.id.btnPage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (itemClickListener != null){
                itemClickListener.onItemClick(getAdapterPosition());
            }

        }
        public interface ItemClickListener {

            void onItemClick(int position);
        }
    }
}
