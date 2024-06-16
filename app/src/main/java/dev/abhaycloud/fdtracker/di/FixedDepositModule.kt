package dev.abhaycloud.fdtracker.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.abhaycloud.fdtracker.data.alarm.AlarmScheduler
import dev.abhaycloud.fdtracker.data.local.database.FixedDepositDatabase
import dev.abhaycloud.fdtracker.data.repository.FixedDepositRepositoryImpl
import dev.abhaycloud.fdtracker.domain.notification.FixedDepositNotificationManager
import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import dev.abhaycloud.fdtracker.domain.usecase.AddFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.DeleteFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetAllFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetTotalInvestedAmountUseCase
import dev.abhaycloud.fdtracker.domain.usecase.UpdateFixedDepositUseCase
import dev.abhaycloud.fdtracker.presentation.ui.add.AddFixedDepositViewModel
import dev.abhaycloud.fdtracker.presentation.ui.home.HomeScreenViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FixedDepositModule {

    @Provides
    @Singleton
    fun providesAppDatabase(app: Application): FixedDepositDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            FixedDepositDatabase::class.java,
            "fdDB"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFixedDepositRepository(appDatabase: FixedDepositDatabase): FixedDepositRepository {
        return FixedDepositRepositoryImpl(appDatabase.fixedDepositDao)
    }

    @Provides
    @Singleton
    fun providesAddFixedDepositUseCase(repository: FixedDepositRepository): AddFixedDepositUseCase {
        return AddFixedDepositUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesUpdateFixedDepositUseCase(repository: FixedDepositRepository): UpdateFixedDepositUseCase {
        return UpdateFixedDepositUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetAllFixedDepositUseCase(repository: FixedDepositRepository): GetAllFixedDepositUseCase {
        return GetAllFixedDepositUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesDeleteFixedDepositUseCase(repository: FixedDepositRepository): DeleteFixedDepositUseCase {
        return DeleteFixedDepositUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesAlarmScheduler(@ApplicationContext context: Context): AlarmScheduler {
        return AlarmScheduler(context)
    }

    @Provides
    @Singleton
    fun providesFixedDepositNotificationManager(alarmScheduler: AlarmScheduler): FixedDepositNotificationManager {
        return FixedDepositNotificationManager(alarmScheduler)
    }

    @Provides
    @Singleton
    fun providesGetTotalInvestedAmountUseCase(repository: FixedDepositRepository): GetTotalInvestedAmountUseCase {
        return GetTotalInvestedAmountUseCase(repository)
    }

    @Provides
    fun providesAddFixedDepositViewModel(
        addFixedDepositUseCase: AddFixedDepositUseCase,
        updateFixedDepositUseCase: UpdateFixedDepositUseCase,
        deleteFixedDepositUseCase: DeleteFixedDepositUseCase,
        notificationManager: FixedDepositNotificationManager
    ): AddFixedDepositViewModel {
        return AddFixedDepositViewModel(
            addFixedDepositUseCase,
            updateFixedDepositUseCase,
            deleteFixedDepositUseCase,
            notificationManager
        )
    }

    @Provides
    @Singleton
    fun providesHomeScreenViewModel(
        getAllFixedDepositUseCase: GetAllFixedDepositUseCase,
        getTotalInvestedAmountUseCase: GetTotalInvestedAmountUseCase
    ): HomeScreenViewModel {
        return HomeScreenViewModel(getAllFixedDepositUseCase, getTotalInvestedAmountUseCase)
    }
}