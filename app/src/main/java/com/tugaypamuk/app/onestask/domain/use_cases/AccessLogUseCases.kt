package com.tugaypamuk.app.onestask.domain.use_cases

import com.tugaypamuk.app.onestask.domain.use_cases.approve.ApproveAccessLogUseCase
import com.tugaypamuk.app.onestask.domain.use_cases.get.GetAccessLogUseCase

data class AccessLogUseCases(
    val approveAccessLogUseCase: ApproveAccessLogUseCase,
    val getAccessLogUseCase: GetAccessLogUseCase
)