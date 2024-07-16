package com.reyaly.reyalyhealthtracker.screens.med

import com.reyaly.reyalyhealthtracker.model.Medication

data class MedUiState(
    val medList: MutableList<Medication> = mutableListOf(),
    val medTimeData: MedTimeData = MedTimeData(),
    val finalMedTimeData: MedTimeData = MedTimeData(),
    val medsAreLoading: Boolean = false,

    val medNamesAndTimes: HashMap<String, List<String>> = hashMapOf(),

    val times: MutableList<String> = mutableListOf(),

    var morning: Boolean = false,
    var afternoon: Boolean = false,
    var evening: Boolean = false,
    var night: Boolean = false,

    val nameError: String? = null,
    val doseError: String? = null,
    val timeError: String? = null,
    var timesError: String? = null,
    var prescriberError: String? = null,
    var takenForError: String? = null,
    var takenError: String? = null,
    var lastFilledError: String? = null,
)

data class MedTimeData(
    var morning: MutableList<Medication> = mutableListOf(),
    var afternoon: MutableList<Medication> = mutableListOf(),
    var evening: MutableList<Medication> = mutableListOf(),
    var night: MutableList<Medication> = mutableListOf(),
)

