package be.vdab.sportwinkel.services;

import be.vdab.sportwinkel.domain.Artikel;
import be.vdab.sportwinkel.dto.NieuwArtikel;
import be.vdab.sportwinkel.events.ArtikelGemaakt;
import be.vdab.sportwinkel.repositories.ArtikelRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArtikelService {
    private final ArtikelRepository artikelRepository;
    private final AmqpTemplate template;

    public ArtikelService(ArtikelRepository artikelRepository, AmqpTemplate template) {
        this.artikelRepository = artikelRepository;
        this.template = template;
    }
    @Transactional
    public void create(NieuwArtikel nieuwArtikel) {
        var artikel = new Artikel(nieuwArtikel.naam(), nieuwArtikel.aankoopprijs(),
                nieuwArtikel.verkoopprijs());
        artikelRepository.save(artikel);
        template.convertAndSend("sportartikels", null, new ArtikelGemaakt(artikel));
    }
}
