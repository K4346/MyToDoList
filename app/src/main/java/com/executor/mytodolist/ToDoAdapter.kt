package com.executor.mytodolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.executor.mytodolist.databinding.ToDoItemBinding

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    data class ToDoEntity(
        var name:String,
        var description:String="",
        var isReady:Boolean=false
    )
    private var list: List<ToDoEntity> = arrayListOf()
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
        holder.isReady.isChecked = item.isReady
    }

    override fun getItemCount() = list.size

    inner class ToDoViewHolder(binding: ToDoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvToDoItem
        val isReady = binding.cbToDoItem
    }
}