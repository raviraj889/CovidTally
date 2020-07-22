package in.ideal.raviraj.networks;

import in.ideal.raviraj.models.ResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("summary")
    Call<ResponseModel> GetSummary();

}



