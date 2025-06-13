import com.mindera.base.models.ErrorTypes
import com.mindera.data.mapper.mapThrowable
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import java.io.IOException

class ThrowableMapperTest {

    @Test
    fun `IOException maps to ErrorTypes_IoException`() {
        val exception = IOException()
        val result = exception.mapThrowable()
        assertEquals(ErrorTypes.IoException, result)
    }

/*    @Test
    fun `HttpException maps to ErrorTypes_Http`() {
        val httpException = mockk<HttpException>(relaxed = true)
        every { httpException.code() } returns 404
        every { httpException.message } returns "Not Found"

        val result = httpException.mapThrowable()

        assertEquals(ErrorTypes.Http(404, "Not Found"), result)
    }*/

    @Test
    fun `Unknown exception maps to ErrorTypes_Unknown`() {
        val exception = IllegalStateException("Something went wrong")
        val result = exception.mapThrowable()
        assertEquals(ErrorTypes.Unknown("Something went wrong"), result)
    }

    @Test
    fun `Unknown exception with null message maps to ErrorTypes_Unknown with null`() {
        val exception = object : Throwable() {
            override val message: String? = null
        }
        val result = exception.mapThrowable()
        assertEquals(ErrorTypes.Unknown(null), result)
    }
}
