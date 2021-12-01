package com.example.diplomstrava.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.diplomstrava.data.Activity
import com.example.diplomstrava.data.LastActivityFromApp
import com.example.diplomstrava.data.Person

@Database(
    entities = [
        Activity::class,
        Person::class,
        LastActivityFromApp::class
    ], version = DatabaseStrava.DB_VERSION
)
abstract class DatabaseStrava : RoomDatabase() {

    abstract fun activityDao(): ActivityDao
    abstract fun personDao(): PersonDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "strava-database"
    }
}

/*
@Database(
    entities = [
        User::class,
        Currency::class,
        Order::class,
        Price::class,
        Wallet::class,
        WalletCurrencies::class
    ], version = DatabaseExchange.DB_VERSION
)
abstract class DatabaseExchange : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun orderDao(): OrderDao
    abstract fun priceDao(): PriceDao
    abstract fun walletDao(): WalletDao


    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "exchange-database"
    }
}
 */