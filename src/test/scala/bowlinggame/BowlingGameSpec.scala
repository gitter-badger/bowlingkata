package bowlinggame

import org.scalatest.{BeforeAndAfterEach, FunSuite}

class BowlingGameSpec extends FunSuite with BeforeAndAfterEach {

  private var game: BowlingGame = _

  override protected def beforeEach(): Unit = game = new BowlingGame()

  test("bowlinggame can be created") {
    assert(game != null)
  }

  test("rolling all gutterballs should score 0") {
    assert(0 == game.getScore)
  }

  test("rolling a 1 should score 1") {
    game.newRoll(1)
    assert(1 == game.getScore)
  }

  test("rolling an 11 should throw an exception") {
    intercept[AssertionError] {
      game.newRoll(11)
    }
  }

  test("rolling 2, 8 and 3 should score 16") {
    game.newRoll(2)
    game.newRoll(8)
    game.newRoll(3)
    assert(16 == game.getScore)
  }

  test("rolling 2, 8 should score 10") {
    game.newRoll(2)
    game.newRoll(8)
    assert(10 == game.getScore)
  }

  test("rolling 10, 3, 3 should score 22") {
    game.newRoll(10)
    game.newRoll(3)
    game.newRoll(3)
    assert(22 == game.getScore)
  }

  test("rolling 10, 0 and 1 should score 12") {
    game.newRoll(10)
    game.newRoll(0)
    game.newRoll(1)
    assert(12 == game.getScore)
  }

  test("perfect game should score 300") {
    for (i <- 1 to 12) game.newRoll(10)
    assert(300 == game.getScore)
  }

  private def playAGame(recordedGame: String) {
    for (i <- 0 until recordedGame.length()) {
      val token = recordedGame.charAt(i)
      token match {
        case '|' => // new frame marker
        case 'X' => /* strike */ game.newRoll(10)
        case '-' => game.newRoll(0)
        case '/' => // spare
          var prevToken = recordedGame.charAt(i - 1)
          if (prevToken == '-') prevToken = '0'
          if (prevToken < '0' || prevToken > '9') throw new IllegalStateException("invalid preceeding token in spare")
          game.newRoll(10 - prevToken.toString.toInt)
        case x if x >= '1' && x <= '9' => game.newRoll(token.toString.toInt)
        case _ => throw new IllegalStateException("invalid token " + token)
      }
    }
  }

  test("play perfect game") {
    playAGame("X|X|X|X|X|X|X|X|X|X||XX")
    assert(300 == game.getScore)
  }

  test("play example game 1") {
    playAGame("9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||")
    assert(90 == game.getScore)
  }

  test("play example game 2") {
    playAGame("5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5")
    assert(150 == game.getScore)
  }
}
