package com.executor.mytodolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.executor.mytodolist.databinding.FragmentDetailToDoBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class AddToDoFragment : Fragment() {

    private var _binding: FragmentDetailToDoBinding? = null

    private val binding get() = _binding!!

    private val date = Calendar.getInstance().time
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
        binding.tvDate.text = SimpleDateFormat("dd-MM-yyyy").format(date)
    }

    private fun setOnClickListeners() {
        binding.mbSaveToDo.setOnClickListener {
            if (binding.tvName.text.toString().isEmpty()) {
                Snackbar.make(it, "Введите название", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            ToDoData.toDoList.add(
                ToDoEntity(
                    binding.tvName.text.toString(),
                    binding.tvDescription.text.toString(),
                    when (binding.spinnerPriority.selectedItemPosition) {
                        0 -> ToDoPriority.High
                        1 -> ToDoPriority.Mid
                        2 -> ToDoPriority.Low
                        else -> {
                            ToDoPriority.Low
                        }
                    },
                    false,
                    date
                )
            )
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}