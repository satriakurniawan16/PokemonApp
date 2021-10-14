package com.example.satriakurniawan.ui.characterdetail

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.satriakurniawan.data.entities.CharacterDetailList
import com.example.satriakurniawan.data.entities.MyCharacter
import com.example.satriakurniawan.databinding.ChangenameDialogBinding
import com.example.satriakurniawan.databinding.CharacterDetailFragmentBinding
import com.example.satriakurniawan.ui.BaseFragment
import com.example.satriakurniawan.utils.Resource
import com.example.satriakurniawan.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : BaseFragment() {

    private var binding: CharacterDetailFragmentBinding by autoCleared()
    private val viewModel: CharacterDetailViewModel by viewModels()
    private var model: MyCharacter? = null

    private var name = ""
    private var imageUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("id")?.let { viewModel.start(it) }

        setupObservers()

        binding.catchPokemon.setOnClickListener {
            doProbability()
        }

    }

    private fun setupObservers() {
        viewModel.character.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.d("lolkunn", "setupObservers: " + it.data)
                    bindCharacter(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.characterCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.characterCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(character: CharacterDetailList) {


        binding.name.text = character.name
        binding.species.text = character.species.name
        binding.weight.text = character.weight.toString()

        name = character.name
        imageUrl = character.sprites.front_default
        var moveString = ""
        for (i in 0 until 3) {
            moveString = moveString + "," + character.moves[i].move.name
        }
        var typeString = ""
        for (i in character.types.indices) {
            typeString = typeString + "," + character.types[i].type.name
        }
        binding.move.text = moveString
        binding.type.text = typeString
        Glide.with(binding.root)
            .load(character.sprites.front_default)
            .transform(CircleCrop())
            .into(binding.image)
    }

    private fun doProbability() {
        var d = Math.random() * 100;
        if (d < 50) {
            getSnackBar(binding.root, "Fail")
        } else {
            getSnackBar(binding.root, "Success")
            myDialog()
        }
    }

    private fun myDialog() {
        val dialog = Dialog(requireContext())
        val bind: ChangenameDialogBinding = ChangenameDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()

        bind.etName.setText(name)
        bind.tvName.setText("You Catched a Pokemon !")

        val newName = bind.etName.text.toString()

        model = MyCharacter(0, name, newName, imageUrl, 0, 0)
        bind.btnLogin.setOnClickListener { viewModel.catched(model!!)
        dialog.dismiss()
        getSnackBar(binding.root,"Saved")}

        dialog.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

}
