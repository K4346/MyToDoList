package com.executor.mytodolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.executor.mytodolist.databinding.ToDoItemBinding
import java.util.*

class ToDoAdapter(private val toDoCallbacks: ToDoCallbacks) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = ToDoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val item = ToDoData.toDoList[position]

        holder.name.text = item.name
        holder.isReady.isChecked = item.isReady
        holder.name.setOnClickListener {
            toDoCallbacks.click.invoke(position)
        }
        holder.isReady.setOnCheckedChangeListener { compoundButton, b ->
           toDoCallbacks.check(position,compoundButton.isChecked)
        }
        holder.removeButton.setOnClickListener {
            toDoCallbacks.remove.invoke(position)
        }
    }

    override fun getItemCount() = ToDoData.toDoList.size

    inner class ToDoViewHolder(binding: ToDoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvToDoItem
        val isReady = binding.cbToDoItem
        val removeButton = binding.ibToDoRemove
    }
    data class ToDoCallbacks(
        val click:(index:Int)->Unit,
        val check:(index:Int,isReady:Boolean)->Unit,
        val remove:(index:Int)->Unit,
    )
}