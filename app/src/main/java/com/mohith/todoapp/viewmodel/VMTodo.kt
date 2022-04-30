package com.mohith.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohith.todoapp.model.TodoModel
import com.mohith.todoapp.repository.TodoRepository

class VMTodo(app:Application): AndroidViewModel(app) {
    private val todoRepo = TodoRepository(app)
    private val allTodos = todoRepo.getAllTodos()

    fun getAllTodos() : LiveData<List<TodoModel>>{
        return allTodos
    }

    fun insert(vararg todo:TodoModel){
        todoRepo.insert(*todo)
    }

    fun update(todo:TodoModel){
        todoRepo.updated(todo)
    }

    fun delete(todo:TodoModel){
        todoRepo.delete(todo)
    }

}