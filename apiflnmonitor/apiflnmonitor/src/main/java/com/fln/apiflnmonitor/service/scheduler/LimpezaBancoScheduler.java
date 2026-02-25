package com.fln.apiflnmonitor.service.scheduler;
import com.fln.apiflnmonitor.repository.NoticiasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class LimpezaBancoScheduler {
    private static final Logger log = LoggerFactory.getLogger(LimpezaBancoScheduler.class);
    private final NoticiasRepository repository;
    public LimpezaBancoScheduler(NoticiasRepository repository) {
        this.repository = repository;
    }
    @Scheduled(cron = "0 0 * * * *")
    public void limparNoticiasAntigas() {
        try {
            LocalDateTime limite = LocalDateTime.now().minusDays(1);
            repository.deletarNoticiasAntigas(limite);
        } catch (Exception e) {
            log.error("❌ Erro ao executar limpeza de notícias antigas", e);
        }
    }
}

