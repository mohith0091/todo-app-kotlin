package com.mohith.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mohith.todoapp.R
import com.mohith.todoapp.model.TodoModel
import de.dlyt.yanndroid.oneui.sesl.recyclerview.DiffUtil
import de.dlyt.yanndroid.oneui.sesl.recyclerview.ListAdapter
import de.dlyt.yanndroid.oneui.view.RecyclerView
import de.dlyt.yanndroid.oneui.view.RecyclerView.NO_POSITION
import android.graphics.Paint

import android.R.attr.name




class TodoAdapter (private val onItemClickListener: (TodoModel) -> Unit)
    : ListAdapter<TodoModel, TodoAdapter.TodoHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.todo_item, parent,
            false)
        return TodoHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        with(getItem(position)) {

            holder.tvTitle.text = title
            holder.tvDescription.text = description
            if(completed == true){
                holder.tvTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.tvDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.root.setBackgroundResource(R.drawable.todo_item_background_completed)
                holder.checkBox.visibility = View.VISIBLE
            }else{
                holder.checkBox.visibility = View.GONE
            }
        }
    }

    fun getNoteAt(position: Int) = getItem(position)

    fun getItemByPosition(position: Int): TodoModel{
        return getItem(position)
    }

    fun refresh(){
        notifyDataSetChanged()
    }
    inner class TodoHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val root :ViewGroup = itemView.findViewById(R.id.todo_root)
        val tvTitle: TextView = itemView.findViewById(R.id.title)
        val tvDescription: TextView = itemView.findViewById(R.id.description)
        val checkBox:View = itemView.findViewById(R.id.checkmark)

        init {
            itemView.setOnClickListener {
                if(adapterPosition != NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }

    }
}

private val diffCallback = object : DiffUtil.ItemCallback<TodoModel>() {
    override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel) =
        oldItem.title == newItem.title
                && oldItem.description == newItem.description
}