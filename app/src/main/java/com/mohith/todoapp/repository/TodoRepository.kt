package com.mohith.todoapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.mohith.todoapp.database.AppDatabase
import com.mohith.todoapp.database.TodoDao
import com.mohith.todoapp.model.TodoModel
import com.mohith.todoapp.utils.subscribeOnBackground

class TodoRepository(application: Application) {
    private var todoDao:TodoDao
    private var allTodos:LiveData<List<TodoModel>>

    private val database = AppDatabase.getInstance(application)

    init {
        todoDao = database.todoDao()
        allTodos = todoDao.getAllTodo()
    }

    fun insert(vararg todo:TodoModel){
        subscribeOnBackground {
            todoDao.insert(*todo)
        }
    }

    fun updated(todo:TodoModel){
        subscribeOnBackground {
            todoDao.update(todo)
        }
    }

    fun delete(todo:TodoModel){
        subscribeOnBackground {
            todoDao.delete(todo)
        }
    }

    fun getAllTodos(): LiveData<List<TodoModel>> {
        return allTodos;
    }



}