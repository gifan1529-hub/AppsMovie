package com.example.core.UseCase_Repository.Tickets

import javax.inject.Inject

class ValidateSeatUC @Inject constructor() {

    operator fun invoke(totalTickets: Int, selectedSeats: Int): Boolean {
        return totalTickets > 0 && selectedSeats == totalTickets
    }
}