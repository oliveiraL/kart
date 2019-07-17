package br.oliveira.kart

import java.time.{Duration, LocalTime}

import org.scalatest._

class PilotoTest extends FunSpec with Matchers with BeforeAndAfter {
  var piloto: Piloto = _
  before {

    val voltas = List[Volta](Volta(inicio = LocalTime.now(), numero = 1, velocidadeMedia = 40, duracao = Duration.between(LocalTime.MIN, "00:01:10")),
      Volta(inicio = LocalTime.now(), numero = 2, velocidadeMedia = 43, duracao = Duration.between(LocalTime.MIN, "00:01:12")),
      Volta(inicio = LocalTime.now(), numero = 3, velocidadeMedia = 42, duracao = Duration.between(LocalTime.MIN, "00:01:14")),
      Volta(inicio = LocalTime.now(), numero = 4, velocidadeMedia = 44, duracao = Duration.between(LocalTime.MIN, "00:01:16")))

    piloto = Piloto(codigo = "001", nome = "Piloto", voltas = voltas)
  }

  describe("Um Piloto deve conter") {
    it("um codigo") {
      piloto.codigo shouldBe a[String]
    }
    it("um nome") {
      piloto.nome shouldBe a[String]
    }

    it("voltas") {
      piloto.voltas shouldBe a[List[Volta]]
    }

    it("melhor volta") {
      piloto.melhorVolta.numero shouldEqual 1
    }

    it("se concluiu a corrida") {
      piloto.concluiu(4) shouldBe true
    }

    it("velocidade média na corrida"){
      piloto.velocidadeMedia shouldEqual 42.28767123287671
    }
  }
}
