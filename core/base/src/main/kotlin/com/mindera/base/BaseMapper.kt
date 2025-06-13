package com.mindera.base

interface BaseMapper<D, M> {

    fun map(data: D): M
}