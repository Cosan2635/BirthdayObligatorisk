package com.example.birthdayobligatorisk.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.birthdayobligatorisk.repository.PersonsRepository

class PersonsViewModel : ViewModel() {
    private val repository = PersonsRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personsLiveData
    var errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPerson()
    }

    operator fun get(index: Int): Person? {
        return personsLiveData.value?.get(index)
    }

    fun add(person: Person) {
        repository.add(person)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(id: Int, updatedPerson: Person) {
        repository.update(id, updatedPerson)
    }

    fun sortByName() {
        repository.sortByName()
    }

    fun sortByAge() {
        repository.sortByAge()
    }
}
