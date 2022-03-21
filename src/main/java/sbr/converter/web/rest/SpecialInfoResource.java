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
import sbr.converter.domain.SpecialInfo;
import sbr.converter.repository.SpecialInfoRepository;
import sbr.converter.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sbr.converter.domain.SpecialInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SpecialInfoResource {

    private final Logger log = LoggerFactory.getLogger(SpecialInfoResource.class);

    private static final String ENTITY_NAME = "specialInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialInfoRepository specialInfoRepository;

    public SpecialInfoResource(SpecialInfoRepository specialInfoRepository) {
        this.specialInfoRepository = specialInfoRepository;
    }

    /**
     * {@code POST  /special-infos} : Create a new specialInfo.
     *
     * @param specialInfo the specialInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialInfo, or with status {@code 400 (Bad Request)} if the specialInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/special-infos")
    public ResponseEntity<SpecialInfo> createSpecialInfo(@RequestBody SpecialInfo specialInfo) throws URISyntaxException {
        log.debug("REST request to save SpecialInfo : {}", specialInfo);
        if (specialInfo.getId() != null) {
            throw new BadRequestAlertException("A new specialInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialInfo result = specialInfoRepository.save(specialInfo);
        return ResponseEntity
            .created(new URI("/api/special-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /special-infos/:id} : Updates an existing specialInfo.
     *
     * @param id the id of the specialInfo to save.
     * @param specialInfo the specialInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialInfo,
     * or with status {@code 400 (Bad Request)} if the specialInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/special-infos/{id}")
    public ResponseEntity<SpecialInfo> updateSpecialInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecialInfo specialInfo
    ) throws URISyntaxException {
        log.debug("REST request to update SpecialInfo : {}, {}", id, specialInfo);
        if (specialInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecialInfo result = specialInfoRepository.save(specialInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /special-infos/:id} : Partial updates given fields of an existing specialInfo, field will ignore if it is null
     *
     * @param id the id of the specialInfo to save.
     * @param specialInfo the specialInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialInfo,
     * or with status {@code 400 (Bad Request)} if the specialInfo is not valid,
     * or with status {@code 404 (Not Found)} if the specialInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the specialInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/special-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpecialInfo> partialUpdateSpecialInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SpecialInfo specialInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update SpecialInfo partially : {}, {}", id, specialInfo);
        if (specialInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecialInfo> result = specialInfoRepository
            .findById(specialInfo.getId())
            .map(existingSpecialInfo -> {
                if (specialInfo.getCode() != null) {
                    existingSpecialInfo.setCode(specialInfo.getCode());
                }
                if (specialInfo.getItem() != null) {
                    existingSpecialInfo.setItem(specialInfo.getItem());
                }
                if (specialInfo.getAttribute() != null) {
                    existingSpecialInfo.setAttribute(specialInfo.getAttribute());
                }
                if (specialInfo.getShortTerminalDesc() != null) {
                    existingSpecialInfo.setShortTerminalDesc(specialInfo.getShortTerminalDesc());
                }
                if (specialInfo.getLongTerminalDesc() != null) {
                    existingSpecialInfo.setLongTerminalDesc(specialInfo.getLongTerminalDesc());
                }
                if (specialInfo.getDisplayText() != null) {
                    existingSpecialInfo.setDisplayText(specialInfo.getDisplayText());
                }
                if (specialInfo.getDs003() != null) {
                    existingSpecialInfo.setDs003(specialInfo.getDs003());
                }
                if (specialInfo.getLanguage() != null) {
                    existingSpecialInfo.setLanguage(specialInfo.getLanguage());
                }

                return existingSpecialInfo;
            })
            .map(specialInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /special-infos} : get all the specialInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialInfos in body.
     */
    @GetMapping("/special-infos")
    public List<SpecialInfo> getAllSpecialInfos() {
        log.debug("REST request to get all SpecialInfos");
        return specialInfoRepository.findAll();
    }

    /**
     * {@code GET  /special-infos/:id} : get the "id" specialInfo.
     *
     * @param id the id of the specialInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/special-infos/{id}")
    public ResponseEntity<SpecialInfo> getSpecialInfo(@PathVariable Long id) {
        log.debug("REST request to get SpecialInfo : {}", id);
        Optional<SpecialInfo> specialInfo = specialInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(specialInfo);
    }

    /**
     * {@code DELETE  /special-infos/:id} : delete the "id" specialInfo.
     *
     * @param id the id of the specialInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/special-infos/{id}")
    public ResponseEntity<Void> deleteSpecialInfo(@PathVariable Long id) {
        log.debug("REST request to delete SpecialInfo : {}", id);
        specialInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
