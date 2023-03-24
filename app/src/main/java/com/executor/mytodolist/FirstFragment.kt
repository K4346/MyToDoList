package com.executor.mytodolist

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.executor.mytodolist.SecondFragment.Companion.TO_DO_INDEX
import com.executor.mytodolist.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    lateinit var adapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenners()
     val callback =   ToDoAdapter.ToDoCallbacks({
                 onClickItem(it)
        },{index, isReady ->
            checkItem(index,isReady)
        },{
         removeItem(it)
     })
        adapter = ToDoAdapter(callback)
        binding.rvToDoList.adapter = adapter
    }

    private fun checkItem(index: Int, isReady: Boolean) {
        ToDoData.toDoList[index].isReady=isReady
        ToDoData.sortItems()
        binding.rvToDoList.post {
            adapter.notifyDataSetChanged()
        }
    }

    private fun removeItem(it: Int) {
       ToDoData.toDoList.removeAt(it)
        binding.rvToDoList.post {
            adapter.notifyDataSetChanged()
        }
    }

    private fun onClickItem(index:Int) {
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

    override fun onResume() {
        super.onResume()
        ToDoData.sortItems()
        binding.rvToDoList.post {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}