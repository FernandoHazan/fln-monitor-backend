package com.fln.apiflnmonitor.model;

import java.util.List;

public record PortalNoticiasDTO(String portal, List<NoticiaDTO> noticias) {
}
