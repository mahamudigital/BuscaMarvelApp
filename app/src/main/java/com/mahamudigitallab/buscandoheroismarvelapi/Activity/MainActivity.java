package com.mahamudigitallab.buscandoheroismarvelapi.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahamudigitallab.buscandoheroismarvelapi.Adapter.RvButtonAdapter;
import com.mahamudigitallab.buscandoheroismarvelapi.Adapter.RvHerosAdapter;
import com.mahamudigitallab.buscandoheroismarvelapi.Api.HeroServices;
import com.mahamudigitallab.buscandoheroismarvelapi.Fragment.FragmentList;
import com.mahamudigitallab.buscandoheroismarvelapi.Model.Character;
import com.mahamudigitallab.buscandoheroismarvelapi.Model.ReturnData;
import com.mahamudigitallab.buscandoheroismarvelapi.R;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvHeros;
    RvHerosAdapter herosAdapter;
    List<Character> listHeros = new ArrayList<>();
    List<Character> listHerosFilter = new ArrayList<>();
    Retrofit retrofit;
    List<FragmentList> fragments = new ArrayList<FragmentList>();
    ImageButton buttonBack, buttonNext;
    EditText edtbusca;
    RecyclerView rvButtons;
    List<Button> listButtonPage = new ArrayList<Button>();
    RvButtonAdapter rvButtonAdapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtbusca = (EditText) findViewById(R.id.edtBusca);
        edtbusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!edtbusca.getText().equals("")){
                    listHerosFilter.clear();
                    for (int i = 0; i < listHeros.size(); i++) {
                        Character character = listHeros.get(i);
                        if (character.getName().toLowerCase().startsWith(edtbusca.getText().toString().toLowerCase())){
                            listHerosFilter.add(character);
                        }
                    }

                    setComponents(listHerosFilter);
                }else{
                    getApiResponse();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonBack = (ImageButton) findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(false);

            }
        });
        buttonNext = (ImageButton) findViewById(R.id.btnNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(true);

            }
        });
        getApiResponse();

    }

    private void getApiResponse() {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com:443/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HeroServices heroServices = retrofit.create(HeroServices.class);

        String ts = String.valueOf(System.currentTimeMillis());

        String hash = getHach(ts);

        Call<ReturnData> call = heroServices.getHeros(ts,getString(R.string.public_Key),hash);

        call.enqueue(new Callback<ReturnData>(){
            @Override
            public void onResponse(Call<ReturnData> call, Response<ReturnData> response) {
                if(response.isSuccessful()){
                    Log.d("response", response.body().toString());
                    ReturnData data = response.body();

                    Log.d("response",  data.data.results.get(1).getDescription());
                    listHeros = data.data.results;
                    setComponents(listHeros);
                }

            }

            @Override
            public void onFailure(Call<ReturnData> call, Throwable t) {
                Log.d("Response", t.getMessage() );

            }
        });

    }
    @SuppressLint("ResourceAsColor")
    public void setComponents(List<Character> listItems){
        fragments.clear();
        int countFragment = listItems.size() /4;
        if (countFragment <= 0){
            countFragment = listItems.size();
        }

        for (int i = 0; i < countFragment; i ++) {
            FragmentList fragmentList = new FragmentList();
            fragmentList.position = i;


            int startCount = ((i+1) * 4) - 4;

            if (startCount < 0){
                startCount = 0;
            }

            int endCount = startCount + 5;
            if (endCount > listItems.size()){
                endCount = listItems.size();
            }
            for (int j = startCount; j < endCount; j++ ){
                fragmentList.list.add(listItems.get(j));
            }
            Button button = new Button(this);
            if (i == 0) {
                button.setSelected(true);
            }

            listButtonPage.add(button);
            fragments.add(fragmentList);
        }
        if (fragments.size() == 0){
            FragmentList fragmentList = new FragmentList();
            fragments.add(fragmentList);
            Button button = new Button(this);
            button.setSelected(true);
            listButtonPage.add(button);
        }

        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentList fragmentList = fragments.get(0);
        transaction.replace(R.id.frameLayoutFragment,fragmentList );
        transaction.commit();

        rvButtons = (RecyclerView) findViewById(R.id.rvButtonsPage);

        rvButtonAdapter = new RvButtonAdapter(this,listButtonPage);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvButtons.setLayoutManager(layoutManager);
        rvButtons.setHasFixedSize(true);

        rvButtonAdapter.setOnItemClickListener(new RvButtonAdapter.HolderButton.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeFragmentIndex(position);
            }
        });

        rvButtons.setAdapter(rvButtonAdapter);

    }
    public String getHach(String ts){
        String publicKey = getString(R.string.public_Key);
        String privateKey = getString(R.string.private_key);
        String str = ts+privateKey+publicKey;

        return new String(Hex.encodeHex(DigestUtils.md5(str))); //DigestUtils.md5Hex(str);

    }
    public void changeFragment(boolean next){
        int position = getCurrentFragment(next);
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentList fragmentList = fragments.get(position);
        transaction.replace(R.id.frameLayoutFragment,fragmentList );
        transaction.commit();
        rvButtons.scrollToPosition(position);

    }
    public void changeFragmentIndex(int index){

        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentList fragmentList = fragments.get(index);
        transaction.replace(R.id.frameLayoutFragment,fragmentList );
        transaction.commit();
    }
    public int getCurrentFragment(boolean next){
        androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            List<androidx.fragment.app.Fragment> fragmentsAux = fm.getFragments();
            if (fragments != null) {
                for(int i = fragmentsAux.size() - 1; i >= 0; i--){
                    FragmentList fragmentList = (FragmentList) fragmentsAux.get(i);
                    if(fragmentList != null) {
                        if(fragmentList instanceof FragmentList) {
                            if (next){
                                if ((fragmentList.position + 1) > fragments.size() - 1) {
                                    return fragmentList.position;
                                } else {
                                    return fragmentList.position + 1;
                                }
                            }else{
                                if ((fragmentList.position - 1) < 0) {
                                    return 0;
                                } else {
                                    return fragmentList.position - 1;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return 0;
    }
}