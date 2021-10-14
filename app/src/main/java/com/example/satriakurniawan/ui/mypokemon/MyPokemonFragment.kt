package com.example.satriakurniawan.ui.mypokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.satriakurniawan.databinding.MyPokemonFragmentBinding
import com.example.satriakurniawan.utils.Resource
import com.example.satriakurniawan.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import android.app.Dialog
import android.view.WindowManager
import com.example.satriakurniawan.data.entities.MyCharacter
import com.example.satriakurniawan.databinding.ChangenameDialogBinding


@AndroidEntryPoint
class MyPokemonFragment : Fragment(), MyPokemonAdapter.MyCharacterItemListener {

    private var binding: MyPokemonFragmentBinding by autoCleared()
    private val viewModel: MyPokemonViewModel by viewModels()
    private lateinit var adapter: MyPokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyPokemonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = MyPokemonAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }


    override fun onClickedCharacter(characterId: MyCharacter) {
        myDialog(characterId)
    }


    private fun myDialog(character: MyCharacter) {
        val dialog = Dialog(requireContext())
        val bind: ChangenameDialogBinding = ChangenameDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()

        bind.etName.setText(character.name + "-" + character.current)
        bind.tvName.setText(character.name)
        //fibbonaci state

        var current = 0
        if (character.last == 0) {
            current = 1
        } else {
            current = character.last + character.current
        }

        bind.btnLogin.setOnClickListener {
            val renameString  = bind.etName.text.toString()
            Log.d("lol", "myDialog: "+renameString)
            viewModel.update(
                MyCharacter(
                    character.id,
                    character.name,
                    renameString,
                    character.url,
                    current,
                    character.current
                )
            )
            dialog.dismiss()
        }

        dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }


}
