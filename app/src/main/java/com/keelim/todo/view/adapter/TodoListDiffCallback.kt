package com.keelim.todo.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.keelim.todo.model.TodoModel

class TodoListDiffCallback(val oldTodoList: List<TodoModel>, val newTodoList: List<TodoModel>): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldTodoList.size


    override fun getNewListSize(): Int = newTodoList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodoList[oldItemPosition].id == newTodoList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newTodoList[newItemPosition].equals(oldTodoList[oldItemPosition])
    }

}

