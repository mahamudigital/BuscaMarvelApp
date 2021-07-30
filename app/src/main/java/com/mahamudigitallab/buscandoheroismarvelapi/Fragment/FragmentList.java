package com.mahamudigitallab.buscandoheroismarvelapi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahamudigitallab.buscandoheroismarvelapi.Activity.HeroDetailActivity;
import com.mahamudigitallab.buscandoheroismarvelapi.Adapter.RvHerosAdapter;
import com.mahamudigitallab.buscandoheroismarvelapi.Model.Character;
import com.mahamudigitallab.buscandoheroismarvelapi.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {
    RecyclerView recyclerView;
    public List<Character> list = new ArrayList<Character>();
    public int position;
    RvHerosAdapter herosAdapter;

    public FragmentList(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_list, container,false);

        recyclerView =(RecyclerView) view.findViewById(R.id.rvHeros1);

        herosAdapter = new RvHerosAdapter(getContext(), list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(herosAdapter);

        herosAdapter.setOnItemClickListener(new RvHerosAdapter.HerosVH.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Character character = list.get(position);
                Intent i = new Intent (getContext(), HeroDetailActivity.class);
                i.putExtra("hero", character);
                startActivity(i);

            }
        });

        return view;
    }
}
