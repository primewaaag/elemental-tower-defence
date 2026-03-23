package base.project.framework;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import base.project.enemy.LavaGolem;
import base.project.enemy.LavaGolemProjectile;
import base.project.enemy.LavaSlime;
import base.project.player.Coral;
import base.project.player.Crab;
import base.project.player.Nautilus;
import base.project.player.PistolShrimpProjectile;
import base.project.player.Pistolshrimp;
import base.project.player.Seamine;
import base.project.player.WaterGolem;
import base.project.player.WaterGolemProjectile;
import javafx.scene.media.AudioClip;


public class CollisionHandler {
    private final List<WaterGolem> waterGolems;
    private final List<Coral> corals;
    private final List<Nautilus> nautiluses;
    private final List<Crab> crabs;
    private final List<Seamine> seamines;
    private final List<Pistolshrimp> pistolshrimps;
    private final AudioClip lavaslimedeathSound = new AudioClip(getClass().getResource("/sounds/enemys/lavaslime-death-sound.mp3").toExternalForm());
    private final AudioClip lavagolemdeathSound = new AudioClip(getClass().getResource("/sounds/enemys/lavagolem-death-sound.mp3").toExternalForm());
    private final AudioClip seamineexplosionSound = new AudioClip(getClass().getResource("/sounds/player/seamine-explosion.mp3").toExternalForm());



    private final List<LavaSlime> slimes;
    private final List<LavaGolem> lavaGolems;

    private final List<WaterGolemProjectile> waterGolemShots;
    private final List<LavaGolemProjectile> lavaGolemShots;
    private final List<PistolShrimpProjectile> shrimpShots;

    private final List<LavaSlime> slimesToRemove = new CopyOnWriteArrayList<>();
    private final List<LavaGolem> lavaGolemsToRemove = new CopyOnWriteArrayList<>();
    private final List<LavaGolemProjectile> lgpToRemove = new CopyOnWriteArrayList<>();
    private final List<WaterGolemProjectile> wgpToRemove = new CopyOnWriteArrayList<>();
    private final List<WaterGolem> waterGolemsToRemove = new CopyOnWriteArrayList<>();
    private final List<Coral> coralsToRemove = new CopyOnWriteArrayList<>();
    private final List<Nautilus> nautilusesToRemove = new CopyOnWriteArrayList<>();
    private final List<Crab> crabsToRemove = new CopyOnWriteArrayList<>();
    private final List<Seamine> seaminesToRemove = new CopyOnWriteArrayList<>();
    private final List<Pistolshrimp> pistolshrimpToRemove = new CopyOnWriteArrayList<>();
    private final List<PistolShrimpProjectile> shrimpShotsToRemove = new CopyOnWriteArrayList<>();

    public CollisionHandler(List<WaterGolem> waterGolems, List<Coral> corals, List<Nautilus> nautiluses,
            List<Crab> crabs, List<LavaSlime> slimes, List<LavaGolem> lavaGolems,
            List<WaterGolemProjectile> waterGolemShots, List<LavaGolemProjectile> lavaGolemShots,
            List<Seamine> seamines, List<Pistolshrimp> pistolshrimps, List<PistolShrimpProjectile> shrimpShots) {

        this.waterGolems = waterGolems;
        this.corals = corals;
        this.nautiluses = nautiluses;
        this.crabs = crabs;
        this.slimes = slimes;
        this.lavaGolems = lavaGolems;
        this.waterGolemShots = waterGolemShots;
        this.lavaGolemShots = lavaGolemShots;
        this.seamines = seamines;
        this.pistolshrimps = pistolshrimps;
        this.shrimpShots = shrimpShots;
    }

    public void checkAllColisions(double deltaInSec) {

        // Water golem Projectiles
        for (WaterGolemProjectile wgp : waterGolemShots) {
            for (LavaSlime s : slimes) {
                if (wgp.collidesWith(s)) {
                    s.setHealth(s.getHealth() - wgp.getDAMAGE());
                    wgpToRemove.add(wgp);

                    if (s.getHealth() <= 0) {
                        slimesToRemove.add(s);
                        lavaslimedeathSound.setVolume(0.25);
                        lavaslimedeathSound.play();
                    }
                }
            }

            for (LavaGolem lg : lavaGolems) {
                if (wgp.collidesWith(lg)) {
                    lg.setHealth(lg.getHealth() - wgp.getDAMAGE());
                    wgpToRemove.add(wgp);

                    if (lg.getHealth() <= 0) {
                        lavaGolemsToRemove.add(lg);
                        lavagolemdeathSound.setVolume(0.25);
                        lavagolemdeathSound.play();
                    }
                }
            }
        }

        // Pistolshrimp Projectiles
        for (PistolShrimpProjectile psp : shrimpShots) {
            for (LavaSlime s : slimes) {
                if (psp.collidesWith(s)) {
                    shrimpShotsToRemove.add(psp);
                    s.setHealth(s.getHealth() - psp.getDAMAGE());

                    if (s.getHealth() <= 0) {
                        slimesToRemove.add(s);
                        lavaslimedeathSound.setVolume(0.25);
                        lavaslimedeathSound.play();
                    }
                }
            }

            for (LavaGolem lg : lavaGolems) {
                if (psp.collidesWith(lg)) {
                    lg.setHealth(lg.getHealth() - psp.getDAMAGE());
                    shrimpShotsToRemove.add(psp);
                    lg.setHealth(lg.getHealth() - psp.getDAMAGE());

                    if (lg.getHealth() <= 0) {
                        lavaGolemsToRemove.add(lg);
                        lavagolemdeathSound.setVolume(0.25);
                        lavagolemdeathSound.play();
                    }
                }
            }
        }

        // Lava Golem Projectiles
        for (LavaGolemProjectile lgp : lavaGolemShots) {
            for (WaterGolem wg : waterGolems) {
                if (wg.collidesWith(lgp)) {
                    wg.setHealth(wg.getHealth() - lgp.getDAMAGE());
                    lgpToRemove.add(lgp);

                    if (wg.getHealth() <= 0) {
                        waterGolemsToRemove.add(wg);
                    }
                }
            }

            for (Coral c : corals) {
                if (c.collidesWith(lgp)) {
                    c.setHealth(c.getHealth() - lgp.getDAMAGE());
                    lgpToRemove.add(lgp);

                    if (c.getHealth() <= 0) {
                        coralsToRemove.add(c);
                    }
                }
            }

            for (Nautilus n : nautiluses) {
                if (n.collidesWith(lgp)) {
                    n.setHealth(n.getHealth() - lgp.getDAMAGE());
                    lgpToRemove.add(lgp);

                    if (n.getHealth() <= 0) {
                        nautilusesToRemove.add(n);
                    }
                }
            }

            for (Crab c : crabs) {
                if (c.collidesWith(lgp)) {
                    c.setHealth(c.getHealth() - lgp.getDAMAGE());
                    lgpToRemove.add(lgp);

                    if (c.getHealth() <= 0) {
                        crabsToRemove.add(c);
                    }
                }
            }

            for (Pistolshrimp p : pistolshrimps) {
                if (p.collidesWith(lgp)) {
                    p.setHealth(p.getHealth() - lgp.getDAMAGE());
                    lgpToRemove.add(lgp);

                    if (p.getHealth() <= 0) {
                        pistolshrimpToRemove.add(p);
                    }
                }
            }
        }

        // Lava Slimes
        for (LavaSlime s : slimes) {
            boolean collided = false;

            for (WaterGolem wg : waterGolems) {
                if (wg.collidesWith(s)) {
                    s.attack(wg, deltaInSec);
                    collided = true;
                    break;
                }
            }

            if (!collided) {
                for (Coral c : corals) {
                    if (c.collidesWith(s)) {
                        s.attack(c, deltaInSec);
                        collided = true;
                        break;
                    }
                }
            }

            if (!collided) {
                for (Nautilus n : nautiluses) {
                    if (n.collidesWith(s)) {
                        s.attack(n, deltaInSec);
                        collided = true;
                        break;
                    }
                }
            }

            if (!collided) {
                for (Crab c : crabs) {
                    if (c.collidesWith(s)) {
                        s.attack(c, deltaInSec);
                        collided = true;
                        break;
                    }

                    if (c.collidesWithAttackBox(s)) {
                        c.attack(s, deltaInSec);
                        break;
                    }
                }
            }

            if (!collided) {
                for (Pistolshrimp p : pistolshrimps) {
                    if (p.collidesWith(s)) {
                        s.attack(p, deltaInSec);
                        collided = true;
                        break;
                    }
                }
            }

            if (!collided) {
                for (Seamine m : seamines) {
                    if (m.collidesWith(s)) {
                        s.setHealth(s.getHealth() - m.getDAMAGE());
                        seamineexplosionSound.setVolume(0.25);
                        seamineexplosionSound.play();
                        seaminesToRemove.add(m);
                        break;
                    }
                }
            }

            s.setSpeed(collided ? 0 : 40);

            if (s.getHealth() <= 0) {
                slimesToRemove.add(s);
            }
        }

        // Lava Golems
        for (LavaGolem lg : lavaGolems) {
            boolean collided = false;

            for (WaterGolem wg : waterGolems) {
                if (wg.collidesWith(lg)) {
                    collided = true;
                    break;
                }
            }

            if (!collided) {
                for (Coral c : corals) {
                    if (c.collidesWith(lg)) {
                        collided = true;
                        break;
                    }
                }
            }

            if (!collided) {
                for (Nautilus n : nautiluses) {
                    if (n.collidesWith(lg)) {
                        collided = true;
                        break;
                    }
                }
            }

            if (!collided) {
                for (Crab c : crabs) {
                    if (c.collidesWith(lg)) {
                        c.attack(lg, deltaInSec);
                        collided = true;
                        break;
                    }

                    if (c.collidesWithAttackBox(lg)) {
                        c.attack(lg, deltaInSec);
                        break;
                    }
                }
            }

            if (!collided) {
                for (Pistolshrimp p : pistolshrimps) {
                    if (p.collidesWith(lg)) {
                        collided = true;
                        break;
                    }
                }
            }

            if (!collided) {
                for (Seamine m : seamines) {
                    if (m.collidesWith(lg)) {
                        lg.setHealth(lg.getHealth() - m.getDAMAGE());
                        seamineexplosionSound.setVolume(0.25);
                        seamineexplosionSound.play();
                        seaminesToRemove.add(m);
                        break;
                    }
                }
            }

            lg.setSpeed(collided ? 0 : 25);

            if (lg.getHealth() <= 0) {
                lavaGolemsToRemove.add(lg);
            }
        }

        // Deleting everything that is dead
        for (WaterGolem wg : waterGolemsToRemove) {
            if (wg.getBlock() != null) wg.getBlock().setAvailable();
        }

        for (Coral c : coralsToRemove) {
            if (c.getBlock() != null) c.getBlock().setAvailable();
        }

        for (Nautilus n : nautilusesToRemove) {
            if (n.getBlock() != null) n.getBlock().setAvailable();
        }

        for (Crab c : crabsToRemove) {
            if (c.getBlock() != null) c.getBlock().setAvailable();
        }

        for (Seamine s : seaminesToRemove) {
            if (s.getBlock() != null) s.getBlock().setAvailable();
        }

        for (Pistolshrimp p : pistolshrimpToRemove) {
            if (p.getBlock() != null) p.getBlock().setAvailable();
        }

        waterGolems.removeAll(waterGolemsToRemove);
        corals.removeAll(coralsToRemove);
        nautiluses.removeAll(nautilusesToRemove);
        crabs.removeAll(crabsToRemove);
        seamines.removeAll(seaminesToRemove);
        pistolshrimps.removeAll(pistolshrimpToRemove);
        slimes.removeAll(slimesToRemove);
        lavaGolems.removeAll(lavaGolemsToRemove);
        waterGolemShots.removeAll(wgpToRemove);
        lavaGolemShots.removeAll(lgpToRemove);
        shrimpShots.removeAll(shrimpShotsToRemove);

        waterGolemsToRemove.clear();
        coralsToRemove.clear();
        nautilusesToRemove.clear();
        crabsToRemove.clear();
        seaminesToRemove.clear();
        pistolshrimpToRemove.clear();
        slimesToRemove.clear();
        lavaGolemsToRemove.clear();
        wgpToRemove.clear();
        lgpToRemove.clear();
        shrimpShotsToRemove.clear();
    }
}