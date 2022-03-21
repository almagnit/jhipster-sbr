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
import sbr.converter.domain.VTS;
import sbr.converter.repository.VTSRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.VTS}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VTSResource {

    private final Logger log = LoggerFactory.getLogger(VTSResource.class);

    private static final String ENTITY_NAME = "vTS";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VTSRepository vTSRepository;

    public VTSResource(VTSRepository vTSRepository) {
        this.vTSRepository = vTSRepository;
    }

    /**
     * {@code POST  /vts} : Create a new vTS.
     *
     * @param vTS the vTS to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vTS, or with status {@code 400 (Bad Request)} if the vTS has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vts")
    public ResponseEntity<VTS> createVTS(@RequestBody VTS vTS) throws URISyntaxException {
        log.debug("REST request to save VTS : {}", vTS);
        if (vTS.getId() != null) {
            throw new BadRequestAlertException("A new vTS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VTS result = vTSRepository.save(vTS);
        return ResponseEntity
            .created(new URI("/api/vts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vts/:id} : Updates an existing vTS.
     *
     * @param id the id of the vTS to save.
     * @param vTS the vTS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vTS,
     * or with status {@code 400 (Bad Request)} if the vTS is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vTS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vts/{id}")
    public ResponseEntity<VTS> updateVTS(@PathVariable(value = "id", required = false) final Long id, @RequestBody VTS vTS)
        throws URISyntaxException {
        log.debug("REST request to update VTS : {}, {}", id, vTS);
        if (vTS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vTS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vTSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VTS result = vTSRepository.save(vTS);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vTS.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vts/:id} : Partial updates given fields of an existing vTS, field will ignore if it is null
     *
     * @param id the id of the vTS to save.
     * @param vTS the vTS to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vTS,
     * or with status {@code 400 (Bad Request)} if the vTS is not valid,
     * or with status {@code 404 (Not Found)} if the vTS is not found,
     * or with status {@code 500 (Internal Server Error)} if the vTS couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/vts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VTS> partialUpdateVTS(@PathVariable(value = "id", required = false) final Long id, @RequestBody VTS vTS)
        throws URISyntaxException {
        log.debug("REST request to partial update VTS partially : {}, {}", id, vTS);
        if (vTS.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vTS.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vTSRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VTS> result = vTSRepository
            .findById(vTS.getId())
            .map(existingVTS -> {
                if (vTS.getVerkehrstage() != null) {
                    existingVTS.setVerkehrstage(vTS.getVerkehrstage());
                }
                if (vTS.getBeschreibung() != null) {
                    existingVTS.setBeschreibung(vTS.getBeschreibung());
                }
                if (vTS.getVersionsnummer() != null) {
                    existingVTS.setVersionsnummer(vTS.getVersionsnummer());
                }
                if (vTS.getMandant() != null) {
                    existingVTS.setMandant(vTS.getMandant());
                }
                if (vTS.getFeiertage() != null) {
                    existingVTS.setFeiertage(vTS.getFeiertage());
                }
                if (vTS.getGueltigkeit() != null) {
                    existingVTS.setGueltigkeit(vTS.getGueltigkeit());
                }

                return existingVTS;
            })
            .map(vTSRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vTS.getId().toString())
        );
    }

    /**
     * {@code GET  /vts} : get all the vTS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vTS in body.
     */
    @GetMapping("/vts")
    public List<VTS> getAllVTS() {
        log.debug("REST request to get all VTS");
        return vTSRepository.findAll();
    }

    /**
     * {@code GET  /vts/:id} : get the "id" vTS.
     *
     * @param id the id of the vTS to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vTS, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vts/{id}")
    public ResponseEntity<VTS> getVTS(@PathVariable Long id) {
        log.debug("REST request to get VTS : {}", id);
        Optional<VTS> vTS = vTSRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vTS);
    }

    /**
     * {@code DELETE  /vts/:id} : delete the "id" vTS.
     *
     * @param id the id of the vTS to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vts/{id}")
    public ResponseEntity<Void> deleteVTS(@PathVariable Long id) {
        log.debug("REST request to delete VTS : {}", id);
        vTSRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
