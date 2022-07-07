package com.odds.oddsbooking.utils

import com.odds.oddsbooking.utils.DateUtilities.getTimeSlot
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class DateUtilitiesTest{
    @Test
    fun`when click dialog should call getTimeSlot & return timeSlot`(){
        //Given
        val startHr = "09:00"
        val endHr = "21:00"
        val timeSlot = arrayOf(
            "9:00", "9:30",
            "10:00", "10:30",
            "11:00", "11:30",
            "12:00", "12:30",
            "13:00", "13:30",
            "14:00", "14:30",
            "15:00", "15:30",
            "16:00", "16:30",
            "17:00", "17:30",
            "18:00", "18:30",
            "19:00", "19:30",
            "20:00", "20:30",
            "21:00"
        )
        //When
        val newTimeSlot = getTimeSlot(startHr,endHr)
        //Then
        assertArrayEquals(timeSlot, newTimeSlot)
    }
    @Test
    fun `when startTime and endTime has same hrs and same date`() {
        //Given
        val startTime = "10:00"
        val endTime = "10:30"
        val timeSlot = arrayOf(
            "10:00"
        )
        //When
        val newTimeSlot = getTimeSlot(startTime, endTime)
        //Then
        assertArrayEquals(timeSlot, newTimeSlot)
    }
}