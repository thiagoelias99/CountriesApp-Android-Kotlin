package com.puc.telias.countriesapp.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.databinding.RecyclerItemListCountriesBinding
import com.puc.telias.countriesapp.models.Country

class CountriesListAdapter(
    private val context: Context,
    countriesList: List<Country?> = emptyList(),
    var itemClickHandler: (country: Country) -> Unit = {},
    var longItemClickHandler: (country: Country) -> Unit = {},
) : RecyclerView.Adapter<CountriesListAdapter.ViewHolder>() {

    private val countries = countriesList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerItemListCountriesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country!!)
    }

    override fun getItemCount(): Int = countries.size

    inner class ViewHolder(binding: RecyclerItemListCountriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country) {
            val title = itemView.findViewById<TextView>(R.id.title)
            val subTitle = itemView.findViewById<TextView>(R.id.subtitle)
            val image = itemView.findViewById<ImageView>(R.id.image)

            title.text = item.namePortuguese
            subTitle.text = item.nameUS
            image.load(item.flag)
        }

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    countries[position]?.let {
                        itemClickHandler(it)
                    }
                }
            }

            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    countries[position]?.let {
                        longItemClickHandler(it)
                    }
                }
                true
            }
        }
    }

    fun update(countries: List<Country?>) {
        this.countries.clear()
        this.countries.addAll(countries)
        notifyDataSetChanged()
    }
}
