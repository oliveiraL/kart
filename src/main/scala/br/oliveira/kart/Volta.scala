package br.oliveira.kart

import java.time.{Duration, LocalTime}


case class Volta(
  inicio: LocalTime,
  numero: Int,
  velocidadeMedia: Double,
  duracao: Duration
)
