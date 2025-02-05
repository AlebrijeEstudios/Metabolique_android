package com.alebrije_estudios.metabolique.gestion

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alebrije_estudios.metabolique.R

enum class ErrorType(val code: Int) {
    ERROR_OK(0),
    ERROR_UNKNOWN(1),
    ERROR_REQUERID(1000),
    ERROR_EMAIL_FORMAT(1001),
    ERROR_PASSWORD_LENGTH(1002),
    ERROR_PASSWORD_FORMAT(1003),

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
@
fun ErrorType.ERROR_ANY():String = stringResource(id = R.string.error_any).format(this.code)
