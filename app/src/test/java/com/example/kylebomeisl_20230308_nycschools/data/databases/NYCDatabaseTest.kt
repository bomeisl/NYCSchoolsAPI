package com.example.kylebomeisl_20230308_nycschools.data.databases

import androidx.compose.runtime.collectAsState
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

class NYCDatabaseTest {

    @RunWith(RobolectricTestRunner::class)
    class NYCDatabaseTest {
        private lateinit var satDao: SATDao
        private lateinit var schoolDao: SchoolDao
        private lateinit var db: NYCDatabase
        val scope = TestScope()

        @Before
        fun createDb() {
            Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))
            val context = ApplicationProvider.getApplicationContext<android.content.Context>()
            db = Room.inMemoryDatabaseBuilder(
                context, NYCDatabase::class.java
            ).build()
            satDao = db.satDao()
            schoolDao = db.schoolDao()

        }

        @After
        fun resetCoroutines() {
            Dispatchers.resetMain()
        }

        fun closeDb() {
            db.close()
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun testSchoolWriteAndRead() {
            val school: School_DB = School_DB(
                "Jr High",
                "Test School",
                "Testland"
            )
            val job = scope.runTest {
                withContext(Dispatchers.IO) {
                    schoolDao.upsertSchool(school)
                    schoolDao.dbGetAllSchools().collect {
                        assert(it.contains(school))
                    }
                }
            }
        }


        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun testSATWriteAndRead() {
            val sat: SAT_DB = SAT_DB(
                "Test High School",
                "800",
                "680",
                "700"
            )
            val job = scope.runTest {
                withContext(Dispatchers.IO) {
                    satDao.upsertSAT(sat)
                    satDao.dbGetAllSATs().collect {
                        assert(it.contains(sat))
                    }
                }
            }
        }
    }

}