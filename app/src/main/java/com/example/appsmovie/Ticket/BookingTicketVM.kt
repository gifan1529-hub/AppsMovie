package com.example.appsmovie.Ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsmovie.RoomDatabase.BookingHistoryDao
import com.example.appsmovie.Seat.Seat
import kotlinx.coroutines.launch

data class BuffetItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    var quantity: Int = 0
)

data class BookingData(
    var movieId: String? = null,
    var movieTitle: String? = null,
    var moviePosterUrl: String? = null,
    var movieGenre: String? = null,
    var theater: String? = null,
    var session: String? = null,
    var adultTickets: Int = 0,
    var childTickets: Int = 0,
    var buffetSubtotal: Double = 0.0,
    var selectedSeats: MutableSet<String> = mutableSetOf(),
    var selectedBuffet: String? = "None",
    var totalPrice: Double = 0.0
)

class BookingTicketVM (private val BookingDao: BookingHistoryDao) : ViewModel() {
    private val _areSeatsValid = MutableLiveData<Boolean>(false)
    val areSeatsValid: LiveData<Boolean> get() = _areSeatsValid

    private val _isTheaterSelected = MutableLiveData<Boolean>(false)
    val isTheaterSelected: LiveData<Boolean> get() = _isTheaterSelected

    private val _isSessionSelected = MutableLiveData<Boolean>(false)
    val isSessionSelected: LiveData<Boolean> get() = _isSessionSelected

    private val _buffetMenuList = MutableLiveData<List<BuffetItem>>()
    val buffetMenuList: LiveData<List<BuffetItem>> = _buffetMenuList
    val paymentTrigger = MutableLiveData<Boolean>()
    private val _takenSeats = MutableLiveData<Set<String>>()
    val takenSeats: LiveData<Set<String>> = _takenSeats
    private val _bookingData = MutableLiveData<BookingData>()
    val bookingData: LiveData<BookingData> = _bookingData

    init {
        _bookingData.value = BookingData()
        loadBuffetMenu()
    }

    private fun loadBuffetMenu() {
       _buffetMenuList.value = listOf(
            BuffetItem(1, "Large Menu", "Large Popcorn\nLarge Coco Cola (400 ml.)", 30.0, "https://media.istockphoto.com/id/681903568/photo/popcorn-in-box-with-cola.jpg?s=612x612&w=0&k=20&c=0rXGh6COImJ8iYpv99Yt2dQOyMneVw_rJw6QwsZPrh4="),
            BuffetItem(2, "Medium Menu", "Medium Popcorn\nMedium Coco Cola (330 ml.)", 20.0, "https://media.istockphoto.com/id/681903568/photo/popcorn-in-box-with-cola.jpg?s=612x612&w=0&k=20&c=0rXGh6COImJ8iYpv99Yt2dQOyMneVw_rJw6QwsZPrh4="),
            BuffetItem(3, "Small Menu", "Small Popcorn\nSmall Coco Cola (250 ml.)", 15.0, "https://media.istockphoto.com/id/681903568/photo/popcorn-in-box-with-cola.jpg?s=612x612&w=0&k=20&c=0rXGh6COImJ8iYpv99Yt2dQOyMneVw_rJw6QwsZPrh4=")
        )
    }

    fun onConfirmPaymentClicked() {
        paymentTrigger.value = true
    }

    fun onPaymentFinished() {
        paymentTrigger.value = false
    }

    private fun updateBuffetAndRecalculate() {
        val currentData = _bookingData.value ?: return
        var buffetTotal = 0.0
        _buffetMenuList.value?.forEach { buffetItem ->
            buffetTotal += buffetItem.price * buffetItem.quantity
        }
        currentData.buffetSubtotal = buffetTotal
        recalculateTotalPrice()
    }

    private fun recalculateTotalPrice() {
        val currentData = _bookingData.value ?: return
        val adultTicketPrice = 40.0
        val childTicketPrice = 25.0
        val ticketTotal = (currentData.adultTickets * adultTicketPrice) + (currentData.childTickets * childTicketPrice)
        val newTotal = ticketTotal + currentData.buffetSubtotal
        currentData.totalPrice = newTotal
 _bookingData.postValue(currentData)
    }


    fun removeBuffetItem(item: BuffetItem) {
        val menuList = _buffetMenuList.value ?: return
        menuList.find { it.id == item.id }?.let {
            if (it.quantity > 0) { it.quantity-- }
        }
        _buffetMenuList.postValue(menuList)
        updateBuffetAndRecalculate()
    }

    fun addBuffetItem(item: BuffetItem) {
        val menuList = _buffetMenuList.value ?: return
        menuList.find { it.id == item.id }?.quantity++
        _buffetMenuList.postValue(menuList)
        updateBuffetAndRecalculate()
    }

    private fun validateSeatSelection() {
        val data = _bookingData.value ?: return
        val totalTickets = data.adultTickets + data.childTickets
        val totalSeatsSelected = data.selectedSeats.size

        _areSeatsValid.value = totalTickets > 0 && totalSeatsSelected == totalTickets
    }

    fun addAdultTicket() {
        val currentData = _bookingData.value ?: return
        currentData.adultTickets++
        recalculateTotalPrice()
         _bookingData.postValue(currentData)
        validateSeatSelection()
    }

    fun removeAdultTicket() {
        val currentData = _bookingData.value ?: return
        if (currentData.adultTickets > 0) {
            currentData.adultTickets--
            recalculateTotalPrice()
            _bookingData.postValue(currentData)
        }
        validateSeatSelection()
    }

    fun addChildTicket() {
        val currentData = _bookingData.value ?: return
        currentData.childTickets++
        recalculateTotalPrice()
        _bookingData.postValue(currentData)
        validateSeatSelection()
    }

    fun removeChildTicket() {
        val currentData = _bookingData.value ?: return
        if (currentData.childTickets > 0) {
            currentData.childTickets--
            recalculateTotalPrice()
            _bookingData.postValue(currentData)
        }
        validateSeatSelection()
    }

    fun confirmBuffetSelection() {
        val currentBookingData = _bookingData.value ?: return
        val selectedBuffetItems = _buffetMenuList.value?.filter { it.quantity > 0 }
        currentBookingData.selectedBuffet = selectedBuffetItems?.joinToString("\n") {
            "${it.quantity}x ${it.name}"
        }?.takeIf { it.isNotBlank() } ?: "None"
        _bookingData.postValue(currentBookingData)
    }

    fun setInitialMovieData(id: String?, title: String?, posterUrl: String?, genre: String?) {
        val currentData = _bookingData.value ?: BookingData()
        currentData.movieId = id
        currentData.movieTitle = title
        currentData.moviePosterUrl = posterUrl
        currentData.movieGenre = genre
        _bookingData.value = currentData
    }

    fun setTheater(theaterName: String) {
        val currentData = _bookingData.value ?: return
        currentData.theater = theaterName
        _bookingData.postValue(currentData)
        _isTheaterSelected.value = true
    }


    fun setSession(sessionTime: String) {
        val currentData = _bookingData.value ?: return
        currentData.session = sessionTime
        _bookingData.postValue(currentData)
        _isSessionSelected.value = true
    }

    fun onSeatSelected(seat: Seat) {
        val currentData = _bookingData.value ?: return
        val totalTickets = currentData.adultTickets + currentData.childTickets
        if (currentData.selectedSeats.contains(seat.id)) {
            currentData.selectedSeats.remove(seat.id)
        } else {
            if (currentData.selectedSeats.size < totalTickets) {
                currentData.selectedSeats.add(seat.id)
            }
        }
        _bookingData.postValue(currentData)
    }

    fun resetBookingData() {
        _bookingData.value = BookingData()
        _isSessionSelected.value = false
        _isTheaterSelected.value = false
        _areSeatsValid.value = false
    }

    fun fetchTakenSeats(movieId: String, theater: String, session: String) {
        viewModelScope.launch {
            val listOfSeatLists = BookingDao.getTakenSeatsForShow(movieId, theater, session)
            val allTakenSeats = listOfSeatLists.flatMap { it.split(",") }.toSet()
            _takenSeats.postValue(allTakenSeats)
        }
    }
}
