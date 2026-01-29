package com.fln.apiflnmonitor.model;

import java.util.List;

public record PortalEServicosDTO(List<PortalNoticiasDTO> portais, List<PortalNoticiasDTO> servicos) {
}
