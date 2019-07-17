package br.oliveira.kart

import java.time.{Duration, LocalTime}

import org.scalatest._


class VoltaTest extends FunSpec with Matchers with BeforeAndAfter {
  var volta: Volta = _
  before {
    volta = Volta(inicio = LocalTime.now(), numero = 1, velocidadeMedia = 40, duracao = Duration.ZERO)
  }

  describe("Uma volta") {
    it("deve ter um inicio") {
      volta.inicio shouldBe a[LocalTime]
    }

    it("deve ter um numero") {
      volta.numero shouldBe a[java.lang.Integer]
    }

    it("deve ter uma velocidade média") {
      volta.velocidadeMedia shouldBe a[java.lang.Double]
    }

    it("deve ter uma duracao") {
      volta.duracao shouldBe a[Duration]
    }
  }

}
