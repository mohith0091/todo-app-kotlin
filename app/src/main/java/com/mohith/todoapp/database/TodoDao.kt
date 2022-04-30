package com.mohith.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mohith.todoapp.model.TodoModel

@Dao
interface TodoDao {
    @Query("SELECT * FROM todomodel ORDER BY completed")
    fun getAllTodo() : LiveData<List<TodoModel>>

    @Query("SELECT * FROM todomodel WHERE id = :id")
    fun getTodoById(id:Int) : LiveData<TodoModel>

    @Insert
    fun insert(vararg todo:TodoModel)

    @Delete
    fun delete(todo:TodoModel)

    @Update
    fun update(todo:TodoModel)
}