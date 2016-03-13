package bowlinggame

import scala.annotation.tailrec

class BowlingGame {
  private var ballScores = List.empty[Int]

  def getScore: Int = {
    @tailrec
    def getScoreRec(score: Int, framesLeft: Int, balls: List[Int]): Int = framesLeft match {
      case 0 => score
      case _ => balls match {
        case 10 :: xs => // Strike
          xs match {
            case b1 :: b2 :: xs2 => getScoreRec(score + 10 + b1 + b2, framesLeft - 1, xs)
            case b1 :: xs2 => getScoreRec(score + 10 + b1, framesLeft - 1, xs)
            case _  => getScoreRec(score + 10, framesLeft - 1, xs)
          }
        case b1 :: b2 :: xs if b1 + b2 == 10 => // Spare
          getScoreRec(score + 10 + xs.headOption.getOrElse(0), framesLeft - 1, xs)
        case b1 :: b2 :: xs => // regular bowl
          getScoreRec(score + b1 + b2, framesLeft - 1, xs)
        case b1 :: Nil => // regular, incomplete bowl
          score + b1
        case _ => score
      }
    }
    getScoreRec(0, 10, ballScores)
  }

  def newRoll(pinsDown: Int): Unit = {
    assert(pinsDown <= 10 && pinsDown >= 0, "bowl must be >=0 and <=10")
    ballScores = ballScores ::: List(pinsDown)
  }
}
