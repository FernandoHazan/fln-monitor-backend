package com.fln.apiflnmonitor.controller;
import com.fln.apiflnmonitor.model.*;
import com.fln.apiflnmonitor.service.NoticiaService;
import org.springframework.web.bind.annotation.*;


@RestController
    @RequestMapping("/api")
    public class NoticiaController {

        private final NoticiaService service;

        public NoticiaController(NoticiaService service) {
            this.service = service;
        }

        @GetMapping("/noticias")
        public NoticiasCountDTO listar() {
            return service.listarNoticasPorData();
        }

        @GetMapping("/scc10")
        public PortalNoticiasDTO listarscc10() { return service.listarNoticiasPorPortal("Scc10");}

        @GetMapping("/nsctotal")
        public PortalNoticiasDTO listarnscTotal() { return service.listarNoticiasPorPortal("NSC Total"); }

        @GetMapping("/nd")
        public PortalNoticiasDTO listarnd() { return  service.listarNoticiasPorPortal("ND");}

        @GetMapping("/jornalrazao")
        public PortalNoticiasDTO listarjornalRazao() { return  service.listarNoticiasPorPortal("Jornal Razão");}

        @GetMapping("/secom")
        public PortalNoticiasDTO listarsecom() { return  service.listarNoticiasPorPortal("Secom");}

        @GetMapping("/agorafloripa")
        public PortalNoticiasDTO listaragoraFloripa() { return  service.listarNoticiasPorPortal("Agora Floripa");}

        @GetMapping("/informefloripa")
        public PortalNoticiasDTO listarinformeFloripa() { return  service.listarNoticiasPorPortal("Informe Floripa");}

        @GetMapping("/portais")
        public PortalNoticiasCountDTO listarportais() { return service.listarNoticiasPortais();}

        @GetMapping("/portaisEservicos")
        public PortalEServicosDTO listarportaisEServicos() { return  service.listarPortaisEServicos();}
    }

