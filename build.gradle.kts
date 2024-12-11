plugins {
    id("fpgradle-minecraft") version ("0.8.3")
}

group = "modtweaker2"

minecraft_fp {
    mod {
        modid = "modtweaker2"
        name = "Mod Tweaker 2"
        rootPkg = "$group"
    }

    core {
        accessTransformerFile = "modtweaker2_at.cfg"
    }

    tokens {
        tokenClass = "Tags"
    }

    publish {
        changelog = "https://github.com/GTMEGA/ModTweaker/releases/tag/${version}"
        maven {
            repoUrl = "https://mvn.falsepattern.com/gtmega_releases"
            repoName = "mega"
            group = "mega"
        }
    }
}

configurations {
    create("compileHelper")
}

sourceSets {
    create("compileStubs")
    create("compileHelper") {
        compileClasspath += configurations["compileHelper"]
    }
}

repositories {
    cursemavenEX()
    exclusive(maven("horizon", "https://mvn.falsepattern.com/horizon"), "com.github.GTNewHorizons")
    exclusive(mega(), "mega", "team.chisel", "codechicken")
    exclusive(maven("tterrag", "https://mvn.falsepattern.com/tterrag"), "exnihilo")
    exclusive(maven("k-4u", "https://mvn.falsepattern.com/k-4u") {
        metadataSources {
            artifact()
        }
    }, "pneumaticCraft")
    exclusive(maven("ic2", "https://mvn.falsepattern.com/ic2") {
        metadataSources {
            artifact()
        }
    }, "net.industrial-craft")
    exclusive(ivy("https://mvn.falsepattern.com/releases/mirror/", "[orgPath]/[artifact]-[revision].[ext]"), "mirror")
}

dependencies {
    api("mega:crafttweaker-mc1.7.10:3.3.2-mega:dev")

    compileOnly("mega:forestry-mc1.7.10:4.5.3-mega:dev") { excludeDeps() }
    compileOnly("team.chisel:chisel-mc1.7.10:2.15.1-mega:api") { excludeDeps() }
    compileOnly("curse.maven:thaumcraft-223628:2227552") { excludeDeps() }
    compileOnly("com.github.GTNewHorizons:Applied-Energistics-2-Unofficial:rv3-beta-400-GTNH:dev") { excludeDeps() }
    compileOnly("com.github.GTNewHorizons:Botania:1.11.0-GTNH:dev") { excludeDeps() }
    compileOnly("com.github.GTNewHorizons:Mantle:0.4.1:dev") { excludeDeps() }
    compileOnly("com.github.GTNewHorizons:Railcraft:9.15.8:dev") { excludeDeps() }
    compileOnly("com.github.GTNewHorizons:TinkersConstruct:1.12.0-GTNH:api") { excludeDeps() }
    compileOnly("curse.maven:botanical-addons-237152:2281663") { excludeDeps() }
    compileOnly("curse.maven:extra-utilities-225561:2264384") { excludeDeps() }
    compileOnly("curse.maven:thermal-expansion-69163:2388759") { excludeDeps() }
    compileOnly("curse.maven:mekanism-268560:2475797") { excludeDeps() }
    compileOnly("exnihilo:Ex-Nihilo:1.38-53:deobf") { excludeDeps() }
    compileOnly("net.industrial-craft:industrialcraft-2:2.2.828-experimental:dev") { excludeDeps() }
    compileOnly("pneumaticCraft:PneumaticCraft-1.7.10:1.7.0-74:userdev") { excludeDeps() }
    compileOnly(deobf("mirror:AuraCascade:557"))
    compileOnly(deobf("mirror:Metallurgy-1.7.10:4.0.9.148"))
    compileOnly(deobf("mirror:Mariculture-Deluxe-1.7.10:1.3.0-6"))
}