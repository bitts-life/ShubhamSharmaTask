package xyz.bittslife.shubhamsharmatask.di

import android.content.Context
import androidx.room.Room
import com.bittslife.shubhamsharmademotest.data.db.HoldingsDatabase
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.jvm.java

class DataModuleTest {
    @Test
    fun testProvideDb() {
        val context = mock(Context::class.java)
        val db = Room.inMemoryDatabaseBuilder(context, HoldingsDatabase::class.java).build()
        assertNotNull(db)
    }
}
