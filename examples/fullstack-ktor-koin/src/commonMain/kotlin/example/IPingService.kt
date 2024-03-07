package example

import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.annotations.RpcService
import dev.kilua.rpc.annotations.RpcServiceException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.serialization.Serializable
import dev.kilua.rpc.RemoteData
import dev.kilua.rpc.RemoteFilter
import dev.kilua.rpc.RemoteSorter
import dev.kilua.rpc.types.Decimal
import dev.kilua.types.KFile
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@RpcServiceException
class MyFirstException(override val message: String) : AbstractServiceException()

@RpcServiceException
class MySecondException(override val message: String) : AbstractServiceException()

@Serializable
data class MyData(val id: Int, val name: String)

@RpcService
interface IPingService {
    suspend fun ping(message: String? = null): String
    suspend fun getData(id: Int, name: String): MyData
    suspend fun getDataResult(id: Int, name: String): Result<MyData>

    suspend fun kiluaTypes(
        files: List<KFile>,
        localDate: LocalDate,
        localTime: LocalTime,
        localDateTime: LocalDateTime,
        decimal: Decimal
    ): Result<List<LocalDate>>

    suspend fun wservice(input: ReceiveChannel<Int>, output: SendChannel<String>) {}
    suspend fun wservice(handler: suspend (SendChannel<Int>, ReceiveChannel<String>) -> Unit) {}

    suspend fun sseConnection(output: SendChannel<String>) {}
    suspend fun sseConnection(handler: suspend (ReceiveChannel<String>) -> Unit) {}

    suspend fun rowData(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<MyData>
}
