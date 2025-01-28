package cz.mendelu.todost11.model

import android.text.format.DateUtils
import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.mendelu.todost11.utils.dateUtils

import java.util.Date;

@Entity(tableName = "transactions")
data class Transaction(var price: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    var date: Long? = dateUtils.getStringDate(2024,6,15)

    var type:String? = null

    var recipient:String=""

    var detail:String=""
}