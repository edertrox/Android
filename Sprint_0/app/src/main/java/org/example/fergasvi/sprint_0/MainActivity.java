package org.example.fergasvi.sprint_0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText edtValor, edtLatitud, edtLongitud, edtFecha;
    Button btnAgregar, btnGenerarMedicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtValor = (EditText) findViewById(R.id.edtValor);
        edtLatitud = (EditText) findViewById(R.id.edtLatitud);
        edtLongitud = (EditText) findViewById(R.id.edtLongitud);
        edtFecha = (EditText) findViewById(R.id.edtFecha);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnGenerarMedicion = (Button) findViewById(R.id.btnGenerarMedicion);

        btnGenerarMedicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtValor.setText("" + new Random().nextInt(256), TextView.BufferType.EDITABLE);
                edtLatitud.setText("" + new Random().nextInt(51), TextView.BufferType.EDITABLE);
                edtLongitud.setText("" + new Random().nextInt(51), TextView.BufferType.EDITABLE);
                edtFecha.setText("2021-" + new Random().nextInt(13) + "-" + new Random().nextInt(19), TextView.BufferType.EDITABLE);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postMedicion("http://0d06-84-125-125-136.ngrok.io/Backend/post-mediciones.php");
            }
        });
    }

private void postMedicion(String URL){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response){
            Toast.makeText(getApplicationContext(), "OPERACION ESITOSA", Toast.LENGTH_SHORT).show();
        }
    }, new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }

    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError{
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("valor", edtValor.getText().toString());
            parametros.put("latitud", edtLatitud.getText().toString());
            parametros.put("longitud", edtLongitud.getText().toString());
            parametros.put("fecha", edtFecha.getText().toString());
            return parametros;
        }
    };
    RequestQueue requestQueue= Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}



}