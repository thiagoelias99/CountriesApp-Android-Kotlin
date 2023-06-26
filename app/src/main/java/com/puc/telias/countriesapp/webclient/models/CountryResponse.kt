package com.puc.telias.countriesapp.webclient.models

import com.puc.telias.countriesapp.models.Country

class CountryResponse(
    val name: Name,
//    val tld: List<String>,
//    val cca2: String,
//    val ccn3: String,
    val cca3: String,
//    val cioc: String,
//    val independent: Boolean,
//    val status: String,
//    val unMember: Boolean,
//    val currencies: Currencies,
//    val idd: Idd,
//    val capital: List<String>,
//    val altSpellings: List<String>,
//    val region: String,
//    val subregion: String,
//    val languages: Languages,
//    val translations: Map<String, Translation>,
//    val latlng: List<Long>,
//    val landlocked: Boolean,
//    val borders: List<String>,
//    val area: Long,
//    val demonyms: Demonyms,
//    val flag: String,
//    val maps: Maps,
//    val population: Long,
//    val gini: Gini,
//    val fifa: String,
//    val car: Car,
//    val timezones: List<String>,
//    val continents: List<String>,
//    val flags: Flags,
//    val coatOfArms: CoatOfArms,
//    val startOfWeek: String,
//    val capitalInfo: CapitalInfo,
//    val postalCode: PostalCode
) {
    val country: Country?
        get() = Country(
            code = cca3,
            namePortuguese = "",
            nameUS = "",
            nameLocal = "",
            nameComplete = "",
            currency = "",
            capital = "",
            region = "",
            languages = "",
            avatar = "",
            area = 0.0,
            population = 0.0,
            flag = "",
            coatOfArms = ""
        )
}

//data class CapitalInfo (
//    val latlng: List<Double>
//)
//
//data class Car (
//    val signs: List<String>,
//    val side: String
//)
//
//data class CoatOfArms (
//    val png: String,
//    val svg: String
//)
//
//data class Currencies (
//    @Json(name = "BRL")
//    val brl: Brl
//)
//
//data class Brl (
//    val name: String,
//    val symbol: String
//)
//
//data class Demonyms (
//    val eng: Eng,
//    val fra: Eng
//)
//
//data class Eng (
//    val f: String,
//    val m: String
//)
//
//data class Flags (
//    val png: String,
//    val svg: String,
//    val alt: String
//)
//
//data class Gini (
//    @Json(name = "2019")
//    val the2019: Double
//)
//
//data class Idd (
//    val root: String,
//    val suffixes: List<String>
//)
//
//data class Languages (
//    val por: String
//)
//
//data class Maps (
//    val googleMaps: String,
//    val openStreetMaps: String
//)
//
data class Name (
    val common: String,
    val official: String,
//    val nativeName: NativeName
)
//
//data class NativeName (
//    val por: Translation
//)
//
//data class Translation (
//    val official: String,
//    val common: String
//)
//
//data class PostalCode (
//    val format: String,
//    val regex: String
//)