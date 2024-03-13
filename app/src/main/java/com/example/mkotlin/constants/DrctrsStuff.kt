package com.example.mkotlin.constants

object DrctrsStuff {

    // OBRECHENNIE
    const val drctr_ua = "Zelensky"
    const val drctr_ru = "Putin"
    const val drctr_us = "Biden"
    const val drctr_fr = "Macron"
    const val drctr_nk = "Un"

    // OBRECHONNOST
    const val drctr_ua_cn = "Dead"
    const val drctr_ru_cn = "Alive"
    const val drctr_us_cn = "Critical"
    const val drctr_fr_cn = "Respond"
    const val drctr_nk_cn = "Kaboom"

    // OBRECHENIE
    const val nuke = "nuke"
    const val nuke_code = "666"
    var is_nuke = false

    //STUFF
    lateinit var city_was: String
    lateinit var city_before: String
    var isclick = false
    var needanimation = false
    var appearance = true
    var is_bottom_open = false
    var can_spinner_refresh = false
    var current_item = 0

    val listOfGlory = listOf("слав", "glory", "москал")
    val listOfUa = listOf("укра", " ua", "ukrain", "зсу", "всу", "apu", "afu", "uaf",
        "нации", "нації", "гиляк", "гілляк", "гилляк", "гіляк")
    val listNorm = listOf("россии", "russ", "белорус", "belaru")
}