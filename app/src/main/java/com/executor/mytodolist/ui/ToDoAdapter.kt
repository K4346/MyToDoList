package com.executor.mytodolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.executor.mytodolist.data.entities.ToDoEntity
import com.executor.mytodolist.databinding.ToDoItemBinding

class ToDoAdapter(private val toDoCallbacks: ToDoCallbacks) :
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    var list: List<ToDoEntity> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = ToDoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val item = list[position]

        holder.isReady.setOnCheckedChangeListener(null)

        holder.name.text = item.name
        holder.isReady.isChecked = item.isReady

        holder.isReady.setOnCheckedChangeListener { compoundButton, b ->
            toDoCallbacks.check(position, compoundButton.isChecked)
        }

        holder.name.setOnClickListener(null)
        holder.name.setOnClickListener {
            toDoCallbacks.click.invoke(position)
        }

        holder.removeButton.setOnClickListener(null)
        holder.removeButton.setOnClickListener {
            toDoCallbacks.remove.invoke(position)
        }
    }

    override fun getItemCount() = list.size

    inner class ToDoViewHolder(binding: ToDoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvToDoItem
        val isReady = binding.cbToDoItem
        val removeButton = binding.ibToDoRemove
    }

    data class ToDoCallbacks(
        val click: (index: Int) -> Unit,
        val check: (index: Int, isReady: Boolean) -> Unit,
        val remove: (index: Int) -> Unit,
    )
}