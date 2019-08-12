package com.example.vadetaxiteste;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vadetaxiteste.Data.Remote.Contract.IWeatherEndPoint;
import com.example.vadetaxiteste.Data.Remote.ServiceGenerator;
import com.example.vadetaxiteste.Model.WeatherResponse;
import com.example.vadetaxiteste.fragment.TempoFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button buttonSearch, buttonConfirmCheck;
    private EditText editTextSearch;
    private String AppId = "18255c9fb107f943bad5ae65743d3aea";
    private String Unit = "metric";
    private String Lang = "pt";
    private Dialog dialog;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSearch = findViewById(R.id.buttonSearch);
        editTextSearch = findViewById(R.id.editTextSearch);
        progressBar = findViewById(R.id.progressBar);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSearch == null || editTextSearch.getText().length() > 3) {
                    editTextSearch.setError("O campo não pode estar vazio e deve ser maior que 3 caracteres!");
                    showProgress(true);
                    if (haveConection()) {
                        getWeather(editTextSearch.getText().toString());
                    } else {
                        showProgress(false);
                        showDialogAlertNotInternet();
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

        Call<WeatherResponse> call = iWeatherEndPoint.getWeather(state, AppId, Unit, Lang);

        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                if (response.code() == 200) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("response", response.body());

                    TempoFragment fragment = new TempoFragment();
                    fragment.setArguments(bundle);

                    // Pega o FragmentManager
                    FragmentManager fm = getSupportFragmentManager();

                    // Substitui um Fragment
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_content, fragment);
                    ft.commit();
                    showProgress(false);

                } else {
                    showProgress(false);
                    showDialogAlertNotFound();

                }

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                showProgress(false);
                showDialogError();
            }
        });
    }

    public void showDialogAlertNotFound() {
        dialog = new Dialog(this, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        TextView textView = dialog.findViewById(R.id.textViewCheck);

        textView.setText("Não foi possível encontrar um resultado para a sua busca!");
        textView.getGravity();
        textView.setTextColor(getResources().getColor(R.color.colorText));
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();

        buttonConfirmCheck = dialog.findViewById(R.id.button_dialog_ckeck);
        buttonConfirmCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showDialogAlertNotInternet() {
        dialog = new Dialog(this, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        TextView textView = dialog.findViewById(R.id.textViewCheck);

        textView.setText("Não foi possível conectar a internet!");
        textView.getGravity();
        textView.setTextColor(getResources().getColor(R.color.colorText));
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();

        buttonConfirmCheck = dialog.findViewById(R.id.button_dialog_ckeck);
        buttonConfirmCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void showDialogError() {
        dialog = new Dialog(this, R.style.CustomAlertDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_alert);
        TextView textView = dialog.findViewById(R.id.textViewCheck);

        textView.setText("Erro. Tente novamente!");
        textView.getGravity();
        textView.setTextColor(getResources().getColor(R.color.colorText));
        dialog.setCancelable(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();

        buttonConfirmCheck = dialog.findViewById(R.id.button_dialog_ckeck);
        buttonConfirmCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showProgress(boolean show){
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}


