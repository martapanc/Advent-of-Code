package aoc2015.day22

import java.util.*
import kotlin.math.max
import kotlin.math.min

fun playWizardSimulator(hp: Int = 71, damage: Int = 10, isPart2: Boolean = false): Int {
    var minimumMana = Integer.MAX_VALUE
    val boss = Boss(hp, damage)
    val wizardList = PriorityQueue<Wizard> { a, b -> b.manaUsed.compareTo(a.manaUsed) }
    wizardList.add(Wizard(50, 500, boss))

    while (wizardList.isNotEmpty()) {
        val currentWizard = wizardList.poll()
        if (isPart2 && currentWizard.hitPoints-- <= 0) continue
        currentWizard.startEffect()

        for (spell in Wizard.spells) {
            if (currentWizard.canCast(spell)) {
                val next = currentWizard.clone()
                next.castSpell(spell)
                next.startEffect()

                if (next.boss.hitPoints <= 0) {
                    minimumMana = min(minimumMana, next.manaUsed)
                    wizardList.removeAll { it.manaUsed > minimumMana }
                } else {
                    next.hitPoints -= max(1, next.boss.damage - next.armor)
                    if (next.hitPoints > 0 && next.mana > 0 && next.manaUsed < minimumMana) {
                        wizardList.add(next)
                    }
                }
            }
        }
    }
    return minimumMana
}

data class Boss(var hitPoints: Int, var damage: Int) : Cloneable {
    public override fun clone(): Boss {
        return Boss(hitPoints, damage)
    }
}

data class Wizard(var hitPoints: Int, var mana: Int, var boss: Boss) : Cloneable {
    var armor = 0
    var manaUsed = 0
    var activeEffects = IntArray(3)

    public override fun clone(): Wizard {
        val copy = Wizard(hitPoints, mana, boss.clone())
        copy.armor = armor
        copy.manaUsed = manaUsed
        copy.activeEffects = activeEffects.clone()
        return copy
    }

    companion object {
        val spells = listOf(
            Spell(53, 0),
            Spell(73, 0),
            Spell(113, 6),
            Spell(173, 6),
            Spell(229, 5)
        )
    }

    fun canCast(spell: Spell): Boolean {
        val spellId = spells.indexOf(spell)
        return mana >= spell.cost && (spellId < 2 || activeEffects[spellId - 2] == 0)
    }

    fun castSpell(spell: Spell) {
        mana -= spell.cost
        manaUsed += spell.cost

        when (val spellId = spells.indexOf(spell)) {
            0 -> boss.hitPoints -= 4
            1 -> {
                hitPoints += 2
                boss.hitPoints -= 2
            }
            else -> activeEffects[spellId - 2] = spell.turns
        }
    }

    fun startEffect() {
        for (i in activeEffects.indices)
            if (activeEffects[i] > 0) {
                activeEffects[i]--
                when (i) {
                    0 -> armor = 7
                    1 -> boss.hitPoints -= 3
                    2 -> mana += 101
                }
            } else if (i == 0) {
                armor = 0
            }
    }
}

class Spell(var cost: Int, var turns: Int)
