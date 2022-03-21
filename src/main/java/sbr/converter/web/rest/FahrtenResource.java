package sbr.converter.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sbr.converter.domain.Fahrten;
import sbr.converter.repository.FahrtenRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.Fahrten}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FahrtenResource {

    private final Logger log = LoggerFactory.getLogger(FahrtenResource.class);

    private static final String ENTITY_NAME = "fahrten";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FahrtenRepository fahrtenRepository;

    public FahrtenResource(FahrtenRepository fahrtenRepository) {
        this.fahrtenRepository = fahrtenRepository;
    }

    /**
     * {@code POST  /fahrtens} : Create a new fahrten.
     *
     * @param fahrten the fahrten to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fahrten, or with status {@code 400 (Bad Request)} if the fahrten has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fahrtens")
    public ResponseEntity<Fahrten> createFahrten(@RequestBody Fahrten fahrten) throws URISyntaxException {
        log.debug("REST request to save Fahrten : {}", fahrten);
        if (fahrten.getId() != null) {
            throw new BadRequestAlertException("A new fahrten cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fahrten result = fahrtenRepository.save(fahrten);
        return ResponseEntity
            .created(new URI("/api/fahrtens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fahrtens/:id} : Updates an existing fahrten.
     *
     * @param id the id of the fahrten to save.
     * @param fahrten the fahrten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fahrten,
     * or with status {@code 400 (Bad Request)} if the fahrten is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fahrten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fahrtens/{id}")
    public ResponseEntity<Fahrten> updateFahrten(@PathVariable(value = "id", required = false) final Long id, @RequestBody Fahrten fahrten)
        throws URISyntaxException {
        log.debug("REST request to update Fahrten : {}, {}", id, fahrten);
        if (fahrten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fahrten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fahrtenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Fahrten result = fahrtenRepository.save(fahrten);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fahrten.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fahrtens/:id} : Partial updates given fields of an existing fahrten, field will ignore if it is null
     *
     * @param id the id of the fahrten to save.
     * @param fahrten the fahrten to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fahrten,
     * or with status {@code 400 (Bad Request)} if the fahrten is not valid,
     * or with status {@code 404 (Not Found)} if the fahrten is not found,
     * or with status {@code 500 (Internal Server Error)} if the fahrten couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fahrtens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Fahrten> partialUpdateFahrten(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Fahrten fahrten
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fahrten partially : {}, {}", id, fahrten);
        if (fahrten.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fahrten.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fahrtenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Fahrten> result = fahrtenRepository
            .findById(fahrten.getId())
            .map(existingFahrten -> {
                if (fahrten.getTitel() != null) {
                    existingFahrten.setTitel(fahrten.getTitel());
                }
                if (fahrten.getZugnummer() != null) {
                    existingFahrten.setZugnummer(fahrten.getZugnummer());
                }
                if (fahrten.getZnrBeschreibung() != null) {
                    existingFahrten.setZnrBeschreibung(fahrten.getZnrBeschreibung());
                }
                if (fahrten.getVariante() != null) {
                    existingFahrten.setVariante(fahrten.getVariante());
                }
                if (fahrten.getTagesart() != null) {
                    existingFahrten.setTagesart(fahrten.getTagesart());
                }
                if (fahrten.getUmlauf() != null) {
                    existingFahrten.setUmlauf(fahrten.getUmlauf());
                }
                if (fahrten.getUmlaufindex() != null) {
                    existingFahrten.setUmlaufindex(fahrten.getUmlaufindex());
                }

                return existingFahrten;
            })
            .map(fahrtenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fahrten.getId().toString())
        );
    }

    /**
     * {@code GET  /fahrtens} : get all the fahrtens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fahrtens in body.
     */
    @GetMapping("/fahrtens")
    public List<Fahrten> getAllFahrtens() {
        log.debug("REST request to get all Fahrtens");
        return fahrtenRepository.findAll();
    }

    /**
     * {@code GET  /fahrtens/:id} : get the "id" fahrten.
     *
     * @param id the id of the fahrten to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fahrten, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fahrtens/{id}")
    public ResponseEntity<Fahrten> getFahrten(@PathVariable Long id) {
        log.debug("REST request to get Fahrten : {}", id);
        Optional<Fahrten> fahrten = fahrtenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fahrten);
    }

    /**
     * {@code DELETE  /fahrtens/:id} : delete the "id" fahrten.
     *
     * @param id the id of the fahrten to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fahrtens/{id}")
    public ResponseEntity<Void> deleteFahrten(@PathVariable Long id) {
        log.debug("REST request to delete Fahrten : {}", id);
        fahrtenRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
