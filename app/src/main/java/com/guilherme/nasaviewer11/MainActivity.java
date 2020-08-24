package com.guilherme.nasaviewer11;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.guilherme.nasaviewer11.adapter.RecyclerAdapter;
import com.guilherme.nasaviewer11.api.RequisicaoFoto;
import com.guilherme.nasaviewer11.api.Respostalmagem;
import com.guilherme.nasaviewer11.model.Foto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Respostalmagem {

    private RequisicaoFoto requisicaoFoto;

    private ArrayList<Foto> fotos;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter adapter;

    private GridLayoutManager gridlayoutmanager;

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu_main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.acao_mudar_manager) {
            mudarLayoutManager();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void mudarLayoutManager() {
        if (recyclerView.getLayoutManager().equals(linearLayoutManager)) {
            recyclerView.setLayoutManager(gridlayoutmanager);

            if (fotos.size() == 1) {
                requisicaoFoto.getFoto();
            }

        } else {
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        gridlayoutmanager = new GridLayoutManager(this, 2);
        fotos = new ArrayList<>();
        adapter = new RecyclerAdapter(fotos);
        recyclerView.setAdapter(adapter);

        requisicaoFoto = new RequisicaoFoto(this);

        setRecyclerViewScrollListener();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onStart() {
        super.onStart();

        if (fotos.size() == 0) {
            requisicaoFoto.getFoto();
        }
    }

    @Override
    public void receberNovaFoto(Foto foto) {

        fotos.add(foto);
        adapter.notifyItemInserted(fotos.size());
    }

    private int getUltimoItemVisivel() {
        int itemCount;

        if ((recyclerView.getLayoutManager().equals(linearLayoutManager))) {
            itemCount = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        } else {
            itemCount = gridlayoutmanager.findFirstCompletelyVisibleItemPosition();
        }

        return itemCount;
    }

    private void setRecyclerViewScrollListener() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int totalItemCount = recyclerView.getLayoutManager().getItemCount();

                if (!requisicaoFoto.isLoadingDado() && totalItemCount == getUltimoItemVisivel() + 1) {
                    requisicaoFoto.getFoto();
                }

            }

        });

    }

    private void setRecyclerViewItemTouchListener() {

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                fotos.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}