package com.example.satriakurniawan.ui.mypokemon

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.satriakurniawan.data.entities.MyCharacter
import com.example.satriakurniawan.databinding.ItemCharacterBinding

class MyPokemonAdapter(private val listener: MyCharacterItemListener) : RecyclerView.Adapter<CharacterViewHolder>() {

    interface MyCharacterItemListener {
        fun onClickedCharacter(characterId: MyCharacter)
    }

    private val items = ArrayList<Any>()

    fun setItems(items: ArrayList<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(items[position])
}

class CharacterViewHolder(private val itemBinding: ItemCharacterBinding, private val listener: MyPokemonAdapter.MyCharacterItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var mycharacter: MyCharacter

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Any) {
            this.mycharacter = item as MyCharacter
            itemBinding.name.text = item.name
            itemBinding.speciesAndStatus.text = item.rename
            Glide.with(itemBinding.root)
                .load(item.url)
                .transform(CircleCrop())
                .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(mycharacter)
    }
}

