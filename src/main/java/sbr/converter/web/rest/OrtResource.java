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
import sbr.converter.domain.Ort;
import sbr.converter.repository.OrtRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.Ort}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrtResource {

    private final Logger log = LoggerFactory.getLogger(OrtResource.class);

    private static final String ENTITY_NAME = "ort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrtRepository ortRepository;

    public OrtResource(OrtRepository ortRepository) {
        this.ortRepository = ortRepository;
    }

    /**
     * {@code POST  /orts} : Create a new ort.
     *
     * @param ort the ort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ort, or with status {@code 400 (Bad Request)} if the ort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orts")
    public ResponseEntity<Ort> createOrt(@RequestBody Ort ort) throws URISyntaxException {
        log.debug("REST request to save Ort : {}", ort);
        if (ort.getId() != null) {
            throw new BadRequestAlertException("A new ort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ort result = ortRepository.save(ort);
        return ResponseEntity
            .created(new URI("/api/orts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orts/:id} : Updates an existing ort.
     *
     * @param id the id of the ort to save.
     * @param ort the ort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ort,
     * or with status {@code 400 (Bad Request)} if the ort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orts/{id}")
    public ResponseEntity<Ort> updateOrt(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ort ort)
        throws URISyntaxException {
        log.debug("REST request to update Ort : {}, {}", id, ort);
        if (ort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ort result = ortRepository.save(ort);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ort.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /orts/:id} : Partial updates given fields of an existing ort, field will ignore if it is null
     *
     * @param id the id of the ort to save.
     * @param ort the ort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ort,
     * or with status {@code 400 (Bad Request)} if the ort is not valid,
     * or with status {@code 404 (Not Found)} if the ort is not found,
     * or with status {@code 500 (Internal Server Error)} if the ort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/orts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ort> partialUpdateOrt(@PathVariable(value = "id", required = false) final Long id, @RequestBody Ort ort)
        throws URISyntaxException {
        log.debug("REST request to partial update Ort partially : {}, {}", id, ort);
        if (ort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ort.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ortRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ort> result = ortRepository
            .findById(ort.getId())
            .map(existingOrt -> {
                if (ort.getIbnr() != null) {
                    existingOrt.setIbnr(ort.getIbnr());
                }
                if (ort.getDs100() != null) {
                    existingOrt.setDs100(ort.getDs100());
                }
                if (ort.getFront() != null) {
                    existingOrt.setFront(ort.getFront());
                }
                if (ort.getSeite() != null) {
                    existingOrt.setSeite(ort.getSeite());
                }
                if (ort.getInnen() != null) {
                    existingOrt.setInnen(ort.getInnen());
                }
                if (ort.getTft() != null) {
                    existingOrt.setTft(ort.getTft());
                }
                if (ort.getTerminal() != null) {
                    existingOrt.setTerminal(ort.getTerminal());
                }
                if (ort.getDs001c() != null) {
                    existingOrt.setDs001c(ort.getDs001c());
                }
                if (ort.getVideo() != null) {
                    existingOrt.setVideo(ort.getVideo());
                }
                if (ort.getDs009() != null) {
                    existingOrt.setDs009(ort.getDs009());
                }
                if (ort.getDs003() != null) {
                    existingOrt.setDs003(ort.getDs003());
                }
                if (ort.getLanguage() != null) {
                    existingOrt.setLanguage(ort.getLanguage());
                }

                return existingOrt;
            })
            .map(ortRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ort.getId().toString())
        );
    }

    /**
     * {@code GET  /orts} : get all the orts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orts in body.
     */
    @GetMapping("/orts")
    public List<Ort> getAllOrts() {
        log.debug("REST request to get all Orts");
        return ortRepository.findAll();
    }

    /**
     * {@code GET  /orts/:id} : get the "id" ort.
     *
     * @param id the id of the ort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orts/{id}")
    public ResponseEntity<Ort> getOrt(@PathVariable Long id) {
        log.debug("REST request to get Ort : {}", id);
        Optional<Ort> ort = ortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ort);
    }

    /**
     * {@code DELETE  /orts/:id} : delete the "id" ort.
     *
     * @param id the id of the ort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orts/{id}")
    public ResponseEntity<Void> deleteOrt(@PathVariable Long id) {
        log.debug("REST request to delete Ort : {}", id);
        ortRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
