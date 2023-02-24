package com.example.lab_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class TodoListActivity extends AppCompatActivity {
    // Exposed for testing purposes later...
    public RecyclerView recyclerView;
    public TodoListViewModel viewModel;
    private EditText newTodoText;
    private Button addTodoButton;
    private TextView deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        viewModel = new ViewModelProvider(this)
                .get(TodoListViewModel.class);

        TodoListAdapter adapter = new TodoListAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnCheckBoxClickedHandler(viewModel::toggleCompleted);
        adapter.setOnTextEditedHandler(viewModel::updateText);
        adapter.setOnDeleteClickHandler(viewModel::deleteTodo);
        viewModel.getTodoListItems().observe(this,adapter::setTodoItems);

        recyclerView = findViewById(R.id.todo_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        this.newTodoText = this.findViewById(R.id.new_todo_text);
        this.addTodoButton = this.findViewById(R.id.add_todo_btn);
        this.deleteBtn = this.findViewById(R.id.delete_btn);

        addTodoButton.setOnClickListener(this::onAddTodoClicked);
    }

    void onAddTodoClicked(View view) {
        String text = newTodoText.getText().toString();
        newTodoText.setText("");
        viewModel.createTodo(text);
    }

}