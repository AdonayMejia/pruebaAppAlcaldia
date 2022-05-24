package alcadia.soyapango.app.Interface;

import alcadia.soyapango.app.Model.EventoSocialResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EventoSocialService {

    @GET("api")
    Call<List<EventoSocialResponse>> getAllEvents();

}
