package bowlinggame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {

    private enum FrameType {

        STRIKE, SPARE, DEFAULT
    };
    private List<Integer> ballScores = new ArrayList<Integer>();

    public int getScore() {
        int score = 0;
        int frame = 0;
        for (int i = 0; i < ballScores.size() && frame < 10; i++) {
            switch (getFrameType(i)) {
                case STRIKE:
                    score += 10 + getBallScore(i + 1);
                    score += getBallScore(i + 2);
                    break;
                case SPARE:
                    score += 10 + getBallScore(i + 2);
                    i++;
                    break;
                default:
                    score += getBallScore(i) + getBallScore(i + 1);
                    i++;
            }
            frame++;
        }
        return score;
    }

    private FrameType getFrameType(int index) {
        FrameType type;

        if (getBallScore(index) == 10) {
            type = FrameType.STRIKE;
        } else if (getBallScore(index) + getBallScore(index + 1) == 10) {
            type = FrameType.SPARE;
        } else {
            type = FrameType.DEFAULT;
        }
        return type;
    }

    private int getBallScore(int index) {
        return (index < ballScores.size()) ? ballScores.get(index) : 0;
    }

    public void pinsDown(int noOfPinsDown) {
        if (noOfPinsDown > 10) {
            throw new IllegalStateException("Bowl score was out of bounds");
        }
        ballScores.add(noOfPinsDown);
    }
}
