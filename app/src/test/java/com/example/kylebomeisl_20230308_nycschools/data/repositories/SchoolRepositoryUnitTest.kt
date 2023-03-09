package com.example.kylebomeisl_20230308_nycschools.data.repositories

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import com.example.kylebomeisl_20230308_nycschools.data.databases.NYCDatabase
import com.example.kylebomeisl_20230308_nycschools.data.databases.SchoolDao
import com.example.kylebomeisl_20230308_nycschools.data.databases.School_DB
import com.example.kylebomeisl_20230308_nycschools.data.network.SchoolNetworkDataSource
import com.example.kylebomeisl_20230308_nycschools.data.network.School_network
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

//This Unit test is technically truistic since
//I mocked up the input and output
//since I already unit tested the database
//and the network API client, there's
//no point in doing an instrumental
//test here with a real database and
//real network call,
//but I just want to demonstrate that
//I understand mocking and stubbing
@RunWith(RobolectricTestRunner::class)
class SchoolRepositoryUnitTest {

    @ExperimentalCoroutinesApi
    @Config(manifest= Config.NONE)
    @RunWith(RobolectricTestRunner::class)
    class ItemsRepositoryTest {

        private val scope = TestScope()

        @MockK
        lateinit var schoolDao1: SchoolDao

        @MockK
        lateinit var schoolNetworkDataSource1: SchoolNetworkDataSource

        @Before
        fun createDispaters() {
            Dispatchers.setMain(StandardTestDispatcher(scope.testScheduler))

        }

        @After
        fun resetCoroutines() {
            Dispatchers.resetMain()
        }

        @Test
        fun databaseRefreshTest() {
            schoolDao1 = mockk<SchoolDao>()
            schoolNetworkDataSource1 = mockk<SchoolNetworkDataSource>()

            val mockedNetworkResponse =
                mutableListOf<School_network>(
                    School_network(
                        "test1",
                    "just a test stub school",
                    "nowhere"),
                    School_network(
                        "test2",
                    "another stub school",
                    "not a real school"),
                    School_network(
                        "test3",
                        "third stub school",
                        "not physically localized"
                    )
                )


            coEvery { schoolNetworkDataSource1.ktorGetSchoolList() } returns mockedNetworkResponse
            coEvery { schoolDao1.dbGetAllSchools() } returns

            val schoolRepo: SchoolRepository = SchoolRepository(
                schoolDao1, schoolNetworkDataSource1
            )

            val job = scope.runTest {
                withContext(Dispatchers.IO) {
                    schoolRepo.refreshDb()
                    Assert.assertEquals(schoolRepo.getAllSchools(), mockedNetworkResponse)
                }
            }
        }

    }
}