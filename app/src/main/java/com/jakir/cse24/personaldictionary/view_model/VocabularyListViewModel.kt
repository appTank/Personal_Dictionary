package com.jakir.cse24.personaldictionary.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakir.cse24.personaldictionary.data.model.Vocabulary
import com.jakir.cse24.personaldictionary.data.repositories.VocabularyListRepository

class VocabularyListViewModel : ViewModel(){
  val vocabularies: LiveData<List<Vocabulary>> = MutableLiveData<List<Vocabulary>>()
      .apply { value = VocabularyListRepository()
          .getVocabularies() }
}