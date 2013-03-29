package bowlinggame;

import org.junit.*;
import static org.junit.Assert.*;

public class BowlingGameTest {

    private BowlingGame game;

    @Before
    public void setUp() {
        game = new BowlingGame();
    }

    @Test
    public void canCreateBowlingGame() {
        assertNotNull("Game should be instantiated.", game);
    }

    @Test
    public void rollingAllGutterBallsShouldScoreZero() {
        assertEquals("Game should score zero.", 0, game.getScore());
    }

    @Test
    public void rollingAOneShouldScoreOne() {
        game.pinsDown(1);
        assertEquals(1, game.getScore());
    }

    @Test(expected = IllegalStateException.class)
    public void rollingAnElevenShouldThrowAnException() {
        game.pinsDown(11);
    }

    @Test
    public void rollingTwoEightAndAThreeShouldScoreSixteen() {
        game.pinsDown(2);
        game.pinsDown(8);
        game.pinsDown(3);
        assertEquals(16, game.getScore());
    }

    @Test
    public void rollTwoOnesShouldScoreTen() {
        game.pinsDown(2);
        game.pinsDown(8);
        assertEquals(10, game.getScore());
    }

    @Test
    public void rollTenAndTwoThreesShouldScoreTwentyTwo() {
        game.pinsDown(10);
        game.pinsDown(3);
        game.pinsDown(3);
        assertEquals(22, game.getScore());
    }

    @Test
    public void rollTenZeroAndOneShouldScoreTwelve() {
        game.pinsDown(10);
        game.pinsDown(0);
        game.pinsDown(1);
        assertEquals(12, game.getScore());
    }

    @Test
    public void perfectGameShouldScore300() {
        for (int i = 0; i < 12; i++) {
            game.pinsDown(10);
        }
        assertEquals(300, game.getScore());
    }
    
    private void playAGame(String recordedGame) {
        for (int i=0; i<recordedGame.length(); i++) {
            char token=recordedGame.charAt(i);
            switch (token) {
                case '|': // new frame marker
                    break;
                case 'X': // strike
                    game.pinsDown(10);
                    break;
                case '-':
                    game.pinsDown(0);
                    break;
                case '/': // spare
                    char prevToken=recordedGame.charAt(i-1);
                    if (prevToken=='-') prevToken='0';
                    if (prevToken<'0' || prevToken>'9') throw new IllegalStateException("invalid preceeding token in spare");
                    int prevBall=Integer.parseInt(Character.toString(prevToken));
                    game.pinsDown(10-prevBall);
                    break;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    game.pinsDown(Integer.parseInt(Character.toString(token)));
                    break;
                default:
                    throw new IllegalStateException("invalid token "+token);
            }
        }
    }
    
    @Test
    public void playPerfectGame() {
        playAGame("X|X|X|X|X|X|X|X|X|X||XX");
        assertEquals(300, game.getScore());
    }
    
    
    @Test
    public void playAGame1() {
        playAGame("9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||");
        assertEquals(90, game.getScore());
    }
        
    
    @Test
    public void playAGame2() {
        playAGame("5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5");
        assertEquals(150, game.getScore());
    }
    
}