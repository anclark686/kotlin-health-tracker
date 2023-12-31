package com.reyaly.reyalyhealthtracker.model.service

interface LogService {
  fun logNonFatalCrash(throwable: Throwable)
}
