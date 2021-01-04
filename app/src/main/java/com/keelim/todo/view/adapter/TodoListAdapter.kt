package com.keelim.todo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keelim.todo.databinding.ItemTodoBinding
import com.keelim.todo.model.TodoModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

import kotlin.collections.ArrayList

class TodoListAdapter :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    private var todoItems:ArrayList<TodoModel> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false), null)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val items = todoItems[position]

        holder.tv_todo_title.text = items.title
        holder.tv_todo_description.text = items.description
        holder.tv_todo_createDate.text = items.createdDate.toDateString("yyyy.MM.dd HH:mm")
    }

    override fun getItemCount(): Int = todoItems.size

    fun addItem(todoModel:TodoModel){
        todoItems.add(todoModel)
    }

    fun getItem(position:Int):TodoModel = todoItems[position]

    fun setTodoItems(todoItems: ArrayList<TodoModel>){
        val diffCallback = TodoListDiffCallback(this.todoItems, todoItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.todoItems = todoItems
        diffResult.dispatchUpdatesTo(this)

        Observable.just(todoItems)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .map { DiffUtil.calculateDiff(TodoListDiffCallback(this.todoItems, todoItems)) }
            .subscribe ({
                this.todoItems = todoItems
                it.dispatchUpdatesTo(this)
            },{

            })
    }

    class TodoViewHolder(binding:ItemTodoBinding, listener: OnTodoItemClickListener?): RecyclerView.ViewHolder(binding.root){
        val tv_todo_title:TextView = binding.tvTodoTitle
        val tv_todo_description:TextView = binding.tvTodoTitle
        val tv_todo_createDate:TextView = binding.tvTodoCreatedDate

        init{
            binding.root.setOnClickListener {
                listener?.onTodoItemClick(adapterPosition)
            }

            binding.root.setOnLongClickListener {
                listener?.onTodoItemLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    interface OnTodoItemClickListener{
        fun onTodoItemClick(position:Int)
        fun onTodoItemLongClick(position:Int)
    }

    var listener: OnTodoItemClickListener? = null
}

fun Long.toDateString(format:String): String{
    val simpleDateFormat = SimpleDateFormat(format)
    return simpleDateFormat.format((Date(this)))
}