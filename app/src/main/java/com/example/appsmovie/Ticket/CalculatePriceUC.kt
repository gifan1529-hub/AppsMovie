package com.example.appsmovie.Ticket

import javax.inject.Inject

class CalculatePriceUC @Inject constructor() {
    private val adultTicketPrice = 40.0
    private val childTicketPrice = 25.0

    operator fun invoke(
        adultTickets: Int,
        childTickets: Int,
        buffetItems: List<BuffetItem>
    ): Double {
        val ticketTotal = (adultTickets * adultTicketPrice) + (childTickets * childTicketPrice)
        val buffetTotal = buffetItems.sumOf { it.price * it.quantity }
        return ticketTotal + buffetTotal
    }
}