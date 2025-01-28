package cz.mendelu.todost11.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(var paylimit: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    var photo: String? = ""
    var withdrawalLimit: Long =0
}