package com.reyaly.reyalyhealthtracker.screens.med

import com.reyaly.reyalyhealthtracker.model.Medication

data class MedUiState(
    val medList: MutableList<Medication> = mutableListOf(),
    val medsAreLoading: Boolean = false,

    val nameError: String? = null,
    val doseError: String? = null,
    val timeError: String? = null,
)

//data class Medication(
//    val name: String = "",
//    val dose: String = "",
//    val time: String = "",
//)