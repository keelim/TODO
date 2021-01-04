package com.keelim.todo.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.keelim.todo.database.TodoDAO
import com.keelim.todo.database.TodoDatabase
import com.keelim.todo.model.TodoModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class TodoRepository(application: Application) {
    private var mTodoDatabase: TodoDatabase
    private var mTodoDAO:TodoDAO
    private var mTodoItems: LiveData<ArrayList<TodoModel>>

    init{
        mTodoDatabase = TodoDatabase.getInstance(application)
        mTodoDAO = mTodoDatabase.todoDao()
        mTodoItems = mTodoDAO.getTodoList()
    }

    fun getTodoList():LiveData<ArrayList<TodoModel>> {return mTodoItems}

    fun insertTodo(todoModel: TodoModel){
        Thread{
            mTodoDAO.insertTodo(todoModel)
        }.start()

        Observable.just(todoModel)
            .subscribeOn(Schedulers.io())
            .subscribe({
                mTodoDAO.insertTodo(todoModel)
            },{

            })
    }

    fun deleteTodo(todoModel: TodoModel){
        Observable.just(todoModel)
            .subscribeOn(Schedulers.io())
            .subscribe  ({
                mTodoDAO.deleteTodo(todoModel)
            }, {

            })
    }
}