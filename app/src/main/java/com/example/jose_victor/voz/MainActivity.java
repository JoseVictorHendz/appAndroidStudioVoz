package com.example.jose_victor.voz;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnFalar;
    TextView txtTextoCapturado;

    // criando id para invocação do texto capturado
    private final int Id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // criando os objetos
        btnFalar = (Button) findViewById(R.id.btnFalar);
        txtTextoCapturado = (TextView) findViewById(R.id.txtFalar);

        btnFalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // criando uma intente de voz, como o uso de captura de audio é nativo do android não é necessario nenhuma permissão
                Intent iVoz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                /* setando como tratara o tempo de duração da espera de captura do audio, neste caso
                * LANGUAGE_MODEL_FREE_FORM está dizendo que será no modo livre, recebera qualquer palavra dentro de um tempo,
                * que se não determinado deve ficar em mais o menos 5 segundos*/
                iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                /* setando o tipo de lingua que o captor de voz deve converter,
                *  neste caso Locale.getDefault() esta pegando a linguagem do smartphone*/
                iVoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                // Executando na tela o prompt de captura de voz
                iVoz.putExtra(RecognizerIntent.EXTRA_PROMPT, "fale algo normalmente");

                try {
                    // enviando como parametro a intente que caturou o audio e o id
                    startActivityForResult(iVoz, Id);

                } catch (ActivityNotFoundException err) {
                    Toast.makeText(getApplicationContext(), "Seu aparelho não possui compatibilidade com comandos de voz", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int id, int resultCodeID, Intent dados) {
        switch (id) {
            case Id:
                if(resultCodeID == RESULT_OK && null != dados) {
                    // pegando os resultados atraves do EXTRA_RESULTS e adicionando em um array
                    ArrayList<String> result = dados
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // adicionando o resultado em uma string
                    String ditado = result.get(0);

                    Toast.makeText(getApplicationContext(), ditado, Toast.LENGTH_SHORT).show();

                    // passando o texto capturado para o TextView
                    txtTextoCapturado.setText(ditado);
                }
            break;

        }
    }
}
