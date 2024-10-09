package com.watercanedelivery.di.scope

import javax.inject.Scope
/**
 * Created by Karthikeyan on 27-01-2021.
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.Target(
    AnnotationTarget.TYPE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS
)
annotation class FragmentScoped