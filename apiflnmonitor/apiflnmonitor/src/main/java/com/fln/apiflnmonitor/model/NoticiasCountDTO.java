package com.fln.apiflnmonitor.model;

import java.util.List;

public record NoticiasCountDTO(Long ultimas24Horas, List<NoticiaDTO> noticias) {
}
