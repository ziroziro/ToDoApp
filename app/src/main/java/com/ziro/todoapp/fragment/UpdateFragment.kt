package com.ziro.todoapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ziro.todoapp.R
import com.ziro.todoapp.data.Priority
import com.ziro.todoapp.data.ToDoData
import com.ziro.todoapp.viewmodel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        view.edt_title_current.setText(args.currentItem.title)
        view.edt_description_current.setText(args.currentItem.description)
        view.spinner_priorities_current.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.spinner_priorities_current.onItemSelectedListener = mSharedViewModel.listener

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmDeleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteItem() {
        AlertDialog.Builder(requireContext())
                .setTitle("Delete '${args.currentItem.title}' ?")
                .setMessage("Are you sure want to remove '${args.currentItem.title}' ?")
                .setPositiveButton("Yes"){ _, _ ->
                    mTodoViewModel.deleteItem(args.currentItem)
                    Toast.makeText(
                            requireContext(),
                            "Successfully Removed: ${args.currentItem.title}",
                            Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                }
                .setNegativeButton("No", null)
                .create()
                .show()
    }

    private fun updateItem() {
        val title = edt_title_current.text.toString()
        val description = edt_description_current.text.toString()
        val getPriority = spinner_priorities_current.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updateItem = ToDoData(
                    args.currentItem.id,
                    title,
                    mSharedViewModel.parsePriority(getPriority),
                    description
            )
            mTodoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Successfully Update!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                    .show()
        }
    }


}