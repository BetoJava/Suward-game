package fr.suward.managers;


import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryo.Kryo;
import fr.suward.game.entities.Character;
import fr.suward.game.entities.Entity;
import fr.suward.game.entities.classes.ClassData;
import fr.suward.game.entities.spells.*;
import fr.suward.game.entities.spells.classes.dolma.*;
import fr.suward.game.entities.spells.classes.erit.Fragmentation;
import fr.suward.game.entities.spells.classes.erit.Ciblage;
import fr.suward.game.entities.spells.classes.erit.*;
import fr.suward.game.entities.spells.classes.erit.InvocHallebarde;
import fr.suward.game.entities.spells.classes.ouq.*;
import fr.suward.game.entities.spells.classes.others.*;
import fr.suward.game.entities.spells.classes.erit.Dephasage;
import fr.suward.game.entities.spells.classes.others.Heal;
import fr.suward.game.entities.spells.pdcity.mdc.*;
import fr.suward.game.entities.spells.pdcity.moustache.*;
import fr.suward.game.entities.spells.pdcity.oeuf.*;
import fr.suward.game.entities.spells.pdcity.pasdoeuf.*;
import fr.suward.game.entities.spells.pdcity.pasdstuff.*;
import fr.suward.game.entities.stats.Stat;
import fr.suward.game.entities.stats.Stats;
import fr.suward.game.pathfinding.Path;
import fr.suward.network.packets.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PacketManager {

    public static void registerPackets(Kryo kryo) {

        kryo.register(LocalMessagePacket.class);
        kryo.register(DamageLine.class);
        kryo.register(Path.class);
        kryo.register(PlayerMovementPacket.class);
        kryo.register(PassTurnPacket.class);
        kryo.register(StartingBattlePacket.class);
        kryo.register(InitialPosPacket.class);
        kryo.register(ChatMessagePacket.class);
        kryo.register(EntityPacket.class);
        kryo.register(StartBattleStatePacket.class);
        kryo.register(MapNamePacket.class);
        kryo.register(ChangePosHubPacket.class);
        kryo.register(EndBattlePacket.class);
        kryo.register(Texture.class);
        kryo.register(HashMap.class);
        kryo.register(Point.class);
        kryo.register(Stats.class);
        kryo.register(Stat.class);
        kryo.register(Effect.class);
        kryo.register(Boost.class);
        kryo.register(Poison.class);
        kryo.register(SpellBoost.class);
        kryo.register(ClassData.class);
        kryo.register(Entity.class);
        kryo.register(Character.class);
        kryo.register(ConnectionPacket.class);
        kryo.register(ArrayList.class);
        kryo.register(BoostPacket.class);

        // Spells //
        kryo.register(SpellCastingPacket.class);
        kryo.register(Spell.class);
        kryo.register(PoisonSpell.class);

        kryo.register(Hook.class);
        kryo.register(Puissance.class);
        kryo.register(DiagoTest.class);
        kryo.register(Sanglante.class);
        kryo.register(Surf.class);
        kryo.register(Fracas.class);
        kryo.register(Courant.class);
        kryo.register(Dephasage.class);
        kryo.register(Ciblage.class);
        kryo.register(Lansso.class);
        kryo.register(Heal.class);
        kryo.register(Chatilance.class);
        kryo.register(CoupDeGrace.class);
        kryo.register(Fragmentation.class);
        kryo.register(Doublance.class);
        kryo.register(SautALaPerche.class);
        kryo.register(LanceErosive.class);
        kryo.register(InvocHallebarde.class);
        kryo.register(Fouguas.class);
        kryo.register(Impesanteur.class);
        kryo.register(ExtinctionMassive.class);
        kryo.register(TrouNoir.class);
        kryo.register(Vitae.class);
        kryo.register(CuryVitae.class);
        kryo.register(PriereOffensive.class);
        kryo.register(PriereProtectrice.class);
        kryo.register(Gravitae.class);
        kryo.register(TraverseeTemporelle.class);
        kryo.register(ProtectionDivine.class);
        kryo.register(InvocHypercube.class);
        kryo.register(InvocMurDePierre.class);
        kryo.register(HyperSoutient.class);
        kryo.register(MiseAuCube.class);

            // pdcity //
        kryo.register(Massacre.class);
        kryo.register(Cry.class);
        kryo.register(Troeuf.class);
        kryo.register(Fracture.class);
        kryo.register(Roulade.class);
        kryo.register(Liberation.class);
        kryo.register(Concentration.class);
        kryo.register(Detente.class);
        kryo.register(Moinsdstuff.class);
        kryo.register(Ventouse.class);
        kryo.register(Tentaculation.class);
        kryo.register(Cavite.class);
        kryo.register(Pasdoeil.class);
        kryo.register(Chai.class);
        kryo.register(Haleine.class);
        kryo.register(Fermentation.class);
        kryo.register(Transposition.class);
        kryo.register(Explochai.class);
        kryo.register(ManzaCoca.class);
        kryo.register(PoilSoyeux.class);
        kryo.register(PoilUrtican.class);
        kryo.register(DansePoilue.class);
        kryo.register(Caresse.class);
        kryo.register(CriDeGege.class);
        kryo.register(ChaiNucleaire.class);
        kryo.register(DepressionPartagee.class);
        kryo.register(CigaretteAfterPex.class);
        kryo.register(Roll.class);
        kryo.register(GrosFront.class);
        kryo.register(OeufPourri.class);
        kryo.register(AutoPilotage.class);
        kryo.register(ExplosionDeChai.class);
        kryo.register(Fracas.class);
        kryo.register(ExplosionDeChai.class);
        kryo.register(Absorption.class);
        kryo.register(CroixIncendiaire.class);
        kryo.register(AttaqueAerienne.class);
        kryo.register(Hook.class);
        kryo.register(TransfertHorizontal.class);
        kryo.register(Puissance.class);
        kryo.register(Surf.class);
        kryo.register(Perfisition.class);
        kryo.register(Cicatrisation.class);
        kryo.register(Transcendance.class);
        kryo.register(InvocEpeeSanglante.class);
        kryo.register(AttiranceSanguinaire.class);
        kryo.register(Canalisation.class);


    }


}
