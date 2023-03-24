package com.executor.mytodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.executor.mytodolist.databinding.FragmentDetailToDoBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentDetailToDoBinding? = null

    private val binding get() = _binding!!

    lateinit var toDoItem: ToDoEntity
    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        index = arguments?.getInt(TO_DO_INDEX) ?: 0
        toDoItem = ToDoData.toDoList[index]

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
        binding.tvDate.text = SimpleDateFormat("dd-MM-yyyy").format(toDoItem.date)
    }

    private fun setOnClickListeners() {
        binding.mbSaveToDo.setOnClickListener {
            if (binding.tvName.text.toString().isEmpty()) {
                Snackbar.make(it, "Введите название", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            ToDoData.toDoList[index].name = binding.tvName.text.toString()
            ToDoData.toDoList[index].description = binding.tvDescription.text.toString()
            ToDoData.toDoList[index].priority =
                when (binding.spinnerPriority.selectedItemPosition) {
                    0 -> ToDoPriority.High
                    1 -> ToDoPriority.Mid
                    2 -> ToDoPriority.Low
                    else -> {
                        ToDoPriority.Low
                    }
                }
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