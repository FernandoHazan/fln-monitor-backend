package com.fln.apiflnmonitor.model;

import java.time.LocalDateTime;

public record NoticiaDTO(  String titulo,
        String link,
        String tipo,
        LocalDateTime data,
        String fonte,
        String conteudo,
        String cidade) {

}
