package com.ziro.todoapp.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.solver.state.State
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ziro.todoapp.R
import com.ziro.todoapp.adapter.ListAdapter
import com.ziro.todoapp.data.ToDoDatabase
import com.ziro.todoapp.databinding.FragmentListBinding
import com.ziro.todoapp.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlin.contracts.Returns

class ListFragment : Fragment() {

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val listAdapter: ListAdapter by lazy { ListAdapter() }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mSharedViewModel

        setupRecyclerView()

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            listAdapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })


        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupRecyclerView() {
        val rvTodo = binding.rvTodo
        rvTodo.apply {
            layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
            adapter = listAdapter
        }
    }


    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            img_no_data.visibility = View.VISIBLE
            tv_no_data.visibility = View.VISIBLE
        } else {
            img_no_data.visibility = View.INVISIBLE
            tv_no_data.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_delete_all -> confirmDeleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteAllData() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Delete Anything?")
                .setMessage("Are you sure want to remove everything?")
                .setPositiveButton("Yes"){ _, _ ->
                    mToDoViewModel.deleteAllData()
                    Toast.makeText(
                            requireContext(),
                            "SuccessFully Removed Everything",
                            Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("No", null)
                .create()
                .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}