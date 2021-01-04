package com.keelim.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.keelim.todo.model.TodoModel
import com.keelim.todo.repository.TodoRepository

class TodoViewModel(application: Application): AndroidViewModel(application) {
    private val mTodoRepository: TodoRepository
    private var mTodoItems: LiveData<ArrayList<TodoModel>>

    init{
        mTodoRepository = TodoRepository(application)
        mTodoItems = mTodoRepository.getTodoList()
    }

    fun insertTodo(todoModel:TodoModel){
        mTodoRepository.insertTodo(todoModel)
    }

    fun getTodoList(): LiveData<ArrayList<TodoModel>>{
        return mTodoItems
    }

    fun deleteTodo(todoModel: TodoModel){
        mTodoRepository.deleteTodo(todoModel)
    }

}