package com.salvador.calculadoraip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText ip, mascara, idnet, broadcast, canip, parnet, parhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaración de variables de las cajas de texto.

        ip = findViewById(R.id.edit_text_ip);
        mascara = findViewById(R.id.edit_text_mask);
        idnet = findViewById(R.id.edit_text_netid);
        broadcast = findViewById(R.id.edit_text_broad);
        canip = findViewById(R.id.edit_text_canip);
        parnet = findViewById(R.id.edit_text_netp);
        parhost = findViewById(R.id.edit_text_hostp);

    }

    //Metodo para calcular
    public void calcular(View v){
        //Cadena que separa la string por punto
        String[] ip_separada = ip.getText().toString().split("\\.");

        //variables para los calculos.
        long rip =0;
        long rmask = 0;
        long invertmask = 0;
        int mascaraint = Integer.parseInt(mascara.getText().toString());

        //validar que tenga todos los octetos
        if(ip_separada.length != 4) return;

        //ip binaria
        for(int i=3; i>=0; i--) {
            rip |= (Long.parseLong(ip_separada[3-i])) << (i*8);
        }

        //mascara binaria
        for(int i=1; i <= mascaraint; i++) {
            rmask |= 1L << (32-i);
        }

        //negar la mascara para la operacion binaria
        invertmask = ~rmask;

        idnet.setText(longToIP(rip & rmask));
        broadcast.setText(longToIP(rip | invertmask));

        canip.setText((int)(Math.pow(2, (double)(32-mascaraint))) -2 + "");
        parnet.setText(mascaraint + "");
        parhost.setText((32-mascaraint) + "");

    }


    //Metodo para pasar el numero binario a IP
    public String longToIP(long ip){
        String st="";

        for(int i=3; i>=0; i--) {
            st += (0b1111_1111 & (ip >> (i*8) )) + (i!=0? ".": "");
        }

        return st;
    }


    public void reset(View v){
        ip.setText("");
        ip.requestFocus();
        mascara.setText("");
        idnet.setText("");
        broadcast.setText("");
        canip.setText("");
        parnet.setText("");
        parhost.setText("");
    }
}
