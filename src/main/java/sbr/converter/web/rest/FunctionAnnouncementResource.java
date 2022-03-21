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
import sbr.converter.domain.FunctionAnnouncement;
import sbr.converter.repository.FunctionAnnouncementRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.FunctionAnnouncement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FunctionAnnouncementResource {

    private final Logger log = LoggerFactory.getLogger(FunctionAnnouncementResource.class);

    private static final String ENTITY_NAME = "functionAnnouncement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FunctionAnnouncementRepository functionAnnouncementRepository;

    public FunctionAnnouncementResource(FunctionAnnouncementRepository functionAnnouncementRepository) {
        this.functionAnnouncementRepository = functionAnnouncementRepository;
    }

    /**
     * {@code POST  /function-announcements} : Create a new functionAnnouncement.
     *
     * @param functionAnnouncement the functionAnnouncement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new functionAnnouncement, or with status {@code 400 (Bad Request)} if the functionAnnouncement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/function-announcements")
    public ResponseEntity<FunctionAnnouncement> createFunctionAnnouncement(@RequestBody FunctionAnnouncement functionAnnouncement)
        throws URISyntaxException {
        log.debug("REST request to save FunctionAnnouncement : {}", functionAnnouncement);
        if (functionAnnouncement.getId() != null) {
            throw new BadRequestAlertException("A new functionAnnouncement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FunctionAnnouncement result = functionAnnouncementRepository.save(functionAnnouncement);
        return ResponseEntity
            .created(new URI("/api/function-announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /function-announcements/:id} : Updates an existing functionAnnouncement.
     *
     * @param id the id of the functionAnnouncement to save.
     * @param functionAnnouncement the functionAnnouncement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionAnnouncement,
     * or with status {@code 400 (Bad Request)} if the functionAnnouncement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the functionAnnouncement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/function-announcements/{id}")
    public ResponseEntity<FunctionAnnouncement> updateFunctionAnnouncement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FunctionAnnouncement functionAnnouncement
    ) throws URISyntaxException {
        log.debug("REST request to update FunctionAnnouncement : {}, {}", id, functionAnnouncement);
        if (functionAnnouncement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionAnnouncement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionAnnouncementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FunctionAnnouncement result = functionAnnouncementRepository.save(functionAnnouncement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionAnnouncement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /function-announcements/:id} : Partial updates given fields of an existing functionAnnouncement, field will ignore if it is null
     *
     * @param id the id of the functionAnnouncement to save.
     * @param functionAnnouncement the functionAnnouncement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated functionAnnouncement,
     * or with status {@code 400 (Bad Request)} if the functionAnnouncement is not valid,
     * or with status {@code 404 (Not Found)} if the functionAnnouncement is not found,
     * or with status {@code 500 (Internal Server Error)} if the functionAnnouncement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/function-announcements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FunctionAnnouncement> partialUpdateFunctionAnnouncement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FunctionAnnouncement functionAnnouncement
    ) throws URISyntaxException {
        log.debug("REST request to partial update FunctionAnnouncement partially : {}, {}", id, functionAnnouncement);
        if (functionAnnouncement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, functionAnnouncement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!functionAnnouncementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FunctionAnnouncement> result = functionAnnouncementRepository
            .findById(functionAnnouncement.getId())
            .map(existingFunctionAnnouncement -> {
                if (functionAnnouncement.getCode() != null) {
                    existingFunctionAnnouncement.setCode(functionAnnouncement.getCode());
                }
                if (functionAnnouncement.getAudioFile() != null) {
                    existingFunctionAnnouncement.setAudioFile(functionAnnouncement.getAudioFile());
                }
                if (functionAnnouncement.getBeschreibung() != null) {
                    existingFunctionAnnouncement.setBeschreibung(functionAnnouncement.getBeschreibung());
                }
                if (functionAnnouncement.getAnmerkung() != null) {
                    existingFunctionAnnouncement.setAnmerkung(functionAnnouncement.getAnmerkung());
                }
                if (functionAnnouncement.getLanguage() != null) {
                    existingFunctionAnnouncement.setLanguage(functionAnnouncement.getLanguage());
                }

                return existingFunctionAnnouncement;
            })
            .map(functionAnnouncementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, functionAnnouncement.getId().toString())
        );
    }

    /**
     * {@code GET  /function-announcements} : get all the functionAnnouncements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of functionAnnouncements in body.
     */
    @GetMapping("/function-announcements")
    public List<FunctionAnnouncement> getAllFunctionAnnouncements() {
        log.debug("REST request to get all FunctionAnnouncements");
        return functionAnnouncementRepository.findAll();
    }

    /**
     * {@code GET  /function-announcements/:id} : get the "id" functionAnnouncement.
     *
     * @param id the id of the functionAnnouncement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the functionAnnouncement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/function-announcements/{id}")
    public ResponseEntity<FunctionAnnouncement> getFunctionAnnouncement(@PathVariable Long id) {
        log.debug("REST request to get FunctionAnnouncement : {}", id);
        Optional<FunctionAnnouncement> functionAnnouncement = functionAnnouncementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(functionAnnouncement);
    }

    /**
     * {@code DELETE  /function-announcements/:id} : delete the "id" functionAnnouncement.
     *
     * @param id the id of the functionAnnouncement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/function-announcements/{id}")
    public ResponseEntity<Void> deleteFunctionAnnouncement(@PathVariable Long id) {
        log.debug("REST request to delete FunctionAnnouncement : {}", id);
        functionAnnouncementRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
