package com.executor.mytodolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.executor.mytodolist.data.entities.ToDoEntity
import com.executor.mytodolist.data.entities.ToDoPriority
import com.executor.mytodolist.databinding.FragmentDetailToDoBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailToDoBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    lateinit var toDoItem: ToDoEntity
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailToDoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        index = arguments?.getInt(TO_DO_INDEX) ?: 0
        toDoItem = viewModel.todoList[index]
        initView()
        setOnClickListeners()
    }

    private fun initView() {
        binding.tvName.setText(toDoItem.name)
        binding.tvDescription.setText(toDoItem.description)
        binding.spinnerPriority.setSelection(
            when (toDoItem.priority) {
                ToDoPriority.High -> 0
                ToDoPriority.Mid -> 1
                ToDoPriority.Low -> 2
            }
        )
        binding.tvDate.text = toDoItem.date
    }

    private fun setOnClickListeners() {
        binding.mbSaveToDo.setOnClickListener {
            if (binding.tvName.text.toString().isEmpty()) {
                Snackbar.make(it, "Введите название", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentObject = viewModel.todoList[index]
            currentObject.name = binding.tvName.text.toString()
            currentObject.description = binding.tvDescription.text.toString()
            currentObject.priority =
                when (binding.spinnerPriority.selectedItemPosition) {
                    0 -> ToDoPriority.High
                    1 -> ToDoPriority.Mid
                    2 -> ToDoPriority.Low
                    else -> {
                        ToDoPriority.Low
                    }
                }
            viewModel.update(currentObject, index)
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TO_DO_INDEX = "TO_DO_INDEX"
    }
}