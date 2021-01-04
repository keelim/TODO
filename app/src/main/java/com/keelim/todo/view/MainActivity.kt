package com.keelim.todo.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.keelim.todo.R
import com.keelim.todo.databinding.ActivityMainBinding
import com.keelim.todo.databinding.CustomDialogBinding
import com.keelim.todo.model.TodoModel
import com.keelim.todo.view.adapter.TodoListAdapter
import com.keelim.todo.viewmodel.TodoViewModel
import java.util.*


import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mTodoListAdapter: TodoListAdapter
    private lateinit var mTodoViewModel: TodoViewModel
    private val mTodoItems: ArrayList<TodoModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ///
        binding.rlTodoList.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TodoListAdapter().apply {
                listener = object : TodoListAdapter.OnTodoItemClickListener {
                    override fun onTodoItemClick(position: Int) {
                        openModifyTodoDialog(getItem(position))
                    }

                    override fun onTodoItemLongClick(position: Int) {
                        TODO("Not yet implemented")
                    }

                }
            }
        }

        binding.btnAddTodo.setOnClickListener {
            openAddTodoDialog()
        }

        mTodoViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(TodoViewModel::class.java)
        mTodoViewModel.getTodoList().observe(this, Observer {
            mTodoListAdapter.setTodoItems(it)
        })
    }

    private fun openAddTodoDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val bbind = CustomDialogBinding.bind(dialogView)
        AlertDialog.Builder(this)
            .setTitle("추가하기")
            .setView(bbind.root)
            .setPositiveButton("확인") { dialogInterface, i ->
                val title = bbind.etTodoTitle.text.toString()
                val description = bbind.etTodoDescription.text.toString()
                val createdDate = Date().time

                val todoModel = TodoModel(null, title, description, createdDate)
                mTodoViewModel.insertTodo(todoModel)
            }.setNegativeButton("취소", null)
            .create()
            .show()
    }

    private fun openModifyTodoDialog(todoModel: TodoModel) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val bbind = CustomDialogBinding.bind(dialogView)
        bbind.etTodoTitle.setText(todoModel.title)
        bbind.etTodoDescription.setText(todoModel.description)

        AlertDialog.Builder(this)
            .setTitle("수정하기")
            .setView(bbind.root)
            .setPositiveButton("확인") { dialogInterface, i ->
                val title = bbind.etTodoTitle.text.toString()
                val description = bbind.etTodoDescription.text.toString()
                todoModel.description = description
                todoModel.title = title
                mTodoViewModel.updateTodo(todoModel)
            }.setNegativeButton("취소", null)
            .create()
            .show ()
    }


}