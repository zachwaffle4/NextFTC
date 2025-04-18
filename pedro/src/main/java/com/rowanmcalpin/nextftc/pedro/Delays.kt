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

package com.rowanmcalpin.nextftc.pedro

import com.pedropathing.localization.Pose
import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil
import com.rowanmcalpin.nextftc.pedro.PedroComponent.PedroFollower.follower

/**
 * This is a delay that waits until the robot has been displaced (by driving) a certain distance.
 * This does not use the length of the path; if you are driving in circles smaller than your distance,
 * it will never finish.
 *
 * @param distance the distance the robot must drive
 */
class DisplacementDelay(private val distance: Double) : Command() {
    private lateinit var startPosition: Pose

    override val isDone: Boolean
        get() = startPosition distanceTo follower.pose >= distance

    override fun start() {
        startPosition = follower.pose
    }
}

/**
 * This is a delay that waits until the robot is within [distanceTolerance] units of the specified [point].
 *
 * @param point the point to wait until the robot is near
 * @param distanceTolerance how close to the point the robot must be for it to be considered "at" the point
 */
class ProximityDelay @JvmOverloads constructor(
    private val point: Pose,
    private val distanceTolerance: Double = 4.0
) : WaitUntil({
    point distanceTo follower.pose <= distanceTolerance
})