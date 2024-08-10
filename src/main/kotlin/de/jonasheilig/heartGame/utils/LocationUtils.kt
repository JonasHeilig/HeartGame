package de.jonasheilig.heartGame.utils

import org.bukkit.Location
import kotlin.random.Random

object LocationUtils {

    fun getRandomLocationNearby(location: Location, radius: Int): Location {
        val randomX = location.x + Random.nextInt(-radius, radius)
        val randomZ = location.z + Random.nextInt(-radius, radius)
        val randomLocation = Location(location.world, randomX, location.y, randomZ)

        randomLocation.y = randomLocation.world.getHighestBlockYAt(randomLocation).toDouble()
        return randomLocation
    }
}
