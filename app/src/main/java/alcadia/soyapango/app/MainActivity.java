package alcadia.soyapango.app;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import alcadia.soyapango.app.Adapter.EventoSocialAdapter;
import alcadia.soyapango.app.Api.ApiEventoSocial;
import alcadia.soyapango.app.Model.EventoSocialResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements EventoSocialAdapter.ClickedItem {

    Toolbar toolbar;
    RecyclerView recyclerView;
    Menu menu;
    EventoSocialAdapter eventoSocialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);

        menu = findViewById(R.id.bottom_navigation);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        eventoSocialAdapter = new EventoSocialAdapter(this::ClickedUser);

        getAllEvents();


    }

    public void getAllEvents() {

        Call<List<EventoSocialResponse>> eventslist = ApiEventoSocial.getEventService().getAllEvents();

        eventslist.enqueue(new Callback<List<EventoSocialResponse>>() {
            @Override
            public void onResponse(Call<List<EventoSocialResponse>> call, Response<List<EventoSocialResponse>> response) {

                if (response.isSuccessful()) {
                    List<EventoSocialResponse> eventoSocialRespons = response.body();
                    eventoSocialAdapter.setData(eventoSocialRespons);
                    recyclerView.setAdapter(eventoSocialAdapter);

                }

            }

            @Override
            public void onFailure(Call<List<EventoSocialResponse>> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());

            }
        });
    }

    @Override
    public void ClickedUser(EventoSocialResponse eventoSocialResponse) {

        startActivity(new Intent(this, EvontoSocialDetailsActivity.class).putExtra("data", eventoSocialResponse));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.bottom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
