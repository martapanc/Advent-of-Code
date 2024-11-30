package aoc2018.day24

class notes {

    //    for ((vacId, vaccine) in vaccineGroups.withIndex()) {
//        for ((covId, corona) in coronaGroups.withIndex()) {
//            val currentCoronaId = "c$covId"
//            val currentVaccineId = "v$vacId"
//
//            val attack = Attack(currentCoronaId, 0, vaccine.initiative)
//            attack.effectivePowerMultiplier = when {
//                corona.weaknesses.contains(vaccine.attackType) -> 2
//                corona.immunities.contains(vaccine.attackType) -> 0
//                else -> 1
//            }
//            if (attackMap.containsKey(currentVaccineId)) {
//                val existingAttack = attackMap[currentVaccineId]!!
//                when {
//                    attack.effectivePowerMultiplier > existingAttack.effectivePowerMultiplier -> attackMap[currentVaccineId] =
//                        attack
//                    attack.effectivePowerMultiplier == existingAttack.effectivePowerMultiplier -> {
//                        val candidateTargetId = Character.getNumericValue(existingAttack.target[1])
//                        if (coronaGroups[covId].effectivePower > coronaGroups[candidateTargetId].effectivePower) {
//                            attackMap[currentVaccineId] = attack
//                        } else if (coronaGroups[covId].effectivePower == coronaGroups[candidateTargetId].effectivePower) {
//                            if (coronaGroups[covId].initiative > coronaGroups[candidateTargetId].initiative) {
//                                attackMap[currentVaccineId] = attack
//                            }
//                        }
//                    }
//                }
//            } else {
//                attackMap[currentVaccineId] = attack
//            }
//        }
//    }
//    for ((vacId, vaccine) in vaccineGroups.withIndex()) {
//        for ((covId, corona) in coronaGroups.withIndex()) {
//            //corona attacks vaccine
//            val currentCoronaId = "c$covId"
//            val currentVaccineId = "v$vacId"
//
//            val attack = Attack(currentVaccineId, 0, corona.initiative)
//            when {
//                vaccine.weaknesses.contains(corona.attackType) -> attack.effectivePowerMultiplier = 2
//                vaccine.immunities.contains(corona.attackType) -> attack.effectivePowerMultiplier = 0
//                else -> attack.effectivePowerMultiplier = 1
//            }
//            if (attackMap.containsKey(currentCoronaId)) {
//                val previouslyAddedAttack = attackMap[currentCoronaId]!!
//                when {
//                    attack.effectivePowerMultiplier > previouslyAddedAttack.effectivePowerMultiplier -> attackMap[currentCoronaId] =
//                        attack
//                    attack.effectivePowerMultiplier == previouslyAddedAttack.effectivePowerMultiplier -> {
//                        val candidateTargetId = Character.getNumericValue(previouslyAddedAttack.target[1])
//                        if (vaccineGroups[vacId].effectivePower > vaccineGroups[candidateTargetId].effectivePower) {
//                            attackMap[currentCoronaId] = attack
//                        } else if (vaccineGroups[covId].effectivePower == vaccineGroups[candidateTargetId].effectivePower) {
//                            if (vaccineGroups[covId].initiative > vaccineGroups[candidateTargetId].initiative) {
//                                attackMap[currentCoronaId] = attack
//                            }
//                        }
//                    }
//                }
//            } else {
//                attackMap[currentCoronaId] = attack
//            }
//        }
//    }
}