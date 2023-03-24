package feature.person_health

import ModelState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate

class PersonHealthComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    val modelState = instanceKeeper.getOrCreate { PersonHealthModelState() }
}

class PersonHealthModelState : ModelState() {

    private val _age = mutableStateOf(23)
    var age
        get() = _age.value
        set(value) {
            _age.value = value
            caculateBMI()
        }
    private val _height = mutableStateOf(180.0f)
    var height
        get() = _height.value
        set(value) {
            _height.value = value
            caculateBMI()
        }

    private val _weight = mutableStateOf(90f)
    var weight
        get() = _weight.value
        set(value) {
            _weight.value = value
            caculateBMI()
        }

    private val _expectedWeight = mutableStateOf(80f)
    var expectedWeight
        get() = _expectedWeight.value
        set(value) {
            _expectedWeight.value = value
            caculateBMI()
        }

    private var _bmi = mutableStateOf(1f)
    val bmi get() = _bmi.value

    fun caculateBMI() {
        val heightInMeters = height / 100
        val bmi0 = weight / (heightInMeters * heightInMeters)
        _bmi.value = bmi0
    }

    fun saveData() {

    }

    init {
        caculateBMI()
    }
}
