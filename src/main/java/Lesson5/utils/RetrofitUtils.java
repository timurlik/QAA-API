package Lesson5.utils;


import lombok.experimental.UtilityClass;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


@UtilityClass
public class RetrofitUtils {

    public Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(ConfigUtil.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
