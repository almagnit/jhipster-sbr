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
import sbr.converter.domain.Sonderziele;
import sbr.converter.repository.SonderzieleRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.Sonderziele}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SonderzieleResource {

    private final Logger log = LoggerFactory.getLogger(SonderzieleResource.class);

    private static final String ENTITY_NAME = "sonderziele";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SonderzieleRepository sonderzieleRepository;

    public SonderzieleResource(SonderzieleRepository sonderzieleRepository) {
        this.sonderzieleRepository = sonderzieleRepository;
    }

    /**
     * {@code POST  /sonderzieles} : Create a new sonderziele.
     *
     * @param sonderziele the sonderziele to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sonderziele, or with status {@code 400 (Bad Request)} if the sonderziele has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sonderzieles")
    public ResponseEntity<Sonderziele> createSonderziele(@RequestBody Sonderziele sonderziele) throws URISyntaxException {
        log.debug("REST request to save Sonderziele : {}", sonderziele);
        if (sonderziele.getId() != null) {
            throw new BadRequestAlertException("A new sonderziele cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sonderziele result = sonderzieleRepository.save(sonderziele);
        return ResponseEntity
            .created(new URI("/api/sonderzieles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sonderzieles/:id} : Updates an existing sonderziele.
     *
     * @param id the id of the sonderziele to save.
     * @param sonderziele the sonderziele to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sonderziele,
     * or with status {@code 400 (Bad Request)} if the sonderziele is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sonderziele couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sonderzieles/{id}")
    public ResponseEntity<Sonderziele> updateSonderziele(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sonderziele sonderziele
    ) throws URISyntaxException {
        log.debug("REST request to update Sonderziele : {}, {}", id, sonderziele);
        if (sonderziele.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sonderziele.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sonderzieleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sonderziele result = sonderzieleRepository.save(sonderziele);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sonderziele.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sonderzieles/:id} : Partial updates given fields of an existing sonderziele, field will ignore if it is null
     *
     * @param id the id of the sonderziele to save.
     * @param sonderziele the sonderziele to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sonderziele,
     * or with status {@code 400 (Bad Request)} if the sonderziele is not valid,
     * or with status {@code 404 (Not Found)} if the sonderziele is not found,
     * or with status {@code 500 (Internal Server Error)} if the sonderziele couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sonderzieles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sonderziele> partialUpdateSonderziele(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sonderziele sonderziele
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sonderziele partially : {}, {}", id, sonderziele);
        if (sonderziele.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sonderziele.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sonderzieleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sonderziele> result = sonderzieleRepository
            .findById(sonderziele.getId())
            .map(existingSonderziele -> {
                if (sonderziele.getZugnummer() != null) {
                    existingSonderziele.setZugnummer(sonderziele.getZugnummer());
                }
                if (sonderziele.getFront() != null) {
                    existingSonderziele.setFront(sonderziele.getFront());
                }
                if (sonderziele.getSeite1() != null) {
                    existingSonderziele.setSeite1(sonderziele.getSeite1());
                }
                if (sonderziele.getSeite2() != null) {
                    existingSonderziele.setSeite2(sonderziele.getSeite2());
                }
                if (sonderziele.getInnen() != null) {
                    existingSonderziele.setInnen(sonderziele.getInnen());
                }
                if (sonderziele.getTft() != null) {
                    existingSonderziele.setTft(sonderziele.getTft());
                }
                if (sonderziele.getTerminal() != null) {
                    existingSonderziele.setTerminal(sonderziele.getTerminal());
                }

                return existingSonderziele;
            })
            .map(sonderzieleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sonderziele.getId().toString())
        );
    }

    /**
     * {@code GET  /sonderzieles} : get all the sonderzieles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sonderzieles in body.
     */
    @GetMapping("/sonderzieles")
    public List<Sonderziele> getAllSonderzieles() {
        log.debug("REST request to get all Sonderzieles");
        return sonderzieleRepository.findAll();
    }

    /**
     * {@code GET  /sonderzieles/:id} : get the "id" sonderziele.
     *
     * @param id the id of the sonderziele to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sonderziele, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sonderzieles/{id}")
    public ResponseEntity<Sonderziele> getSonderziele(@PathVariable Long id) {
        log.debug("REST request to get Sonderziele : {}", id);
        Optional<Sonderziele> sonderziele = sonderzieleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sonderziele);
    }

    /**
     * {@code DELETE  /sonderzieles/:id} : delete the "id" sonderziele.
     *
     * @param id the id of the sonderziele to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sonderzieles/{id}")
    public ResponseEntity<Void> deleteSonderziele(@PathVariable Long id) {
        log.debug("REST request to delete Sonderziele : {}", id);
        sonderzieleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
