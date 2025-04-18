/*
 * NextFTC: a user-friendly control library for FIRST Tech Challenge
 *     Copyright (C) 2025 Rowan McAlpin
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.rowanmcalpin.nextftc.ftc.components

import com.qualcomm.hardware.lynx.LynxModule
import com.rowanmcalpin.nextftc.core.components.Component
import com.rowanmcalpin.nextftc.ftc.OpModeData

/**
 * This component automatically sets up bulk reading for your control and expansion hubs
 */
class BulkReadComponent : Component {
    private lateinit var allHubs: List<LynxModule>
    override fun postInit() {

        OpModeData.hardwareMap
            ?: throw UninitializedPropertyAccessException("hardwareMap has not been initialized")
        allHubs = OpModeData.hardwareMap!!.getAll(LynxModule::class.java)

        allHubs.forEach {
            it.bulkCachingMode = LynxModule.BulkCachingMode.MANUAL
        }
    }

    override fun postWaitForStart() = clearCache()

    override fun postUpdate() = clearCache()

    private fun clearCache() {
        allHubs.forEach {
            it.clearBulkCache()
        }
    }
}