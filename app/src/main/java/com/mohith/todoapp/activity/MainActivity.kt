package com.mohith.todoapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mohith.todoapp.R
import com.mohith.todoapp.adapter.TodoAdapter
import com.mohith.todoapp.gesture.SwipeGesture
import com.mohith.todoapp.model.TodoModel
import com.mohith.todoapp.utils.showSnack
import com.mohith.todoapp.viewmodel.VMTodo
import de.dlyt.yanndroid.oneui.sesl.recyclerview.ItemTouchHelper
import de.dlyt.yanndroid.oneui.sesl.recyclerview.LinearLayoutManager
import de.dlyt.yanndroid.oneui.view.RecyclerView
import android.content.ActivityNotFoundException

import android.net.Uri
import de.dlyt.yanndroid.oneui.view.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private lateinit var todoVM: VMTodo
    private lateinit var adapter: TodoAdapter
    private lateinit var getSourceCodeBtn: Button

    val TAG: String = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.bindIds()
        this.setupDrawer()
        this.setupAdapter()
        this.setupVM()
        this.onclicks()

    }

    private fun setupDrawer() {

    }

    private fun onclicks() {
        fab.setOnClickListener {
            goToAddEditPage(null)
        }
        getSourceCodeBtn.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                startActivity(browserIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this, "No application can handle this request."
                            + " Please install a webbrowser", Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }

    private fun goToAddEditPage(model: TodoModel?) {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra(AddEditActivity.TODO_ID, model)
        startActivity(intent)
    }

    private fun setupVM() {
        todoVM = ViewModelProvider(this).get(VMTodo::class.java)
        todoVM.getAllTodos().observe(this, {
            adapter.submitList(it)
        })
    }

    private fun setupAdapter() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter { click ->
            val model =
                TodoModel(click.id, click.title, click.description, click.completed, click.color)
            goToAddEditPage(model)
        }

        val swipeGesture = object : SwipeGesture() {
            override fun onSwiped(p0: RecyclerView.ViewHolder, direction: Int) {
                val todoModel = adapter.getItemByPosition(p0.adapterPosition)
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        Log.e(TAG, "onSwiped: Left")
                        todoVM.delete(todoModel)
                        showSnack(
                            window.decorView.rootView,
                            "TODO Deleted",
                            ContextCompat.getColor(this@MainActivity, R.color.sesl_functional_red),
                            ContextCompat.getColor(this@MainActivity, R.color.white),
                            fab
                        )
                    }
                    ItemTouchHelper.RIGHT -> {
                        Log.e(TAG, "onSwiped: Right")
                        todoModel.completed = true
                        todoVM.update(todoModel)
                        adapter.refresh()
                        showSnack(
                            window.decorView.rootView,
                            "TODO Completed",
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.sesl_functional_green
                            ),
                            ContextCompat.getColor(this@MainActivity, R.color.white),
                            fab
                        )
                    }
                }
                super.onSwiped(p0, direction)
            }
        }

        recyclerView.adapter = adapter

        val touchHelper = ItemTouchHelper(swipeGesture)

        touchHelper.attachToRecyclerView(recyclerView)

    }


    private fun bindIds() {
        recyclerView = findViewById(R.id.recycler_view)
        getSourceCodeBtn = findViewById(R.id.get_source_code)
        fab = findViewById(R.id.fab)
    }
}