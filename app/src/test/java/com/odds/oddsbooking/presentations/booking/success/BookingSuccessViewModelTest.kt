package com.odds.oddsbooking.presentations.booking.success

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.odds.oddsbooking.presentations.MainCoroutineScopeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class BookingSuccessViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val onReturnToForm: Observer<Unit> = mock()

    private lateinit var viewModel: BookingSuccessViewModel

    @Before
    fun setUp() {
        viewModel = BookingSuccessViewModel()
        viewModel.onReturnToForm.observeForever(onReturnToForm)
    }

    @Test
    fun `when input data success should call onReturnToForm`() {
        //Given

        //When
        viewModel.onReturnToForm()
        //Then
        verify(onReturnToForm).onChanged(Unit)
    }
}