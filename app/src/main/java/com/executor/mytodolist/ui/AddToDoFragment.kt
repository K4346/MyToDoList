package com.executor.mytodolist.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.executor.mytodolist.data.entities.ToDoEntity
import com.executor.mytodolist.data.entities.ToDoPriority
import com.executor.mytodolist.databinding.FragmentDetailToDoBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class AddToDoFragment : Fragment() {

    private var _binding: FragmentDetailToDoBinding? = null

    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    @SuppressLint("SimpleDateFormat")
    private val date = SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().time)
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
        initView()
        setOnClickListeners()
    }


    private fun initView() {
        binding.tvDate.text = date
    }

    private fun setOnClickListeners() {
        binding.mbSaveToDo.setOnClickListener {
            if (binding.tvName.text.toString().isEmpty()) {
                Snackbar.make(it, "Введите название", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.insert(
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
                    date.toString()
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