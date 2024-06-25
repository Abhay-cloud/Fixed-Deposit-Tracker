package dev.abhaycloud.fdtracker.di

import android.app.Application
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.abhaycloud.fdtracker.data.alarm.AlarmScheduler
import dev.abhaycloud.fdtracker.data.local.database.FixedDepositDatabase
import dev.abhaycloud.fdtracker.data.preferences.PreferencesDataSource
import dev.abhaycloud.fdtracker.data.repository.FixedDepositRepositoryImpl
import dev.abhaycloud.fdtracker.data.repository.PreferencesRepositoryImpl
import dev.abhaycloud.fdtracker.domain.notification.FixedDepositNotificationManager
import dev.abhaycloud.fdtracker.domain.repository.FixedDepositRepository
import dev.abhaycloud.fdtracker.domain.repository.PreferencesRepository
import dev.abhaycloud.fdtracker.domain.usecase.AddFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.DeleteAllFixedDepositsUseCase
import dev.abhaycloud.fdtracker.domain.usecase.DeleteFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetAllFixedDepositUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetDarkModeUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetDynamicColorUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetTotalInvestedAmountUseCase
import dev.abhaycloud.fdtracker.domain.usecase.GetTotalMaturityAmountUseCase
import dev.abhaycloud.fdtracker.domain.usecase.SetDarkModeUseCase
import dev.abhaycloud.fdtracker.domain.usecase.SetDynamicColorUseCase
import dev.abhaycloud.fdtracker.domain.usecase.UpdateFixedDepositUseCase
import dev.abhaycloud.fdtracker.presentation.ui.add.AddFixedDepositViewModel
import dev.abhaycloud.fdtracker.presentation.ui.home.HomeScreenViewModel
import dev.abhaycloud.fdtracker.presentation.ui.settings.SettingScreenViewModel
import dev.abhaycloud.fdtracker.presentation.ui.settings.ThemeViewModel
import dev.abhaycloud.fdtracker.presentation.ui.widget.FixedDepositWidget
import dev.abhaycloud.fdtracker.presentation.ui.widget.FixedDepositWidgetViewModel
import dev.abhaycloud.fdtracker.presentation.ui.widget.UpdateWidgetHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FixedDepositModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

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
    fun providesPreferencesDataSource(@ApplicationContext context: Context): PreferencesDataSource {
        return PreferencesDataSource(context)
    }

    @Provides
    @Singleton
    fun providesPreferencesRepository(dataSource: PreferencesDataSource): PreferencesRepository {
        return PreferencesRepositoryImpl(dataSource)
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
    fun providesDeleteAllFixedDepositsUseCase(repository: FixedDepositRepository): DeleteAllFixedDepositsUseCase {
        return DeleteAllFixedDepositsUseCase(repository)
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
    @Singleton
    fun providesGetTotalMaturityAmountUseCase(repository: FixedDepositRepository): GetTotalMaturityAmountUseCase {
        return GetTotalMaturityAmountUseCase(repository)
    }


    @Provides
    @Singleton
    fun providesSetDynamicColorUseCase(repository: PreferencesRepository): SetDynamicColorUseCase {
        return SetDynamicColorUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetDynamicColorUseCase(repository: PreferencesRepository): GetDynamicColorUseCase {
        return GetDynamicColorUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesSetDarkModeUseCase(repository: PreferencesRepository): SetDarkModeUseCase {
        return SetDarkModeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetDarkModeUseCase(repository: PreferencesRepository): GetDarkModeUseCase {
        return GetDarkModeUseCase(repository)
    }

    @Provides
    fun providesAddFixedDepositViewModel(
        addFixedDepositUseCase: AddFixedDepositUseCase,
        updateFixedDepositUseCase: UpdateFixedDepositUseCase,
        deleteFixedDepositUseCase: DeleteFixedDepositUseCase,
        notificationManager: FixedDepositNotificationManager,
        updateWidgetHelper: UpdateWidgetHelper
    ): AddFixedDepositViewModel {
        return AddFixedDepositViewModel(
            addFixedDepositUseCase,
            updateFixedDepositUseCase,
            deleteFixedDepositUseCase,
            notificationManager,
            updateWidgetHelper
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

    @Provides
    @Singleton
    fun providesThemeViewModel(
        setDynamicColorUseCase: SetDynamicColorUseCase,
        getDynamicColorUseCase: GetDynamicColorUseCase,
        setDarkModeUseCase: SetDarkModeUseCase,
        getDarkModeUseCase: GetDarkModeUseCase
    ): ThemeViewModel {
        return ThemeViewModel(
            setDynamicColorUseCase,
            getDynamicColorUseCase,
            setDarkModeUseCase,
            getDarkModeUseCase
        )
    }

    @Provides
    @Singleton
    fun providesSettingsViewModel(
        deleteAllFixedDepositsUseCase: DeleteAllFixedDepositsUseCase
    ): SettingScreenViewModel {
        return SettingScreenViewModel(deleteAllFixedDepositsUseCase)
    }

    @Provides
    @Singleton
    fun providesFixedDepositWidgetViewModel(
        getTotalInvestedAmountUseCase: GetTotalInvestedAmountUseCase,
        getTotalMaturityAmountUseCase: GetTotalMaturityAmountUseCase
    ): FixedDepositWidgetViewModel {
        return FixedDepositWidgetViewModel(getTotalInvestedAmountUseCase, getTotalMaturityAmountUseCase)
    }

    @Provides
    @Singleton
    fun providesFixedDepositWidget(viewModel: FixedDepositWidgetViewModel): GlanceAppWidget {
        return FixedDepositWidget(viewModel)
    }



}