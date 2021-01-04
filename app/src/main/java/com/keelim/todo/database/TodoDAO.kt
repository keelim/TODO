package com.keelim.todo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.keelim.todo.model.TodoModel

@Dao
interface TodoDAO {

    @Query("select * from Todo order by createdDate asc")
    fun getTodoList(): LiveData<ArrayList<TodoModel>>

    @Insert
    fun insertTodo(todoModel: TodoModel)

    @Delete
    fun deleteTodo(todoModel: TodoModel)
}