package com.executor.mytodolist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.executor.mytodolist.R
import com.executor.mytodolist.data.entities.ToDoEntity
import com.executor.mytodolist.databinding.FragmentFirstBinding
import com.executor.mytodolist.ui.DetailFragment.Companion.TO_DO_INDEX

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ToDoListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    lateinit var adapter: ToDoAdapter

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setAdapter()
        setObservers()
        setListenners()

    }

    private fun setAdapter() {
        val callback = ToDoAdapter.ToDoCallbacks({
            onClickItem(it)
        }, { index, isReady ->
            checkItem(index, isReady)
        }, {
            removeItem(it)
        })
        adapter = ToDoAdapter(callback)
        binding.rvToDoList.adapter = adapter
        if (adapter.list.isEmpty()) {
            adapter.list = viewModel.todoList
        }
    }

    private fun setObservers() {
        viewModel.todoListFromDb.observe(viewLifecycleOwner) {
            if (viewModel.todoList.isNotEmpty()) return@observe
            viewModel.todoList = it as ArrayList<ToDoEntity>
            viewModel.sortItems()
            adapter.list = viewModel.todoList
        }
        viewModel.listUpdatedSLE.observe(viewLifecycleOwner) {
//            binding.rvToDoList.post {
            Log.i("bts", adapter.list.toString())
            Log.i("bts", viewModel.todoList.toString())
            Log.i("bts", binding.rvToDoList.adapter.toString())
            adapter.list = viewModel.todoList
//            }
        }
    }

    private fun checkItem(index: Int, isReady: Boolean) {
        val obj = viewModel.todoList[index]
        obj.isReady = isReady
        viewModel.update(obj, index)
    }

    private fun removeItem(it: Int) {
        viewModel.delete(it)
    }

    private fun onClickItem(index: Int) {
        findNavController().navigate(
            R.id.action_FirstFragment_to_SecondFragment,
            bundleOf(TO_DO_INDEX to index)
        )
    }

    private fun setListenners() {
        binding.fabNewToDo.setOnClickListener {
            findNavController().navigate(
                R.id.action_FirstFragment_to_addToDoFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}