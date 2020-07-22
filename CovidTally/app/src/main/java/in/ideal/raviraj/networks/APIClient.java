package in.ideal.raviraj.networks;

import java.util.concurrent.TimeUnit;

import in.ideal.raviraj.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;
    private APIClient(){}
    public static synchronized Retrofit getClient() {

        if (retrofit==null) {

            String BASE_URL = Constants.BASE_URL;
            OkHttpClient client = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }





}
