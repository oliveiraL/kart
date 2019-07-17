package br.oliveira.kart


object Main {
  def main(args: Array[String]): Unit = {

    LeitorArquivo.lerArquivoDeLogDaCorrida("resultado_corrida.txt") match {
      case Right(corrida) =>
        val resultado = calcularVencedorETempoExcedidoPorPiloto(corrida)
        val melhorVolta = recuperarMelhorVoltaDaCorrida(corrida)
        println(s"Melhor volta teve o tempo de ${duracaoParaString(melhorVolta._2.duracao)} e foi do piloto ${melhorVolta._1.nome}")
        println("Posição Chegada, Código Piloto, Nome Piloto, Qtde Voltas Completadas, Tempo Total de Prova, Melhor volta, Tempo da melhor volta, Velocidade média na Corrida")
        resultado.foreach(piloto => println(converterPilotoParaString(piloto)))
      case Left(erro) => println(erro)
    }


  }
}

