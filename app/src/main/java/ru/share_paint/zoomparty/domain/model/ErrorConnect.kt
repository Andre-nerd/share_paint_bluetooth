package ru.share_paint.zoomparty.domain.model

sealed class ErrorConnect(val message: String, val name:String) {
    data class MasterError(val m: String) : ErrorConnect(m,"")
    data class SlaveError(val m: String) : ErrorConnect(m,"")
    data class DisconnectSlaveError(val m: String): ErrorConnect(m,"Мастер устройство отключено")
    data class DisconnectMasterError(val m: String): ErrorConnect(m,"Игрок отключен")
    object  NoError : ErrorConnect("","No error")
}