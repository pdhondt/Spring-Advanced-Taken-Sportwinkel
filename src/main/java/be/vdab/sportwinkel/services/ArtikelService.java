package be.vdab.sportwinkel.services;

import be.vdab.sportwinkel.domain.Artikel;
import be.vdab.sportwinkel.dto.NieuwArtikel;
import be.vdab.sportwinkel.events.ArtikelGemaakt;
import be.vdab.sportwinkel.repositories.ArtikelGemaaktRepository;
import be.vdab.sportwinkel.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArtikelService {
    private final ArtikelRepository artikelRepository;
    private final ArtikelGemaaktRepository artikelGemaaktRepository;

    public ArtikelService(ArtikelRepository artikelRepository, ArtikelGemaaktRepository artikelGemaaktRepository) {
        this.artikelRepository = artikelRepository;
        this.artikelGemaaktRepository = artikelGemaaktRepository;
    }
    @Transactional
    public void create(NieuwArtikel nieuwArtikel) {
        var artikel = new Artikel(nieuwArtikel.naam(), nieuwArtikel.aankoopprijs(),
                nieuwArtikel.verkoopprijs());
        artikelRepository.save(artikel);
        artikelGemaaktRepository.save(new ArtikelGemaakt(artikel));
    }
}
