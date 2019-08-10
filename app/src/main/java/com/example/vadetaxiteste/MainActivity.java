package com.example.vadetaxiteste;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vadetaxiteste.Data.Remote.Contract.IWeatherEndPoint;
import com.example.vadetaxiteste.Data.Remote.ServiceGenerator;
import com.example.vadetaxiteste.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button buttonProcurar;
    private EditText editTextProcure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonProcurar = findViewById(R.id.buttonProcurar);
        editTextProcure = findViewById(R.id.editTextProcure);

        buttonProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextProcure == null || editTextProcure.getText().length() > 3) {
                    if (haveConection()) {
                        getWeather(editTextProcure.getText().toString());
                    }

                }

            }
        });
    }

    public boolean haveConection() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public void getWeather(String state) {

        IWeatherEndPoint iWeatherEndPoint = ServiceGenerator.createService(IWeatherEndPoint.class);

        Call<WeatherResponse> call = iWeatherEndPoint.getWeather();

        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                if(response.code() == 200){
                    Toast.makeText(MainActivity.this, "Deu certo",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Não encontrado",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Deu errado",Toast.LENGTH_SHORT).show();
            }
        });
    }

}

