package com.alebrije_estudios.metabolique.recobery_account.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alebrije_estudios.metabolique.EMAIL
import com.alebrije_estudios.metabolique.PHONE
import com.alebrije_estudios.metabolique.R
import com.alebrije_estudios.metabolique.composable.DefaultButton
import com.alebrije_estudios.metabolique.login.ui.EmailField

@Preview(showBackground = true, locale= "es")
@Composable
fun RecoveryAccountScreenPreview() {
    RecoveryAccountScreen(RecoveryAccountViewModel())
}


@Composable
fun RecoveryAccountScreen(
    recoveryAccountViewModel: RecoveryAccountViewModel,
) {
    val email by recoveryAccountViewModel.email.observeAsState("")
    val isEnabled by recoveryAccountViewModel.isEnabled.observeAsState(false)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.text_message_recobery_account))
            Spacer(modifier = Modifier.size(64.dp))
            EmailField(email = email) {
                recoveryAccountViewModel.onValidatedEmail(it)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Submit(isEnabled){}
            Spacer(modifier = Modifier.size(128.dp))
            Text(EMAIL)
            Text(PHONE)
        }
    }
}


@Composable
fun Submit(isEnabled: Boolean, onClicked: () -> Unit){
    DefaultButton(label = stringResource(id = R.string.button_submit), enabled = isEnabled) {
        onClicked()
    }
}