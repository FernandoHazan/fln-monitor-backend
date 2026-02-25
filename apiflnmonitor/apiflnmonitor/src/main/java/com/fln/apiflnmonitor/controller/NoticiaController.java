package com.fln.apiflnmonitor.controller;
import com.fln.apiflnmonitor.model.*;
import com.fln.apiflnmonitor.service.NoticiaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/api/scraping")
    public class NoticiaController {

        private final NoticiaService service;

        public NoticiaController(NoticiaService service) {
            this.service = service;
        }

        // 🔍 GET – listar tudo
        @GetMapping("/noticias")
        public NoticiasCountDTO listar() {
            return service.listarNoticasPorData();
        }

        @GetMapping("/scc10")
        public PortalNoticiasDTO listarscc10() { return service.listarNoticiasPorPortal("Scc10");}

        @GetMapping("/nsctotal")
        public PortalNoticiasDTO listarnsctotal() { return service.listarNoticiasPorPortal("NSC Total"); }

        @GetMapping("/nd")
        public PortalNoticiasDTO listarnd() { return  service.listarNoticiasPorPortal("ND");}

        @GetMapping("/jornalrazao")
        public PortalNoticiasDTO listarjornalRazao() { return  service.listarNoticiasPorPortal("Jornal Razão");}

        @GetMapping("/portais")
        public PortalNoticiasCountDTO listarportais() { return service.listarNoticiasPortais();}

        @GetMapping("/portaisEservicos")
        public PortalEServicosDTO listarportaiseservicos() { return  service.listarportaiseservicos();}
    }

