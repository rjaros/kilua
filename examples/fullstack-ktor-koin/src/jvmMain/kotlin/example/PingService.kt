package example

import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.RemoteData
import dev.kilua.rpc.RemoteFilter
import dev.kilua.rpc.RemoteOption
import dev.kilua.rpc.RemoteSorter
import dev.kilua.rpc.SimpleRemoteOption
import dev.kilua.rpc.types.Decimal
import dev.kilua.types.KFile
import io.ktor.server.application.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.core.annotation.Factory
import kotlin.time.Duration.Companion.seconds

@Suppress("ACTUAL_WITHOUT_EXPECT")
@Factory
actual class PingService(private val call: ApplicationCall) : IPingService {

    actual override suspend fun ping(message: String?): String {
        println(message)
        return "Hello world from server!"
    }

    actual override suspend fun getData(id: Int, name: String): MyData {
        if (id < 0) {
            throw MyFirstException("id must be positive")
        }
        if (name.isBlank()) {
            throw MySecondException("name must not be blank")
        }
        return MyData(id, name)
    }

    actual override suspend fun getDataResult(id: Int, name: String): Result<MyData> {
        try {
            return Result.success(getData(id, name))
        } catch (e: AbstractServiceException) {
            return Result.failure(e)
        }
    }

    actual override suspend fun kiluaTypes(
        files: List<KFile>,
        localDate: LocalDate,
        localTime: LocalTime,
        localDateTime: LocalDateTime,
        decimal: Decimal
    ): Result<List<LocalDate>> {
        println(files)
        println(localDate)
        println(localTime)
        println(localDateTime)
        println(decimal)
        return Result.success(listOf(localDate))
    }

    actual override suspend fun wservice(input: ReceiveChannel<Int>, output: SendChannel<String>) {
        for (i in input) {
            output.send("I'v got: $i")
        }
    }

    actual override suspend fun sseConnection(output: SendChannel<String>) {
        var i = 0
        while (true) {
            output.send("Hello world (${i++})!")
            delay(3.seconds)
        }
    }

    actual override suspend fun rowData(
        page: Int?,
        size: Int?,
        filter: List<RemoteFilter>?,
        sorter: List<RemoteSorter>?,
        state: String?
    ): RemoteData<MyData> {
        val requestedPage = (page ?: 1) - 1
        val requestedSize = size ?: 10
        val allData = listOf(
            MyData(1, "One"),
            MyData(2, "Two"),
            MyData(3, "Three"),
            MyData(4, "Four"),
            MyData(5, "Five"),
            MyData(6, "Six"),
            MyData(7, "Seven"),
            MyData(8, "Eight"),
            MyData(9, "Nine"),
            MyData(10, "Ten"),
            MyData(11, "Eleven"),
            MyData(12, "Twelve"),
        )
        val requestedData = allData.drop(requestedPage * requestedSize).take(requestedSize)
        val count = allData.size
        return RemoteData(requestedData, ((count - 1) / requestedSize) + 1, count)
    }

    actual override suspend fun dictionary(state: String?): List<SimpleRemoteOption> {
        println(state)
        println(call.request.headers.get("X-My-Header"))
        return listOf(SimpleRemoteOption("1", "One"), SimpleRemoteOption("2", "Two"))
    }

    actual override suspend fun dictionaryTs(search: String?, initial: String?, state: String?): List<RemoteOption> {
        println(state)
        println(call.request.headers.get("X-My-Header"))
        return listOf("pl" to "Poland", "uk" to "United Kingdom", "us" to "United States of America")
            .filter { (code, name) ->
                val searchCondition = search?.let { name.startsWith(it, ignoreCase = true) }
                if (initial != null) {
                    code == initial || searchCondition ?: false
                } else {
                    searchCondition ?: true
                }
            }.map { (code, name) ->
                RemoteOption(code, name)
            }
    }

    actual override suspend fun suggestionList(search: String?, state: String?): List<String> {
        println(state)
        println(call.request.headers.get("X-My-Header"))
        return listOf("Poland", "United Kingdom", "United States of America")
            .filter { it.startsWith(search ?: "", ignoreCase = true) }
    }
}
