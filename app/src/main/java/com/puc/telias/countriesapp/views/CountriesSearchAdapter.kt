package com.puc.telias.countriesapp.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.databinding.RecyclerItemSearchCountriesBinding
import com.puc.telias.countriesapp.models.Country

class CountriesSearchAdapter (
    private val context: Context,
    countriesList: List<Country?> = emptyList()
) : RecyclerView.Adapter<CountriesSearchAdapter.ViewHolder>() {

    private val countries = countriesList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemSearchCountriesBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country!!)
    }

    override fun getItemCount(): Int = countries.size

    inner class ViewHolder(binding: RecyclerItemSearchCountriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country) {
            val name = itemView.findViewById<TextView>(R.id.country_name)
//            val state = itemView.findViewById<TextView>(R.id.city_state)

            name.text = item.namePortuguese
//            state.text = item.estado
        }

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val clickedCity = countries[position]
                    Toast.makeText(context, "Clicou em ${clickedCity!!.namePortuguese}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun update(cities: List<Country?>) {
        this.countries.clear()
        this.countries.addAll(cities)
        notifyDataSetChanged()
    }
}