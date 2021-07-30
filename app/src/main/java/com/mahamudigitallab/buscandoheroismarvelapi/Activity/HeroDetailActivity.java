package com.mahamudigitallab.buscandoheroismarvelapi.Activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mahamudigitallab.buscandoheroismarvelapi.R;
import com.mahamudigitallab.buscandoheroismarvelapi.Model.Character;
import com.squareup.picasso.Picasso;

public class HeroDetailActivity extends AppCompatActivity {

    private ImageView ivhero;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvModified;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_detail);

        Character character = (Character) getIntent().getExtras().getSerializable("hero");

        ivhero = (ImageView)findViewById(R.id.ivhero);
        tvName =(TextView)findViewById(R.id.tvName);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvModified = (TextView)findViewById(R.id.tvModified);

        Picasso.with(this).load(character.getThumbnail().getPath()+"/standard_medium."+character.getThumbnail().getExtension()).into(ivhero);
        tvName.setText("Nome: " +character.getName());
        tvDescription.setText("Descrição: " +character.getDescription());
        tvModified.setText("Modificado em: " + character.getModified());
    }
}
