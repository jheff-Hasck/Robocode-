package RGB;

import robocode.*;
import java.awt.Color;

public class Capitao extends AdvancedRobot {
int gunDirection = 1;

    final double MOVE_DISTANCE = 100;
    final double TURN_ANGLE = 45;

    boolean movingForward = true;
    int turnDirection = 1;

    double lastEnemyEnergy = 100;

    public void run() {
       
        Color CorAleatoria = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        setColors(CorAleatoria, CorAleatoria, CorAleatoria); // vai mudar de cor sim //

        while (true) { //enquanto condição minha for sim meu robo vai executar essa condicao//
 move();
            execute();
			turnGunRight(360);
        }
    }

    private void move() {
        if (movingForward) {
            setAhead(MOVE_DISTANCE); 
        } else {
            setBack(MOVE_DISTANCE);
        }
        turnRight(TURN_ANGLE * turnDirection);
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double angleToEnemy = getHeadingRadians() + e.getBearingRadians();
        double gunTurnAngle = robocode.util.Utils.normalRelativeAngle(angleToEnemy - getGunHeadingRadians());
        setTurnGunRightRadians(gunTurnAngle);
        
        if (e.getEnergy() < lastEnemyEnergy) {
            setTurnRight(e.getBearing() + 90 - (10 * turnDirection));
        }
        
        fire(5);
        
        lastEnemyEnergy = e.getEnergy();
    }

    public void onHitRobot(HitRobotEvent e) {
        turnDirection = -turnDirection;
        setBack(70);
    }

    public void onHitWall(HitWallEvent e) {
        movingForward = !movingForward;
    }
}
