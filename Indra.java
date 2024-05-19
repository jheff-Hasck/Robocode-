package Acesso;

import robocode.*;
import java.awt.Color;

public class Indra extends AdvancedRobot {

    final double MOVE_DISTANCE = 100;
    final double TURN_ANGLE = 45;

    boolean movingForward = true;
    int turnDirection = 1;

    double lastEnemyEnergy = 100;

    public void run() {
       
        Color randomColor = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
        setColors(randomColor, randomColor, randomColor); // vai mudar de cor sim //

        while (true) {
            move();
            execute();
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
        
        fire(3);
        
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
