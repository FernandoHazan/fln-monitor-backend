package com.fln.apiflnmonitor.model;

import java.util.List;

public record PortalNoticiasCountDTO(Long ultimas24Horas, List<PortalNoticiasDTO> portais) {
}
