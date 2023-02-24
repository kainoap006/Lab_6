package com.example.lab_6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {
    private List<TodoListItem> todoItems = Collections.emptyList();
    private Consumer<TodoListItem> onCheckBoxClicked;
    private BiConsumer<TodoListItem, String> onTextEditedHandler;
    private Consumer<TodoListItem> onDeleteClicked;

    public void setTodoItems(List<TodoListItem> newTodoItems) {
        this.todoItems.clear();
        this.todoItems = newTodoItems;
        notifyDataSetChanged();
    }

    public void setOnCheckBoxClickedHandler(Consumer<TodoListItem> onCheckBoxClicked) {
        this.onCheckBoxClicked = onCheckBoxClicked;
    }

    public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEdited){
        this.onTextEditedHandler = onTextEdited;
    }

    public void setOnDeleteClickedHandler(Consumer<TodoListItem> deleteTodo) {
        this.onDeleteClicked = deleteTodo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setTodoItem(todoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    @Override
    public long getItemId(int position) {
        return todoItems.get(position).id;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private CheckBox checkBox;
        private TodoListItem todoItem;
        private TextView deleteBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.todo_item_text);
            this.checkBox = itemView.findViewById(R.id.completed);
            this.deleteBox = itemView.findViewById(R.id.delete_btn);

            this.textView.setOnFocusChangeListener((view, hasFocus) ->{
                if (!hasFocus){
                    onTextEditedHandler.accept(todoItem,textView.getText().toString());
                }
            });

            this.checkBox.setOnClickListener(view -> {
                if (onCheckBoxClicked == null) return;
                onCheckBoxClicked.accept(todoItem);
            });

            this.deleteBox.setOnClickListener(view -> {
                if (onDeleteClicked == null) return;
                onDeleteClicked.accept(todoItem);
            });
        }

        public TodoListItem getTodoItem() {
            return todoItem;
        }

        public void setTodoItem(TodoListItem todoItem) {
            this.todoItem = todoItem;
            this.textView.setText(todoItem.text);
            this.checkBox.setChecked(todoItem.completed);
        }
    }
}
