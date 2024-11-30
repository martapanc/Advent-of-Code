package aoc2015.day21

import org.paukov.combinatorics3.Generator

fun playRpgSimulator(inputCharacter: Character): Int {
    val itemSets = getValidItemCombinations().sortedBy { it.sumBy(Item::cost) }
    var bossIsDefeated = false

    for (items in itemSets) {
        val boss = inputCharacter.copy()
        val player = Character(100, items.sumBy(Item::damage), items.sumBy(Item::armor))
        var currentMove = 1
        while (true) {
            if (currentMove % 2 == 0) {
                boss.attack(player)
                if (player.hitpoints <= 0) break
            } else {
                player.attack(boss)
                if (boss.hitpoints <= 0) {
                    bossIsDefeated = true
                    break
                }
            }
            currentMove++
        }
        if (bossIsDefeated)
            return items.sumBy(Item::cost)
    }
    return -1
}

fun playRpgSimulatorPart2(inputCharacter: Character): Int {
    val itemSets = getValidItemCombinations().sortedByDescending { it.sumBy(Item::cost) }
    var playerIsDefeated = false

    for (items in itemSets) {
        val boss = inputCharacter.copy()
        val player = Character(100, items.sumBy(Item::damage), items.sumBy(Item::armor))
        var currentMove = 1
        while (true) {
            if (currentMove % 2 == 0) {
                boss.attack(player)
                if (player.hitpoints <= 0) {
                    playerIsDefeated = true
                    break
                }
            } else {
                player.attack(boss)
                if (boss.hitpoints <= 0) {
                    break
                }
            }
            currentMove++
        }
        if (playerIsDefeated)
            return items.sumBy(Item::cost)
    }
    return -1
}

fun getValidItemCombinations(): List<List<Item>> {
    val items = setOf(
        Weapon(cost = 8, damage = 4),
        Weapon(cost = 10, damage = 5),
        Weapon(cost = 25, damage = 6),
        Weapon(cost = 40, damage = 7),
        Weapon(cost = 74, damage = 8),
        Armor(cost = 13, armor = 1),
        Armor(cost = 31, armor = 2),
        Armor(cost = 53, armor = 3),
        Armor(cost = 75, armor = 4),
        Armor(cost = 102, armor = 5),
        Ring(cost = 25, damage = 1),
        Ring(cost = 50, damage = 2),
        Ring(cost = 100, damage = 3),
        Ring(cost = 20, armor = 1),
        Ring(cost = 40, armor = 2),
        Ring(cost = 80, armor = 3)
    )
    return Generator.subset(items).simple().filter { i -> isValidItemSet(i) }.toList()
}

data class Character(var hitpoints: Int = 0, var damage: Int = 0, var armor: Int = 0) {
    fun attack(other: Character) {
        other.hitpoints -= if (damage - other.armor < 1) 1 else damage - other.armor
    }
}

fun isValidItemSet(items: List<Item>): Boolean {
    return !(items.isEmpty() || items.size > 4 ||
            items.count { it.type == Type.Weapon } != 1 ||
            items.count { it.type == Type.Armor } > 1 ||
            items.count { it.type == Type.Armor } > 2)
}

abstract class Item(val type: Type) {
    abstract val cost: Int
    abstract val damage: Int
    abstract val armor: Int
}

class Weapon(override val cost: Int, override val damage: Int = 0, override val armor: Int = 0) : Item(Type.Weapon)
class Armor(override val cost: Int, override val damage: Int = 0, override val armor: Int = 0) : Item(Type.Armor)
class Ring(override val cost: Int, override val damage: Int = 0, override val armor: Int = 0) : Item(Type.Ring)

enum class Type { Weapon, Armor, Ring }
