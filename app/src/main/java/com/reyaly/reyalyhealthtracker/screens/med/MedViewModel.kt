package com.reyaly.reyalyhealthtracker.screens.med

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.reyaly.reyalyhealthtracker.model.FoodItem
import com.reyaly.reyalyhealthtracker.model.Medication
import com.reyaly.reyalyhealthtracker.storage.med.addMedicationToDates
import com.reyaly.reyalyhealthtracker.storage.med.addMedicationToMeds
import com.reyaly.reyalyhealthtracker.storage.med.deleteMedication
import com.reyaly.reyalyhealthtracker.storage.med.editMedication
import com.reyaly.reyalyhealthtracker.storage.med.getMedDateInfo
import com.reyaly.reyalyhealthtracker.storage.med.getMedications
import com.reyaly.reyalyhealthtracker.storage.med.toggleMedInDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "Med"
private val TIMES: List<String> = listOf("morning", "afternoon", "evening", "night")

class MedViewModel : ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(MedUiState())
    val uiState = _uiState.asStateFlow()

    private val _medState = MutableStateFlow(Medication())
    val medState = _medState.asStateFlow()

    private val medList
        get() = uiState.value.medList

    private val name
        get() = medState.value.name

    private val dose
        get() = medState.value.dose

    private val times
        get() = medState.value.times

    private val prescriber
        get() = medState.value.prescriber

    private val takenFor
        get() = medState.value.takenFor

    private val lastFilled
        get() = medState.value.lastFilled

    private val morning
        get() = uiState.value.morning

    private val afternoon
        get() = uiState.value.afternoon

    private val evening
        get() = uiState.value.evening

    private val night
        get() = uiState.value.night

    fun onNameChange(newValue: String) {
        _medState.value = medState.value.copy(name = newValue)
        _uiState.value = uiState.value.copy(nameError = null)
    }

    fun onDoseChange(newValue: String) {
        _medState.value = medState.value.copy(dose = newValue)
        _uiState.value = uiState.value.copy(doseError = null)
    }

    fun onPrescriberChange(newValue: String) {
        _medState.value = medState.value.copy(prescriber = newValue)
        _uiState.value = uiState.value.copy(prescriberError = null)
    }

    fun onTakenForChange(newValue: String) {
        _medState.value = medState.value.copy(takenFor = newValue)
        _uiState.value = uiState.value.copy(takenForError = null)
    }

    fun onLastFilledChange(newValue: String) {
        _medState.value = medState.value.copy(lastFilled = newValue)
        _uiState.value = uiState.value.copy(lastFilledError = null)
    }

    fun onMorningChange(checked: Boolean) {
        _uiState.value = _uiState.value.copy(
            morning = checked,
            timesError = null
        )
    }

    fun onAfternoonChange(checked: Boolean) {
        _uiState.value = _uiState.value.copy(
            afternoon = checked,
            timesError = null
        )
    }

    fun onEveningChange(checked: Boolean) {
        _uiState.value = _uiState.value.copy(
            evening = checked,
            timesError = null
        )
    }

    fun onNightChange(checked: Boolean) {
        _uiState.value = _uiState.value.copy(
            night = checked,
            timesError = null
        )
    }

    private val blankMessage = "Field cannot be blank"
    private val selectAnOption = "Please select at least one option"

    private fun validateMed(): Boolean {
        var invalidCount = 0

        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(nameError = blankMessage)
            invalidCount++
        }

        if (dose.isBlank()) {
            _uiState.value = _uiState.value.copy(doseError = blankMessage)
            invalidCount++
        }

        if (prescriber.isBlank()) {
            _uiState.value = _uiState.value.copy(prescriberError = blankMessage)
            invalidCount++
        }

        if (takenFor.isBlank()) {
            _uiState.value = _uiState.value.copy(takenForError = blankMessage)
            invalidCount++
        }

        if (lastFilled.isBlank()) {
            _uiState.value = _uiState.value.copy(lastFilledError = blankMessage)
            invalidCount++
        }

        if (!morning && !afternoon && !evening && !night) {
            _uiState.value = _uiState.value.copy(timesError = selectAnOption)
            invalidCount++
        }

        return invalidCount == 0
    }

    private fun logEverything() {
        Log.d(TAG, "name = $name")
        Log.d(TAG, "dose = $dose")
        Log.d(TAG, "prescriber = $prescriber")
        Log.d(TAG, "takenFor = $takenFor")
        Log.d(TAG, "lastFilled = $lastFilled")
        Log.d(TAG, "morning = $morning")
        Log.d(TAG, "afternoon = $afternoon")
        Log.d(TAG, "evening = $evening")
        Log.d(TAG, "night = $night")
    }

    fun clearEverything() {
        _medState.value = medState.value.copy(
            name = "",
            dose = "",
            prescriber = "",
            takenFor = "",
            lastFilled = "",
        )
        _uiState.value = uiState.value.copy(
            morning = false,
            afternoon = false,
            evening = false,
            night = false
        )
    }

    fun populateFieldsWithInitialValues(med: Medication) {
        onNameChange(med.name)
        onDoseChange(med.dose)
        onPrescriberChange(med.prescriber)
        onTakenForChange(med.takenFor)
        onLastFilledChange(med.lastFilled.replace("-", "/"))
        onMorningChange("morning" in med.times)
        onAfternoonChange("afternoon" in med.times)
        onEveningChange("evening" in med.times)
        onNightChange("night" in med.times)
    }

    suspend fun onAddNewMed(date: LocalDate): Boolean {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

        val firebaseUser = auth.currentUser!!

        logEverything()

        if (validateMed()) {
            val times: MutableList<String> = mutableListOf()

            if (morning) times.add("morning")
            if (afternoon) times.add("afternoon")
            if (evening) times.add("evening")
            if (night) times.add("night")

            val med = Medication(
                name = name.lowercase(),
                dose = dose,
                times = times,
                prescriber = prescriber,
                takenFor = takenFor,
                lastFilled = lastFilled.replace("/", "-"),
            )

            try {
                val response = addMedicationToMeds(firebaseUser.uid, med)

                med.documentId = response

                med.times.forEach { time ->
                    med.taken = false
                    med.time = time
                    addMedicationToDates(firebaseUser.uid, med, date.format(formatter), time)
                }

                medList.add(med)

                clearEverything()
                return true
            } catch (e: Exception) {
                Log.d(TAG, "an error occurred: $e")
            }
        }
        return false
    }

    private fun sortIntoTimes(meds: List<Medication>) {
        val medTimeData = MedTimeData(
            morning = mutableListOf(),
            afternoon = mutableListOf(),
            evening = mutableListOf(),
            night = mutableListOf(),
                )

        val medNamesAndTimes: HashMap<String, List<String>> = hashMapOf()

        meds.forEach { med ->
            if ("morning" in med.times) {
                medTimeData.morning.add(med)
            }
            if ("afternoon" in med.times) {
                medTimeData.afternoon.add(med)
            }
            if ("evening" in med.times) {
                medTimeData.evening.add(med)
            }
            if ("night" in med.times) {
                medTimeData.night.add(med)
            }

            medNamesAndTimes[med.name] = med.times
        }

        _uiState.value = _uiState.value.copy(
            medTimeData = medTimeData,
            medNamesAndTimes = medNamesAndTimes
            )
    }

    suspend fun getUsersMeds(date: LocalDate) {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        val firebaseUser = auth.currentUser!!

        _uiState.value = _uiState.value.copy(medsAreLoading = true)

        try {
            val meds = getMedications(firebaseUser.uid).toMutableList()

            _uiState.value = _uiState.value.copy(medList = meds)

            sortIntoTimes(meds)

            val medTimeData = MedTimeData(
                morning = mutableListOf(),
                afternoon = mutableListOf(),
                evening = mutableListOf(),
                night = mutableListOf(),
            )

            suspend fun filterAndMapData(medList: List<Medication>, time: String): MutableList<Medication> {
                val data = getMedDateInfo(firebaseUser.uid, medList, date.format(formatter), time)

                data.removeIf { med -> med.name !in uiState.value.medNamesAndTimes && uiState.value.medNamesAndTimes[med.name]?.contains(
                    time
                ) != true  }

                val dataHash: HashMap<String, HashMap<String, Any>> = hashMapOf()
                val newMedList: MutableList<Medication> = mutableListOf()

                data.forEach { med ->
                    dataHash["${med.name}-${med.time}"] = hashMapOf(
                        "taken" to med.taken!!,
                        "time" to med.time!!
                    )
                }

                medList.forEach { med ->
                    if ("${med.name}-$time" in dataHash.keys) {
                        val temp = Medication(
                            name = med.name,
                            dose = med.dose,
                            prescriber = med.prescriber,
                            takenFor = med.takenFor,
                            lastFilled = med.lastFilled,
                            times = med.times,
                            taken = dataHash["${med.name}-$time"]?.get("taken") as Boolean?,
                            time = dataHash["${med.name}-$time"]?.get("time") as String?
                        )

                        newMedList.add(temp)
                    } else {
                        addMedicationToDates(firebaseUser.uid, med, date.format(formatter), time)

                        val temp = Medication(
                            name = med.name,
                            dose = med.dose,
                            prescriber = med.prescriber,
                            takenFor = med.takenFor,
                            lastFilled = med.lastFilled,
                            times = med.times,
                            taken = false,
                            time = time
                        )

                        newMedList.add(temp)
                    }
                }

                return newMedList
            }

            TIMES.forEach { time ->
                when (time) {
                    "morning" -> {
                        medTimeData.morning = filterAndMapData(uiState.value.medTimeData.morning, "morning")
                    }
                    "afternoon" -> {
                        medTimeData.afternoon = filterAndMapData(uiState.value.medTimeData.afternoon, "afternoon")
                    }
                    "evening" -> {
                        medTimeData.evening = filterAndMapData(uiState.value.medTimeData.evening, "evening")
                    }
                    "night" -> {
                        medTimeData.night = filterAndMapData(uiState.value.medTimeData.night, "night")
                    }
                }
            }

            _uiState.value = _uiState.value.copy(
                medsAreLoading = false,
                finalMedTimeData = medTimeData
            )
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }

    private fun updateMedInUI(newMedInfo: Medication) {
        val data = uiState.value.medTimeData

        fun replace(newMedInfo: Medication, medList: MutableList<Medication>): MutableList<Medication> {
            medList.forEach { med ->
                if (med.name == newMedInfo.name) {
                    med.taken = newMedInfo.taken
                    med.dose = newMedInfo.dose
                    med.prescriber = newMedInfo.prescriber
                    med.takenFor = newMedInfo.takenFor
                    med.lastFilled = newMedInfo.lastFilled
                    med.times = newMedInfo.times
                }
            }

            return medList
        }

        when (newMedInfo.time) {
            "morning" -> {
                data.morning = replace(newMedInfo, uiState.value.medTimeData.morning)
            }
            "afternoon" -> {
                data.afternoon = replace(newMedInfo, uiState.value.medTimeData.afternoon)
            }
            "evening" -> {
                data.evening = replace(newMedInfo, uiState.value.medTimeData.evening)
            }
            "night" -> {
                data.night = replace(newMedInfo, uiState.value.medTimeData.night)
            }
        }

        _uiState.value = _uiState.value.copy(medTimeData = data)
    }

    suspend fun toggleMed(med: Medication, date: LocalDate, time: String) {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
        val firebaseUser = auth.currentUser!!

        try {
            med.taken = !med.taken!!

            toggleMedInDB(firebaseUser.uid, med, date.format(formatter), time)

            updateMedInUI(med)
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }

    suspend fun deleteMed(med: Medication) {
        val firebaseUser = auth.currentUser!!

        try {
            deleteMedication(firebaseUser.uid, med.name.lowercase())
        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
    }

    suspend fun onEditMed(prevMed: Medication, date: LocalDate): Boolean {
        val firebaseUser = auth.currentUser!!
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

        try {
            if (prevMed.name != name) {
                deleteMedication(firebaseUser.uid, prevMed.name.lowercase())

                onAddNewMed(date)
                return true
            } else {
                val times: MutableList<String> = mutableListOf()

                if (morning) times.add("morning")
                if (afternoon) times.add("afternoon")
                if (evening) times.add("evening")
                if (night) times.add("night")

                val changedMed = Medication(
                    name = name.lowercase(),
                    dose = dose,
                    times = times,
                    prescriber = prescriber,
                    takenFor = takenFor,
                    lastFilled = lastFilled.replace("/", "-"),
                )

                logEverything()

                editMedication(firebaseUser.uid, changedMed)

                changedMed.times.forEach { time ->
                    changedMed.taken = false
                    changedMed.time = time

                    addMedicationToDates(firebaseUser.uid, changedMed, date.format(formatter), time)
                }

                clearEverything()
                return true
            }

        } catch (e: Exception) {
            Log.d(TAG, "an error occurred: $e")
        }
        return false
    }
}