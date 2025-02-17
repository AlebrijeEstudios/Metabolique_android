<<<<<<< HEAD
package com.alebrije_estudios.metabolique.library
=======
package com.alebrije_estudios.metabolique.gestion
>>>>>>> 31ca231b9f948be85465eeaa1d10e93d82e796b0

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alebrije_estudios.metabolique.R

enum class ErrorType(val code: Int) {
    ERROR_OK(0),
    ERROR_UNKNOWN(1),
    ERROR_REQUERID(1000),
    ERROR_EMAIL_FORMAT(1001),
    ERROR_PASSWORD_LENGTH(1002),
<<<<<<< HEAD
    ERROR_PASSWORD_FORMAT(1003);
    companion object {
        fun fromCode(code: Int): ErrorType {
            return values().firstOrNull { it.code == code } ?: ERROR_UNKNOWN
        }
        fun fromName(name: String): ErrorType {
            when(name){
                "ERROR_OK" -> return ERROR_OK
                "ERROR_UNKNOWN" -> return ERROR_UNKNOWN
                "ERROR_REQUERID" -> return ERROR_REQUERID
                "ERROR_EMAIL_FORMAT" -> return ERROR_EMAIL_FORMAT
                "ERROR_PASSWORD_LENGTH" -> return ERROR_PASSWORD_LENGTH
                "ERROR_PASSWORD_FORMAT" -> return ERROR_PASSWORD_FORMAT
                else -> return ERROR_UNKNOWN
            }
        }
    }
=======
    ERROR_PASSWORD_FORMAT(1003),

>>>>>>> 31ca231b9f948be85465eeaa1d10e93d82e796b0
}
@Composable
fun ErrorType.getErrorMessage(name: String = ""): String {
    return when (this) {
        ErrorType.ERROR_REQUERID -> stringResource(id = R.string.error_requerid).format(name)
        ErrorType.ERROR_EMAIL_FORMAT -> stringResource(id = R.string.error_email)
        ErrorType.ERROR_PASSWORD_LENGTH -> stringResource(id = R.string.error_password_length)
        ErrorType.ERROR_PASSWORD_FORMAT -> stringResource(id = R.string.error_password_format)
        ErrorType.ERROR_UNKNOWN -> stringResource(id = R.string.error_unknown)
        ErrorType.ERROR_OK -> ""
        else -> stringResource(id = R.string.error_unknown)
    }
}
@Composable
<<<<<<< HEAD
=======
@
>>>>>>> 31ca231b9f948be85465eeaa1d10e93d82e796b0
fun ErrorType.ERROR_ANY():String = stringResource(id = R.string.error_any).format(this.code)
