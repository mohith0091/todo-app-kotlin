package com.mohith.todoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.mohith.todoapp.R
import com.mohith.todoapp.model.TodoModel
import com.mohith.todoapp.utils.showSnack
import com.mohith.todoapp.viewmodel.VMTodo
import de.dlyt.yanndroid.oneui.layout.ToolbarLayout

val DEFAULT_ADD_ID:Int = -1

class AddEditActivity : AppCompatActivity() {
    private var _model:TodoModel? = null
    private lateinit var _toolBar :ToolbarLayout;

    private lateinit var _title:EditText
    private lateinit var _description:EditText
    private lateinit var _addBtn:Button

    private lateinit var _todoVM : VMTodo
    companion object{
        val TODO_ID:String = "todo_id"
        val TAG:String = AddEditActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        this.getArguments()
        this.bindIds()
        this.setupData()
        this.setupVM()
        this.onclicks()
    }

    private fun setupData() {
        if(!isNew()){
            this._title.setText(_model?.title)
            this._description.setText(_model?.description)
            this._addBtn.text = "Edit"
        }
    }

    private fun getArguments() {
        _model = intent.getParcelableExtra(TODO_ID)
    }

    private fun onclicks() {
        _addBtn.setOnClickListener {

           val model:TodoModel? =  prepareModel()
            if(model!=null){
                if(isNew()){
                    _todoVM.insert(model)
                }else{
                    _todoVM.update(model)
                }
                finish()
            }else{
                showSnack(window.decorView.rootView,"Please enter the title to add",
                    ContextCompat.getColor(this@AddEditActivity,R.color.sesl_functional_red),
                    ContextCompat.getColor(this@AddEditActivity,R.color.white),null)
            }
        }

        _toolBar.setNavigationButtonOnClickListener {
            finish()
        }

    }

    private fun prepareModel(): TodoModel? {
        var id:Int? = null
        val  title :String = _title.text.toString()
        val description:String = _description.text.toString()
        if(title.trim() == ""){
            return null
        }
        if(!isNew() && _model?.id!=null){
            id = _model?.id
        }
        return TodoModel(id,title,description)
    }

    private fun setupVM() {
        _todoVM = ViewModelProvider(this).get(VMTodo::class.java)
    }

    private fun bindIds() {
        _toolBar = findViewById(R.id.toolbar_layout)
        _title = findViewById(R.id.title_text)
        _description = findViewById(R.id.description_text)
        _addBtn = findViewById(R.id.add_button)
    }

    private fun isNew():Boolean{
        return _model ==null
    }

}