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
import sbr.converter.domain.LSATurnout;
import sbr.converter.repository.LSATurnoutRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.LSATurnout}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LSATurnoutResource {

    private final Logger log = LoggerFactory.getLogger(LSATurnoutResource.class);

    private static final String ENTITY_NAME = "lSATurnout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LSATurnoutRepository lSATurnoutRepository;

    public LSATurnoutResource(LSATurnoutRepository lSATurnoutRepository) {
        this.lSATurnoutRepository = lSATurnoutRepository;
    }

    /**
     * {@code POST  /lsa-turnouts} : Create a new lSATurnout.
     *
     * @param lSATurnout the lSATurnout to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lSATurnout, or with status {@code 400 (Bad Request)} if the lSATurnout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lsa-turnouts")
    public ResponseEntity<LSATurnout> createLSATurnout(@RequestBody LSATurnout lSATurnout) throws URISyntaxException {
        log.debug("REST request to save LSATurnout : {}", lSATurnout);
        if (lSATurnout.getId() != null) {
            throw new BadRequestAlertException("A new lSATurnout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LSATurnout result = lSATurnoutRepository.save(lSATurnout);
        return ResponseEntity
            .created(new URI("/api/lsa-turnouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lsa-turnouts/:id} : Updates an existing lSATurnout.
     *
     * @param id the id of the lSATurnout to save.
     * @param lSATurnout the lSATurnout to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lSATurnout,
     * or with status {@code 400 (Bad Request)} if the lSATurnout is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lSATurnout couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lsa-turnouts/{id}")
    public ResponseEntity<LSATurnout> updateLSATurnout(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LSATurnout lSATurnout
    ) throws URISyntaxException {
        log.debug("REST request to update LSATurnout : {}, {}", id, lSATurnout);
        if (lSATurnout.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lSATurnout.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lSATurnoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LSATurnout result = lSATurnoutRepository.save(lSATurnout);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lSATurnout.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lsa-turnouts/:id} : Partial updates given fields of an existing lSATurnout, field will ignore if it is null
     *
     * @param id the id of the lSATurnout to save.
     * @param lSATurnout the lSATurnout to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lSATurnout,
     * or with status {@code 400 (Bad Request)} if the lSATurnout is not valid,
     * or with status {@code 404 (Not Found)} if the lSATurnout is not found,
     * or with status {@code 500 (Internal Server Error)} if the lSATurnout couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lsa-turnouts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LSATurnout> partialUpdateLSATurnout(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LSATurnout lSATurnout
    ) throws URISyntaxException {
        log.debug("REST request to partial update LSATurnout partially : {}, {}", id, lSATurnout);
        if (lSATurnout.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lSATurnout.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lSATurnoutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LSATurnout> result = lSATurnoutRepository
            .findById(lSATurnout.getId())
            .map(existingLSATurnout -> {
                if (lSATurnout.getStation1() != null) {
                    existingLSATurnout.setStation1(lSATurnout.getStation1());
                }
                if (lSATurnout.getStation2() != null) {
                    existingLSATurnout.setStation2(lSATurnout.getStation2());
                }
                if (lSATurnout.getLsaCode() != null) {
                    existingLSATurnout.setLsaCode(lSATurnout.getLsaCode());
                }
                if (lSATurnout.getTurnout() != null) {
                    existingLSATurnout.setTurnout(lSATurnout.getTurnout());
                }

                return existingLSATurnout;
            })
            .map(lSATurnoutRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lSATurnout.getId().toString())
        );
    }

    /**
     * {@code GET  /lsa-turnouts} : get all the lSATurnouts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lSATurnouts in body.
     */
    @GetMapping("/lsa-turnouts")
    public List<LSATurnout> getAllLSATurnouts() {
        log.debug("REST request to get all LSATurnouts");
        return lSATurnoutRepository.findAll();
    }

    /**
     * {@code GET  /lsa-turnouts/:id} : get the "id" lSATurnout.
     *
     * @param id the id of the lSATurnout to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lSATurnout, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lsa-turnouts/{id}")
    public ResponseEntity<LSATurnout> getLSATurnout(@PathVariable Long id) {
        log.debug("REST request to get LSATurnout : {}", id);
        Optional<LSATurnout> lSATurnout = lSATurnoutRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lSATurnout);
    }

    /**
     * {@code DELETE  /lsa-turnouts/:id} : delete the "id" lSATurnout.
     *
     * @param id the id of the lSATurnout to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lsa-turnouts/{id}")
    public ResponseEntity<Void> deleteLSATurnout(@PathVariable Long id) {
        log.debug("REST request to delete LSATurnout : {}", id);
        lSATurnoutRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
