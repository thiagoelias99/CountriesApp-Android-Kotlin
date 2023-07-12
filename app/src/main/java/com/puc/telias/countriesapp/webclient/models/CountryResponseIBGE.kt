package com.puc.telias.countriesapp.webclient.models

import com.puc.telias.countriesapp.models.Country
import java.util.UUID

data class CountryResponseIBGE(
//    val id: CountryResponseIBGEID,
//    val nome: Nome,
    val area: Area,
//    val localizacao: Localizacao,
    val linguas: List<Lingua>,
    val governo: Governo,

    val unidadesMonetarias: List<UnidadesMonetaria>,

    val historico: String
) {
    val country: Country?
        get() = Country(
            code = "",
            namePortuguese = "",
            nameUS = "",
            nameLocal = "",
            nameComplete = "",
            currency = unidadesMonetarias?.get(0)?.nome ?: "",
            capital = governo.capital.nome ?: "",
            region = "",
            languages = linguas[0]?.nome ?: "",
            area = 0.0,
//            area = area.total.toDouble() ?: 0.0,
            population = 0.0,
            flag = "",
            coatOfArms = "",
            history = historico,
            uuid = UUID.randomUUID()
        )
}

data class Area(
    val total: String,
    val unidade: Unidade
)

data class Unidade(
    val nome: String,
    val símbolo: String,
    val multiplicador: Long
)

data class Governo(
    val capital: Capital
)

data class Capital(
    val nome: String
)

//data class CountryResponseIBGEID (
//    @Json(name = "M49")
//    val m49: Long,
//
//    @Json(name = "ISO-3166-1-ALPHA-2")
//    val iso31661Alpha2: String,
//
//    @Json(name = "ISO-3166-1-ALPHA-3")
//    val iso31661Alpha3: String
//)

data class Lingua(
//    val id: LinguaID,
    val nome: String
)

//data class LinguaID (
//    @Json(name = "ISO-639-1")
//    val iso6391: String,
//
//    @Json(name = "ISO-639-2")
//    val iso6392: String
//)

//data class Localizacao (
//    val regiao: Regiao,
//
//    @Json(name = "sub-regiao")
//    val subRegiao: Regiao,
//
//    @Json(name = "regiao-intermediaria")
//    val regiaoIntermediaria: Regiao
//)

//data class Regiao (
//    val id: RegiaoID,
//    val nome: String
//)
//
//data class RegiaoID (
//    @Json(name = "M49")
//    val m49: Long
//)
//
//data class Nome (
//    val abreviado: String,
//
//    @Json(name = "abreviado-EN")
//    val abreviadoEN: String,
//
//    @Json(name = "abreviado-ES")
//    val abreviadoES: String
//)

data class UnidadesMonetaria(
//    val id: UnidadesMonetariaID,
    val nome: String
)

//data class UnidadesMonetariaID (
//    @Json(name = "ISO-4217-ALPHA")
//    val iso4217Alpha: String,
//
//    @Json(name = "ISO-4217-NUMERICO")
//    val iso4217Numerico: String
//)
