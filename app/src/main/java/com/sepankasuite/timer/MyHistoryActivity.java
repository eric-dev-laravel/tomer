package com.sepankasuite.timer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MyHistoryActivity extends AppCompatActivity {

    //Variable de instancia de clase de manejo en la BD
    DataBaseManager manager;
    String msgError;
    Cursor cursor;

    ArrayList<DataHistory> dataModels;
    ListView listView;
    private static CustomAdapterHistory adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Creamos una nueva instancia de la clase para obtener atributos y metodos
        manager = new DataBaseManager(this);

        listView=(ListView)findViewById(R.id.list);

        dataModels= new ArrayList<>();

        cursor = manager.selectDataRecordsTimer();
        if (cursor.moveToFirst()){
            String direccion, comentario, fecha, hora;
            do{
                direccion = cursor.getString(0);
                comentario = cursor.getString(1);
                fecha = cursor.getString(2);
                hora = cursor.getString(3);
                dataModels.add(new DataHistory(fecha, comentario, direccion,hora));
            } while (cursor.moveToNext());
        } else {
            dataModels.add(new DataHistory("Apple Pie", "Android 1.0", "1","September 23, 2008"));
        }

        adapter= new CustomAdapterHistory(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataHistory dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType() + dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
