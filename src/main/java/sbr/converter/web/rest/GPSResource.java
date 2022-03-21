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
import sbr.converter.domain.GPS;
import sbr.converter.repository.GPSRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.GPS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GPSResource {

    private final Logger log = LoggerFactory.getLogger(GPSResource.class);

    private static final String ENTITY_NAME = "gPS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GPSRepository gPSRepository;

    public GPSResource(GPSRepository gPSRepository) {
        this.gPSRepository = gPSRepository;
    }

    /**
     * {@code POST  /gps} : Create a new gPS.
     *
     * @param gPS the gPS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gPS, or with status {@code 400 (Bad Request)} if the gPS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gps")
    public ResponseEntity<GPS> createGPS(@RequestBody GPS gPS) throws URISyntaxException {
        log.debug("REST request to save GPS : {}", gPS);
        if (gPS.getId() != null) {
            throw new BadRequestAlertException("A new gPS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GPS result = gPSRepository.save(gPS);
        return ResponseEntity
            .created(new URI("/api/gps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gps/:id} : Updates an existing gPS.
     *
     * @param id the id of the gPS to save.
     * @param gPS the gPS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gPS,
     * or with status {@code 400 (Bad Request)} if the gPS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gPS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gps/{id}")
    public ResponseEntity<GPS> updateGPS(@PathVariable(value = "id", required = false) final Long id, @RequestBody GPS gPS)
        throws URISyntaxException {
        log.debug("REST request to update GPS : {}, {}", id, gPS);
        if (gPS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gPS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gPSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GPS result = gPSRepository.save(gPS);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gPS.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gps/:id} : Partial updates given fields of an existing gPS, field will ignore if it is null
     *
     * @param id the id of the gPS to save.
     * @param gPS the gPS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gPS,
     * or with status {@code 400 (Bad Request)} if the gPS is not valid,
     * or with status {@code 404 (Not Found)} if the gPS is not found,
     * or with status {@code 500 (Internal Server Error)} if the gPS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GPS> partialUpdateGPS(@PathVariable(value = "id", required = false) final Long id, @RequestBody GPS gPS)
        throws URISyntaxException {
        log.debug("REST request to partial update GPS partially : {}, {}", id, gPS);
        if (gPS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gPS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gPSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GPS> result = gPSRepository
            .findById(gPS.getId())
            .map(existingGPS -> {
                if (gPS.getName() != null) {
                    existingGPS.setName(gPS.getName());
                }
                if (gPS.getGpsCode() != null) {
                    existingGPS.setGpsCode(gPS.getGpsCode());
                }
                if (gPS.getInneresFenster() != null) {
                    existingGPS.setInneresFenster(gPS.getInneresFenster());
                }
                if (gPS.getAuBeresFenster() != null) {
                    existingGPS.setAuBeresFenster(gPS.getAuBeresFenster());
                }

                return existingGPS;
            })
            .map(gPSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gPS.getId().toString())
        );
    }

    /**
     * {@code GET  /gps} : get all the gPS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gPS in body.
     */
    @GetMapping("/gps")
    public List<GPS> getAllGPS() {
        log.debug("REST request to get all GPS");
        return gPSRepository.findAll();
    }

    /**
     * {@code GET  /gps/:id} : get the "id" gPS.
     *
     * @param id the id of the gPS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gPS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gps/{id}")
    public ResponseEntity<GPS> getGPS(@PathVariable Long id) {
        log.debug("REST request to get GPS : {}", id);
        Optional<GPS> gPS = gPSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gPS);
    }

    /**
     * {@code DELETE  /gps/:id} : delete the "id" gPS.
     *
     * @param id the id of the gPS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gps/{id}")
    public ResponseEntity<Void> deleteGPS(@PathVariable Long id) {
        log.debug("REST request to delete GPS : {}", id);
        gPSRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
