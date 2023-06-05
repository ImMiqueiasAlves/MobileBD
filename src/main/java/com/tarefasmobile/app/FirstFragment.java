package com.tarefasmobile.app;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.tarefasmobile.app.databinding.FragmentFirstBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper(MainActivity.getAppContext());
        ArrayList<String> list = new ArrayList<String>();
        ListView listView = (ListView) view.findViewById(R.id.lista_tarefas);
//        dbHelper.criarTarefa("Tirar o lixo");
//        dbHelper.criarTarefa("Varrer a casa");
//        dbHelper.criarTarefa("Dar comida para o cão");
//        dbHelper.atualizarStatusTarefa(30, true);
        Cursor data = dbHelper.buscarTarefas();
        while (data.moveToNext()) {
            String completed = data.getInt(2) == 1 ? "Sim" : "Não";
             list.add("Id: " +data.getInt(0) +"Tarefa: " + data.getString(1) + "Completado: " + completed);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                MainActivity.getAppContext(),
                android.R.layout.simple_list_item_1,
                list
        );
        listView.setOnItemClickListener((arg0, arg1, position, id) -> {
            // TODO Auto-generated method stub
            Toast.makeText(MainActivity.getAppContext(),
                    "You selected : " + list.get(position), Toast.LENGTH_SHORT).show();
            list.set(position, "Bosta");
            arrayAdapter.notifyDataSetChanged();
        });
        listView.setAdapter(arrayAdapter);
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}