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
import sbr.converter.domain.SpecialAnnouncement;
import sbr.converter.repository.SpecialAnnouncementRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.SpecialAnnouncement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SpecialAnnouncementResource {

    private final Logger log = LoggerFactory.getLogger(SpecialAnnouncementResource.class);

    private static final String ENTITY_NAME = "specialAnnouncement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialAnnouncementRepository specialAnnouncementRepository;

    public SpecialAnnouncementResource(SpecialAnnouncementRepository specialAnnouncementRepository) {
        this.specialAnnouncementRepository = specialAnnouncementRepository;
    }

    /**
     * {@code POST  /special-announcements} : Create a new specialAnnouncement.
     *
     * @param specialAnnouncement the specialAnnouncement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialAnnouncement, or with status {@code 400 (Bad Request)} if the specialAnnouncement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/special-announcements")
    public ResponseEntity<SpecialAnnouncement> createSpecialAnnouncement(@RequestBody SpecialAnnouncement specialAnnouncement)
        throws URISyntaxException {
        log.debug("REST request to save SpecialAnnouncement : {}", specialAnnouncement);
        if (specialAnnouncement.getId() != null) {
            throw new BadRequestAlertException("A new specialAnnouncement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialAnnouncement result = specialAnnouncementRepository.save(specialAnnouncement);
        return ResponseEntity
            .created(new URI("/api/special-announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /special-announcements/:id} : Updates an existing specialAnnouncement.
     *
     * @param id the id of the specialAnnouncement to save.
     * @param specialAnnouncement the specialAnnouncement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialAnnouncement,
     * or with status {@code 400 (Bad Request)} if the specialAnnouncement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialAnnouncement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/special-announcements/{id}")
    public ResponseEntity<SpecialAnnouncement> updateSpecialAnnouncement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecialAnnouncement specialAnnouncement
    ) throws URISyntaxException {
        log.debug("REST request to update SpecialAnnouncement : {}, {}", id, specialAnnouncement);
        if (specialAnnouncement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialAnnouncement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialAnnouncementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecialAnnouncement result = specialAnnouncementRepository.save(specialAnnouncement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialAnnouncement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /special-announcements/:id} : Partial updates given fields of an existing specialAnnouncement, field will ignore if it is null
     *
     * @param id the id of the specialAnnouncement to save.
     * @param specialAnnouncement the specialAnnouncement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialAnnouncement,
     * or with status {@code 400 (Bad Request)} if the specialAnnouncement is not valid,
     * or with status {@code 404 (Not Found)} if the specialAnnouncement is not found,
     * or with status {@code 500 (Internal Server Error)} if the specialAnnouncement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/special-announcements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpecialAnnouncement> partialUpdateSpecialAnnouncement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecialAnnouncement specialAnnouncement
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpecialAnnouncement partially : {}, {}", id, specialAnnouncement);
        if (specialAnnouncement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialAnnouncement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialAnnouncementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecialAnnouncement> result = specialAnnouncementRepository
            .findById(specialAnnouncement.getId())
            .map(existingSpecialAnnouncement -> {
                if (specialAnnouncement.getCode() != null) {
                    existingSpecialAnnouncement.setCode(specialAnnouncement.getCode());
                }
                if (specialAnnouncement.getItem() != null) {
                    existingSpecialAnnouncement.setItem(specialAnnouncement.getItem());
                }
                if (specialAnnouncement.getAusgabeOrt() != null) {
                    existingSpecialAnnouncement.setAusgabeOrt(specialAnnouncement.getAusgabeOrt());
                }
                if (specialAnnouncement.getKurz() != null) {
                    existingSpecialAnnouncement.setKurz(specialAnnouncement.getKurz());
                }
                if (specialAnnouncement.getLanguage() != null) {
                    existingSpecialAnnouncement.setLanguage(specialAnnouncement.getLanguage());
                }
                if (specialAnnouncement.getAnsagedatei() != null) {
                    existingSpecialAnnouncement.setAnsagedatei(specialAnnouncement.getAnsagedatei());
                }
                if (specialAnnouncement.getKlartext() != null) {
                    existingSpecialAnnouncement.setKlartext(specialAnnouncement.getKlartext());
                }

                return existingSpecialAnnouncement;
            })
            .map(specialAnnouncementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialAnnouncement.getId().toString())
        );
    }

    /**
     * {@code GET  /special-announcements} : get all the specialAnnouncements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialAnnouncements in body.
     */
    @GetMapping("/special-announcements")
    public List<SpecialAnnouncement> getAllSpecialAnnouncements() {
        log.debug("REST request to get all SpecialAnnouncements");
        return specialAnnouncementRepository.findAll();
    }

    /**
     * {@code GET  /special-announcements/:id} : get the "id" specialAnnouncement.
     *
     * @param id the id of the specialAnnouncement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialAnnouncement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/special-announcements/{id}")
    public ResponseEntity<SpecialAnnouncement> getSpecialAnnouncement(@PathVariable Long id) {
        log.debug("REST request to get SpecialAnnouncement : {}", id);
        Optional<SpecialAnnouncement> specialAnnouncement = specialAnnouncementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(specialAnnouncement);
    }

    /**
     * {@code DELETE  /special-announcements/:id} : delete the "id" specialAnnouncement.
     *
     * @param id the id of the specialAnnouncement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/special-announcements/{id}")
    public ResponseEntity<Void> deleteSpecialAnnouncement(@PathVariable Long id) {
        log.debug("REST request to delete SpecialAnnouncement : {}", id);
        specialAnnouncementRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
