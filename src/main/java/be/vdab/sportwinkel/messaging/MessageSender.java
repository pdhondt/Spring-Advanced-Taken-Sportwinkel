package be.vdab.sportwinkel.messaging;

import be.vdab.sportwinkel.repositories.ArtikelGemaaktRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageSender {
    private final ArtikelGemaaktRepository repository;
    private final AmqpTemplate template;

    public MessageSender(ArtikelGemaaktRepository repository, AmqpTemplate template) {
        this.repository = repository;
        this.template = template;
    }
    @Scheduled(fixedDelay = 5_000)
    @Transactional
    void verstuurMessages() {
        var artikelsGemaakt = repository.findAll();
        artikelsGemaakt.forEach(artikelGemaakt ->
                template.convertAndSend("sportartikels", null, artikelGemaakt));
        repository.deleteAllInBatch(artikelsGemaakt);
    }
}
