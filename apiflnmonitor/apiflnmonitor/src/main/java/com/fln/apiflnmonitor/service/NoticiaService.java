package com.fln.apiflnmonitor.service;
import com.fln.apiflnmonitor.model.*;
import com.fln.apiflnmonitor.repository.NoticiasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticiaService {

    @Autowired
    private final NoticiasRepository noticiasRepository;

    public NoticiaService(NoticiasRepository noticiasRepository) {
        this.noticiasRepository = noticiasRepository;
    }

    public NoticiasCountDTO listarNoticasPorData() {

        List<Noticia> noticiasBanco = noticiasRepository.findTop100ByOrderByDataDesc();
        Long numeroDeNoticias = noticiasRepository.count();
        List<NoticiaDTO> noticiasDTO = noticiasBanco.stream()
                .map(noticia -> new NoticiaDTO(
                        noticia.getTitulo(),
                        noticia.getLink(),
                        noticia.getTipo(),
                        noticia.getData(),
                        noticia.getFonte(),
                        noticia.getConteudo(),
                        noticia.getCidade()
                ))
                .toList();

        return new NoticiasCountDTO(numeroDeNoticias, noticiasDTO);
    }

    public PortalNoticiasDTO listarNoticiasPorPortal(String fonte) {

        List<Noticia> noticiasBanco = noticiasRepository.findTop100ByFonteOrderByIdDesc(fonte);
        long numeroDeNoticias = noticiasRepository.countByFonte(fonte);
        List<NoticiaDTO> noticiasDTO = noticiasBanco.stream()
                .map(noticia -> new NoticiaDTO(
                        noticia.getTitulo(),
                        noticia.getLink(),
                        noticia.getTipo(),
                        noticia.getData(),
                        noticia.getFonte(),
                        noticia.getConteudo(),
                        noticia.getCidade()
                ))
                .toList();

        return new PortalNoticiasDTO(fonte, noticiasDTO, numeroDeNoticias);
    }

    //Melhorar logica das funções.

    public PortalNoticiasDTO listarNoticiasPorPortalTop5(String fonte) {

        List<Noticia> noticiasBanco = noticiasRepository.findTop5ByFonteOrderByIdDesc(fonte);
        long numeroDeNoticias = noticiasRepository.countByFonte(fonte);
        List<NoticiaDTO> noticiasDTO = noticiasBanco.stream()
                .map(noticia -> new NoticiaDTO(
                        noticia.getTitulo(),
                        noticia.getLink(),
                        noticia.getTipo(),
                        noticia.getData(),
                        noticia.getFonte(),
                        noticia.getConteudo(),
                        noticia.getCidade()
                ))
                .toList();

        return new PortalNoticiasDTO(fonte, noticiasDTO, numeroDeNoticias);
    }

    public PortalNoticiasCountDTO listarNoticiasPortais() {
        List<PortalNoticiasDTO> portalNoticiasDTO = new ArrayList<>();
        Long numeroDeNoticias = noticiasRepository.count();
        portalNoticiasDTO.add(listarNoticiasPorPortalTop5("Scc10"));
        portalNoticiasDTO.add(listarNoticiasPorPortalTop5("NSC Total"));
        portalNoticiasDTO.add(listarNoticiasPorPortalTop5("ND"));
        portalNoticiasDTO.add(listarNoticiasPorPortalTop5("Jornal Razão"));

        return new PortalNoticiasCountDTO(numeroDeNoticias, portalNoticiasDTO);
    }
}

