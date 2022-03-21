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
import sbr.converter.domain.Ziel;
import sbr.converter.repository.ZielRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.Ziel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ZielResource {

    private final Logger log = LoggerFactory.getLogger(ZielResource.class);

    private static final String ENTITY_NAME = "ziel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZielRepository zielRepository;

    public ZielResource(ZielRepository zielRepository) {
        this.zielRepository = zielRepository;
    }

    /**
     * {@code POST  /ziels} : Create a new ziel.
     *
     * @param ziel the ziel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ziel, or with status {@code 400 (Bad Request)} if the ziel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ziels")
    public ResponseEntity<Ziel> createZiel(@RequestBody Ziel ziel) throws URISyntaxException {
        log.debug("REST request to save Ziel : {}", ziel);
        if (ziel.getId() != null) {
            throw new BadRequestAlertException("A new ziel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ziel result = zielRepository.save(ziel);
        return ResponseEntity
            .created(new URI("/api/ziels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ziels/:id} : Updates an existing ziel.
     *
     * @param id the id of the ziel to save.
     * @param ziel the ziel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ziel,
     * or with status {@code 400 (Bad Request)} if the ziel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ziel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ziels/{id}")
    public ResponseEntity<Ziel> updateZiel(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ziel ziel)
        throws URISyntaxException {
        log.debug("REST request to update Ziel : {}, {}", id, ziel);
        if (ziel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ziel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zielRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ziel result = zielRepository.save(ziel);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ziel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ziels/:id} : Partial updates given fields of an existing ziel, field will ignore if it is null
     *
     * @param id the id of the ziel to save.
     * @param ziel the ziel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ziel,
     * or with status {@code 400 (Bad Request)} if the ziel is not valid,
     * or with status {@code 404 (Not Found)} if the ziel is not found,
     * or with status {@code 500 (Internal Server Error)} if the ziel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ziels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ziel> partialUpdateZiel(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ziel ziel)
        throws URISyntaxException {
        log.debug("REST request to partial update Ziel partially : {}, {}", id, ziel);
        if (ziel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ziel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!zielRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ziel> result = zielRepository
            .findById(ziel.getId())
            .map(existingZiel -> {
                if (ziel.getCode() != null) {
                    existingZiel.setCode(ziel.getCode());
                }
                if (ziel.getFront() != null) {
                    existingZiel.setFront(ziel.getFront());
                }
                if (ziel.getSeite1() != null) {
                    existingZiel.setSeite1(ziel.getSeite1());
                }
                if (ziel.getSeite2() != null) {
                    existingZiel.setSeite2(ziel.getSeite2());
                }
                if (ziel.getInnen() != null) {
                    existingZiel.setInnen(ziel.getInnen());
                }
                if (ziel.getTft() != null) {
                    existingZiel.setTft(ziel.getTft());
                }
                if (ziel.getTerminal() != null) {
                    existingZiel.setTerminal(ziel.getTerminal());
                }
                if (ziel.getLanguage() != null) {
                    existingZiel.setLanguage(ziel.getLanguage());
                }

                return existingZiel;
            })
            .map(zielRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ziel.getId().toString())
        );
    }

    /**
     * {@code GET  /ziels} : get all the ziels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ziels in body.
     */
    @GetMapping("/ziels")
    public List<Ziel> getAllZiels() {
        log.debug("REST request to get all Ziels");
        return zielRepository.findAll();
    }

    /**
     * {@code GET  /ziels/:id} : get the "id" ziel.
     *
     * @param id the id of the ziel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ziel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ziels/{id}")
    public ResponseEntity<Ziel> getZiel(@PathVariable Long id) {
        log.debug("REST request to get Ziel : {}", id);
        Optional<Ziel> ziel = zielRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ziel);
    }

    /**
     * {@code DELETE  /ziels/:id} : delete the "id" ziel.
     *
     * @param id the id of the ziel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ziels/{id}")
    public ResponseEntity<Void> deleteZiel(@PathVariable Long id) {
        log.debug("REST request to delete Ziel : {}", id);
        zielRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
